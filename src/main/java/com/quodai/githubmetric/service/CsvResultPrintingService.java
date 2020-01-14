package com.quodai.githubmetric.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.quodai.githubmetric.constant.Constants;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.HealthScoreMark;

public class CsvResultPrintingService {

	private static String[] HEADERS = {"repo_name","health_score","num_commits"};
	
	public static CsvResultPrintingService newInstance() {
		return new CsvResultPrintingService();
	}
	
	public void printResult(TreeMap<HealthScoreMark, GitRepositoryOverview> results) throws IOException {
		try(FileWriter out = new FileWriter(Constants.RESOURCES_FOLDER + "result.csv");
				CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
			results.forEach((key, value) -> {
				try {
					printer.printRecord(value.getRepoName(), value.getHealthScore(), value.getNoOfCommit());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
}
