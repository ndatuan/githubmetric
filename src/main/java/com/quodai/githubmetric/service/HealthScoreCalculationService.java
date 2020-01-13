package com.quodai.githubmetric.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quodai.githubmetric.constant.GitEventType;
import com.quodai.githubmetric.model.GitRepoData;
import com.quodai.githubmetric.model.GithubEvent;

public class HealthScoreCalculationService {

	private static Map<String, GitRepoData> gitRepos = new HashMap<>();
	private static GitRepoData maxRepoData = new GitRepoData();
	static {
		maxRepoData.setNoOfCommit(0);
	}
	
	
	public static HealthScoreCalculationService newInstance() {
		return new HealthScoreCalculationService();
	}
	
	public void calculate(String jsonFilePath) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));) {
			String line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				ObjectMapper mapper = new ObjectMapper();
				GithubEvent githubEvent = mapper.readValue(line, GithubEvent.class);
				if(StringUtils.equals(githubEvent.getType(), GitEventType.PUSH_EVENT)) {
					calculateNoOfCommitForEachRepo(githubEvent);
				}
				line = reader.readLine();
			}
		}
		gitRepos.entrySet().forEach(entry -> {
			System.out.println("entry id " + entry.getKey() + " ----total commit: " + entry.getValue().getNoOfCommit());
		});
		System.out.println("Max repo " + maxRepoData.getNoOfCommit());
	}

	private void calculateNoOfCommitForEachRepo(GithubEvent githubEvent) {
		String repoId = githubEvent.getRepo().getId();
		int noOfCommit = githubEvent.getPayload().getDistinct_size();
		if(!gitRepos.containsKey(repoId)) {
			gitRepos.put(repoId, new GitRepoData(repoId, noOfCommit));
			if(noOfCommit > maxRepoData.getNoOfCommit()) {
				maxRepoData.setNoOfCommit(noOfCommit);
			}
		} else {
			GitRepoData gitRepoData = gitRepos.get(repoId);
			gitRepoData.setNoOfCommit(gitRepoData.getNoOfCommit() + noOfCommit);
			if(gitRepoData.getNoOfCommit() > maxRepoData.getNoOfCommit()) {
				maxRepoData.setNoOfCommit(gitRepoData.getNoOfCommit());
			}
		}
	}
	
}
