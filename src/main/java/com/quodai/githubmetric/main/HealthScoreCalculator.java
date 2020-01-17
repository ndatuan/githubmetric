package com.quodai.githubmetric.main;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.quodai.githubmetric.service.CsvResultPrintingService;
import com.quodai.githubmetric.service.FileUnzipService;
import com.quodai.githubmetric.service.GitHubDataSynchronizingService;
import com.quodai.githubmetric.service.GithubDataHandlingService;
import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.GithubEventUrlBuildingService;
import com.quodai.githubmetric.service.HealthScoreCalculationService;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;

public class HealthScoreCalculator {
	
	public static String RESOURCES_FOLDER = "src/main/resources/";
	
	public static void main(String[] args) throws IOException {
		File folder = new File(RESOURCES_FOLDER);
		if(folder.mkdir()) {
			System.out.println("create folder successfully");
		}
		//List<String> requestUrls = GithubEventUrlBuildingService.newInstance().buildUrl("2019-01-01T00:00:00Z", "2019-01-01T05:00:00Z");
		List<String> requestUrls = GithubEventUrlBuildingService .newInstance().buildUrl(args[0], args[1]);
		System.out.println("Finish building url");
		List<String> archiveFilePaths = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrls);
		System.out.println("Finish downloading url");
		List<String> jsonFilePaths = FileUnzipService.newInstance().unzipToJsonFile(archiveFilePaths);
		System.out.println("Finish extract url");
		List<GithubRawData> rawDatas = extractRawDataFromJsonFilePaths(jsonFilePaths);
		System.out.println("Finish handling raw data from json file");
		GithubRawData githubRawData = GitHubDataSynchronizingService.newInstance().synchronize(rawDatas);
		System.out.println("Finish synchronize raw data from hours");
		TreeMap<BigDecimal, List<GitRepositoryOverview>> results = HealthScoreCalculationService.newInstance().calculate(githubRawData);
		System.out.println("Finish sorting");
		CsvResultPrintingService.newInstance().printResult(results);
		System.out.println("Finish printing results");
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
