package com.quodai.githubmetric.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.quodai.githubmetric.service.CsvResultPrintingService;
import com.quodai.githubmetric.service.FileUnzipService;
import com.quodai.githubmetric.service.GithubDataHandlingService;
import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.GithubEventUrlBuildingService;
import com.quodai.githubmetric.service.HealthScoreCalculationService;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;
import com.quodai.githubmetric.shared.model.HourGitRepositoryOverview;
import com.quodai.githubmetric.sorting.algorithm.MergeSort;

public class HealthScoreCalculator {
	
	public static String RESOURCES_FOLDER = "src/main/resources/";
	
	public static void main(String[] args) throws IOException {
		//List<String> requestUrls = GithubEventUrlBuildingService.newInstance().buildUrl("2019-01-01T00:00:00Z", "2019-01-01T05:00:00Z");
		List<String> requestUrls = GithubEventUrlBuildingService .newInstance().buildUrl(args[0], args[1]);
		System.out.println("Finish building url");
		List<String> archiveFilePaths = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrls);
		System.out.println("Finish downloading url");
		List<String> jsonFilePaths = FileUnzipService.newInstance().unzipToJsonFile(archiveFilePaths);
		System.out.println("Finish extract url");
		List<GithubRawData> rawDatas = extractRawDataFromJsonFilePaths(jsonFilePaths);
		GithubDataHandlingService.newInstance().synchronizeMaxRepoDataInHour(rawDatas);
		System.out.println("Finish handling raw data from json file");
		List<List<GitRepositoryOverview>> resultsPerHour = calculateHealthScoreAndSortDataPerHour(rawDatas);
		System.out.println("Finish calculate score");
		List<GitRepositoryOverview> results = MergeSort.sortAllGitRepo(resultsPerHour);
		System.out.println("Finish sorting");
		CsvResultPrintingService.newInstance().printResult(results);
		System.out.println("Finish printing results");
	}

	private static List<List<GitRepositoryOverview>> calculateHealthScoreAndSortDataPerHour(List<GithubRawData> rawDatas) {
		CopyOnWriteArrayList<List<GitRepositoryOverview>> results = new CopyOnWriteArrayList<>();
		rawDatas.parallelStream().forEach(rawDataPerHour -> {
			try {
				TreeMap<BigDecimal, List<GitRepositoryOverview>> resultPerHour = HealthScoreCalculationService.newInstance().calculate(rawDataPerHour);
				results.add(resultPerHour.values().stream().flatMap(List::stream).collect(Collectors.toList()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return results;
	}

	private static List<GithubRawData> extractRawDataFromJsonFilePaths(List<String> jsonFilePaths) {
		CopyOnWriteArrayList<GithubRawData> rawDatas = new CopyOnWriteArrayList<>();
		jsonFilePaths.parallelStream().forEach(jsonFilePath -> {
			try {
				GithubRawData rawData = GithubDataHandlingService.newInstance().handleData(jsonFilePath);
				rawDatas.add(rawData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return rawDatas;
	}
	
}
