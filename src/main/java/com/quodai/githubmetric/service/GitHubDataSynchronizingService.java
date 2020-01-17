package com.quodai.githubmetric.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quodai.githubmetric.shared.model.GitRepoMaxData;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;

public class GitHubDataSynchronizingService {

	private GitRepoMaxData maxData;
	
	public static GitHubDataSynchronizingService newInstance() {
		return new GitHubDataSynchronizingService();
	}
	
	private GitHubDataSynchronizingService() {
		maxData = new GitRepoMaxData();
	}
	
	public GithubRawData synchronize(List<GithubRawData> rawDatas) {
		GithubRawData githubRawData = new GithubRawData();
		Map<String, GitRepositoryOverview> gitRepos = new HashMap<>();
		int size = 0;
		for (GithubRawData githubRawData2 : rawDatas) {
			size += githubRawData2.getRepoIdToGitRepos().size();
		};
		System.out.println("total before " + size);
		rawDatas.forEach(rawDataPerHour -> {
			rawDataPerHour.getRepoIdToGitRepos().entrySet().forEach(gitRepoEntryInHour-> {
				String repoId = gitRepoEntryInHour.getKey();
				GitRepositoryOverview repoOverviewInHour = gitRepoEntryInHour.getValue();
				if(gitRepos.containsKey(repoId)) {
					GitRepositoryOverview mergedRepoOverview = gitRepos.get(repoId);
					mergedRepoOverview.setNoOfCommit(mergedRepoOverview.getNoOfCommit() + repoOverviewInHour.getNoOfCommit());
					if(mergedRepoOverview.getNoOfCommit() > maxData.getMaxCommit()) {
						maxData.setMaxCommit(mergedRepoOverview.getNoOfCommit());
					}
				} else {
					gitRepos.put(repoId, repoOverviewInHour);
				}
			});
		});
		githubRawData.setGitRepos(gitRepos);
		githubRawData.setGitRepoMaxData(maxData);
		return githubRawData;
	}
}
