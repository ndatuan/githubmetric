package com.quodai.githubmetric.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.quodai.githubmetric.service.FileUnzipService;
import com.quodai.githubmetric.service.GithubDataHandlingService;
import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.GithubEventUrlBuildingService;
import com.quodai.githubmetric.service.HealthScoreCalculationService;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;

public class HealthScoreCalculator {
	
	public static String RESOURCES_FOLDER = "D:\\New folder\\";
	
	public static void main(String[] args) throws IOException {
		List<String> requestUrls = GithubEventUrlBuildingService .newInstance().buildUrl("2019-01-01T00:00:00Z", "2019-01-02T00:00:00Z");
		System.out.println("Finish building url");
		List<String> archiveFilePaths = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrls);
		System.out.println("Finish downloading url");
		List<String> jsonFilePaths = FileUnzipService.newInstance().unzipToJsonFile(archiveFilePaths);
		System.out.println("Finish extract url");
		List<GithubRawData> rawDatas = extractRawDataFromJsonFilePaths(jsonFilePaths);
		System.out.println("Finish handling raw data from json file");
		TreeMap<BigDecimal, List<GitRepositoryOverview>> results = calculateHealthScore(rawDatas);
	}

	private static TreeMap<BigDecimal, List<GitRepositoryOverview>> calculateHealthScore(List<GithubRawData> rawDatas) {
		CopyOnWriteArrayList<TreeMap<BigDecimal, List<GitRepositoryOverview>>> results = new CopyOnWriteArrayList<>();
		rawDatas.parallelStream().forEach(rawDataPerHour -> {
			try {
				TreeMap<BigDecimal, List<GitRepositoryOverview>> resultPerHour = HealthScoreCalculationService.newInstance().calculate(rawDataPerHour);
				results.add(resultPerHour);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return null;
	}

	private static List<GithubRawData> extractRawDataFromJsonFilePaths(List<String> jsonFilePaths) {
		CopyOnWriteArrayList<GithubRawData> rawDatas = new CopyOnWriteArrayList<>();
		jsonFilePaths.parallelStream().forEach(jsonFilePath -> {
			try {
				GithubRawData rawData = GithubDataHandlingService.newInstance().handleData(jsonFilePath);
				rawDatas.add(rawData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return rawDatas;
	}
}
