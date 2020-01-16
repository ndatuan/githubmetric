package com.quodai.githubmetric.service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.quodai.githubmetric.main.HealthScoreCalculator;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;

public class CsvResultPrintingService {

	private static String[] HEADERS = {"repo_name","health_score","num_commits"};
	private static int NUMBER_OF_PRINTED_REPOS = 1000;
	
	public static CsvResultPrintingService newInstance() {
		return new CsvResultPrintingService();
	}
	
	public void printResult(List<GitRepositoryOverview> results) throws IOException {
		try(FileWriter out = new FileWriter(HealthScoreCalculator.RESOURCES_FOLDER + "repo_result_" + UUID.randomUUID() + ".csv");
				CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
			List<GitRepositoryOverview> printedRepos = results.subList(0, 1000);
			printRepos(printer, printedRepos);
		}
	}

	private void printRepos(CSVPrinter printer, List<GitRepositoryOverview> printedRepos) {
		for(int i = 0; i < NUMBER_OF_PRINTED_REPOS; i++) {
			GitRepositoryOverview repo = printedRepos.get(i);
			try {
				printer.printRecord(repo.getRepoName(), repo.getCommitHealthScore(), repo.getNoOfCommit());
			} catch (IOException e) {
				System.out.println("Repo " + repo.getRepoName() + " cannot print");
				e.printStackTrace();
			}
		}
	}

	private List<GitRepositoryOverview> buildPrintedRepos(TreeMap<BigDecimal, List<GitRepositoryOverview>> results) {
		List<GitRepositoryOverview> printedRepositoryOverviews = new ArrayList<>();
		for (Map.Entry<BigDecimal,List<GitRepositoryOverview>> entry : results.entrySet()) {
			printedRepositoryOverviews.addAll(entry.getValue());
			if(printedRepositoryOverviews.size() > NUMBER_OF_PRINTED_REPOS) {
				break;
			}
		}
		return printedRepositoryOverviews;
	}
	
}
