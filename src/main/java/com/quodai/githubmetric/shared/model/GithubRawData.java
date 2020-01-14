package com.quodai.githubmetric.shared.model;

import java.util.HashMap;
import java.util.Map;

public class GithubRawData {

	private Map<String, GitRepositoryOverview> gitRepos;
	private GitRepositoryOverview maxRepoData;

	public GithubRawData() {
		gitRepos = new HashMap<String, GitRepositoryOverview>();
		maxRepoData = new GitRepositoryOverview();
	}

	public Map<String, GitRepositoryOverview> getGitRepos() {
		return gitRepos;
	}

	public void setGitRepos(Map<String, GitRepositoryOverview> gitRepos) {
		this.gitRepos = gitRepos;
	}

	public GitRepositoryOverview getMaxRepoData() {
		return maxRepoData;
	}

	public void setMaxRepoData(GitRepositoryOverview maxRepoData) {
		this.maxRepoData = maxRepoData;
	}

}
