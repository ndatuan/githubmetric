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
		String requestUrl = "https://data.gharchive.org/2019-01-01-15.json.gz";
		String filePath = GithubEventDownloadingService.newInstance().downloadFileAndReturnFilePath(requestUrl);
		System.out.println("Finish download");
		String jsonFilePath = FileUnzipService.newInstance().unzipToJsonFile(filePath);
		System.out.println("Finish unzip");
		GithubRawData rawData = GithubDataHandlingService.newInstance().handleData(jsonFilePath);
		System.out.println("Handle data");
		TreeMap<BigDecimal, List<GitRepositoryOverview>> results = HealthScoreCalculationService.newInstance().calculate(rawData);
		CsvResultPrintingService.newInstance().printResult(results);
	}
}
