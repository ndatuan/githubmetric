package com.quodai.githubmetric.shared.model;

import java.util.HashMap;
import java.util.Map;

public class GithubRawData {

	private Map<String, GitRepositoryOverview> gitRepos;
	private GitRepoMaxData gitRepoMaxData;
	
	public GithubRawData() {
		gitRepos = new HashMap<String, GitRepositoryOverview>();
		gitRepoMaxData = new GitRepoMaxData();
	}

	public Map<String, GitRepositoryOverview> getGitRepos() {
		return gitRepos;
	}

	public void setGitRepos(Map<String, GitRepositoryOverview> gitRepos) {
		this.gitRepos = gitRepos;
	}

	public GitRepoMaxData getGitRepoMaxData() {
		return gitRepoMaxData;
	}

	public void setGitRepoMaxData(GitRepoMaxData gitRepoMaxData) {
		this.gitRepoMaxData = gitRepoMaxData;
	}

}
