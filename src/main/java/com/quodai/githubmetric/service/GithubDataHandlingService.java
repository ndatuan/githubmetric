package com.quodai.githubmetric.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quodai.githubmetric.constant.GitEventType;
import com.quodai.githubmetric.github.model.GithubEvent;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;
import com.quodai.githubmetric.shared.model.HourGitRepositoryOverview;

public class GithubDataHandlingService {
	
	private Map<String, GitRepositoryOverview> gitRepos;
	private Set<String> repoIds;
	private HourGitRepositoryOverview hourRepoOverview;
	
	public static GithubDataHandlingService newInstance() {
		return new GithubDataHandlingService();
	}
	
	private GithubDataHandlingService() {
		gitRepos = new HashMap<>();
		repoIds = new HashSet<>();
		hourRepoOverview = new HourGitRepositoryOverview();
	}
	

	public GithubRawData handleData(String jsonFilePath) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));) {
			String line = reader.readLine();
			while(line != null) {
				GithubEvent githubEvent = readGithubDataByLine(line);
				GitRepositoryOverview gitRepositoryOverview = findRepositoryOverview(githubEvent);
				handleCommitForPushEvent(githubEvent, gitRepositoryOverview);
				line = reader.readLine();
			}
		}
		GithubRawData rawData = buildRawData();
		return rawData;
	}

	public void synchronizeMaxRepoDataInHour(List<GithubRawData> rawDatas) {
		// TODO Auto-generated method stub
		List<HourGitRepositoryOverview> hourRepoOverviews = rawDatas.stream().map(GithubRawData::getHourRepoOverview).collect(Collectors.toList());
		int maxCommit = hourRepoOverviews.stream().map(HourGitRepositoryOverview::getMaxCommit).mapToInt(Integer::intValue).max().orElse(0);
		hourRepoOverviews.forEach(hourRepoOverview -> hourRepoOverview.setMaxCommit(maxCommit));
	}
	
	private GithubRawData buildRawData() {
		GithubRawData rawData = new GithubRawData();
		rawData.setGitRepos(gitRepos);
		rawData.setHourRepoOverview(hourRepoOverview);
		return rawData;
	}

	private void handleCommitForPushEvent(GithubEvent githubEvent, GitRepositoryOverview gitRepositoryOverview) {
		if(StringUtils.equals(githubEvent.getType(), GitEventType.PUSH_EVENT)) {
			int noOfCommit = githubEvent.getPayload().getDistinct_size();
			countRepoCommitAndFindMaxCommitsOfAllRepos(gitRepositoryOverview, noOfCommit);
		}
	}

	private void countRepoCommitAndFindMaxCommitsOfAllRepos(GitRepositoryOverview gitRepositoryOverview,
			int noOfCommit) {
		noOfCommit = gitRepositoryOverview.getNoOfCommit() + noOfCommit;
		gitRepositoryOverview.setNoOfCommit(noOfCommit);
		checkMaxCommit(noOfCommit);
	}
	
	private GithubEvent readGithubDataByLine(String line) throws JsonProcessingException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		GithubEvent githubEvent = mapper.readValue(line, GithubEvent.class);
		return githubEvent;
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

	private void checkMaxCommit(int currentCommitNumber) {
		if(currentCommitNumber > hourRepoOverview.getMaxCommit()) {
			hourRepoOverview.setMaxCommit(currentCommitNumber);
		}
	}
}
