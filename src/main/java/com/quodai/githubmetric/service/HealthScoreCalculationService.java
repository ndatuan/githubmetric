package com.quodai.githubmetric.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quodai.githubmetric.comparator.HealthScoreComparator;
import com.quodai.githubmetric.constant.GitEventType;
import com.quodai.githubmetric.github.model.GithubEvent;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;

public class HealthScoreCalculationService {

	private static Map<String, GitRepositoryOverview> gitRepos = new HashMap<>();
	private static Set<String> repoIds = new HashSet<>();
	private static GitRepositoryOverview maxRepoData = new GitRepositoryOverview();
	static {
		maxRepoData.setNoOfCommit(0);
	}
	
	
	public static HealthScoreCalculationService newInstance() {
		return new HealthScoreCalculationService();
	}
	
	public TreeMap<Integer, GitRepositoryOverview> calculate(String jsonFilePath) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));) {
			String line = reader.readLine();
			while(line != null) {
				GithubEvent githubEvent = readGithubDataByLine(line);
				GitRepositoryOverview gitRepositoryOverview = findRepositoryOverview(githubEvent);
				if(StringUtils.equals(githubEvent.getType(), GitEventType.PUSH_EVENT)) {
					int noOfCommit = githubEvent.getPayload().getDistinct_size();
					calculateRepoCommitAndCheckMaxCommitsOfAllRepos(gitRepositoryOverview, noOfCommit);
				}
				line = reader.readLine();
			}
		}
		TreeMap<Integer, GitRepositoryOverview> treeMap = buildResults();
		return treeMap;
	}

	private TreeMap<Integer, GitRepositoryOverview> buildResults() {
		TreeMap<Integer, GitRepositoryOverview> treeMap = new TreeMap<Integer, GitRepositoryOverview>(new HealthScoreComparator());
		gitRepos.entrySet().forEach(entry -> {
			treeMap.put(entry.getValue().getNoOfCommit(), entry.getValue());
		});
		return treeMap;
	}

	private GithubEvent readGithubDataByLine(String line) throws JsonProcessingException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		GithubEvent githubEvent = mapper.readValue(line, GithubEvent.class);
		return githubEvent;
	}

	private void calculateRepoCommitAndCheckMaxCommitsOfAllRepos(GitRepositoryOverview gitRepositoryOverview,
			int noOfCommit) {
		noOfCommit = gitRepositoryOverview.getNoOfCommit() + noOfCommit;
		gitRepositoryOverview.setNoOfCommit(noOfCommit);
		checkMaxCommit(noOfCommit);
	}

	private GitRepositoryOverview findRepositoryOverview(GithubEvent githubEvent) {
		GitRepositoryOverview repositoryOverview = null;
		String repoId = githubEvent.getRepo().getId();
		if(repoIds.contains(repoId)) {
			repositoryOverview = gitRepos.get(repoId);
		} else {
			repositoryOverview = new GitRepositoryOverview(repoId, githubEvent.getRepo().getName());
			repoIds.add(repoId);
			gitRepos.put(repoId, repositoryOverview);
		}
		return repositoryOverview;
	}

	private void checkMaxCommit(int gitRepoData) {
		if(gitRepoData > maxRepoData.getNoOfCommit()) {
			maxRepoData.setNoOfCommit(gitRepoData);
		}
	}
	
}
