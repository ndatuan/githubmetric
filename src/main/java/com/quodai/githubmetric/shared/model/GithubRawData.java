package com.quodai.githubmetric.shared.model;

import java.util.HashMap;
import java.util.Map;

public class GithubRawData {

	private Map<String, GitRepositoryOverview> repoIdToGitRepos;
	private GitRepoMaxData gitRepoMaxData;
	
	public GithubRawData() {
		repoIdToGitRepos = new HashMap<String, GitRepositoryOverview>();
		gitRepoMaxData = new GitRepoMaxData();
	}

	public Map<String, GitRepositoryOverview> getRepoIdToGitRepos() {
		return repoIdToGitRepos;
	}

	public void setGitRepos(Map<String, GitRepositoryOverview> gitRepos) {
		this.repoIdToGitRepos = gitRepos;
	}

	public GitRepoMaxData getGitRepoMaxData() {
		return gitRepoMaxData;
	}

	public void setGitRepoMaxData(GitRepoMaxData gitRepoMaxData) {
		this.gitRepoMaxData = gitRepoMaxData;
	}

}
