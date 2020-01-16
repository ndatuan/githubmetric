package com.quodai.githubmetric.shared.model;

import java.util.HashMap;
import java.util.Map;

public class GithubRawData {

	private Map<String, GitRepositoryOverview> gitRepos;
	private HourGitRepositoryOverview hourRepoOverview;
	
	public GithubRawData() {
		gitRepos = new HashMap<String, GitRepositoryOverview>();
		hourRepoOverview = new HourGitRepositoryOverview();
	}

	public Map<String, GitRepositoryOverview> getGitRepos() {
		return gitRepos;
	}

	public void setGitRepos(Map<String, GitRepositoryOverview> gitRepos) {
		this.gitRepos = gitRepos;
	}

	public HourGitRepositoryOverview getHourRepoOverview() {
		return hourRepoOverview;
	}

	public void setHourRepoOverview(HourGitRepositoryOverview hourRepoOverview) {
		this.hourRepoOverview = hourRepoOverview;
	}

}
