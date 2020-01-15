package com.quodai.githubmetric.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.quodai.githubmetric.service.CsvResultPrintingService;
import com.quodai.githubmetric.service.FileUnzipService;
import com.quodai.githubmetric.service.GithubDataHandlingService;
import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.HealthScoreCalculationService;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;

public class HealthScoreCalculator {
	
	public static void main(String[] args) throws IOException {
		String requestUrl = args[0];
		System.out.println("----------- request " + requestUrl);
		String resourceFolder = args[1];
		System.out.println("----------- resourceFolder " + resourceFolder);
		String filePath = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrl, resourceFolder);
		System.out.println("----------- filePath " + filePath);
		String jsonFilePath = FileUnzipService.newInstance().unzipToJsonFile(filePath);
		GithubRawData rawData = GithubDataHandlingService.newInstance().handleData(jsonFilePath);
		TreeMap<BigDecimal, List<GitRepositoryOverview>> results = HealthScoreCalculationService.newInstance().calculate(rawData);
		CsvResultPrintingService.newInstance().printResult(results, resourceFolder);
	}
}
