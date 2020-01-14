package com.quodai.githubmetric.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.TreeMap;

import com.quodai.githubmetric.constant.Constants;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;

public class CsvResultPrintingService {

	public static CsvResultPrintingService newInstance() {
		return new CsvResultPrintingService();
	}
	
	public void printResult(TreeMap<Integer, GitRepositoryOverview> results) throws IOException {
		try(Reader in = new FileReader(Constants.RESOURCES_FOLDER + "result.csv");) {
			
		}
	}
	
}
