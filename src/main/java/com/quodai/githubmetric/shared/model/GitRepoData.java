package com.quodai.githubmetric.shared.model;

public class GitRepoData {
	
	String repoId;
	int noOfCommit;
	
	public GitRepoData() {}
	
	public GitRepoData(String orgId, int noOfCommit) {
		this.repoId = orgId;
		this.noOfCommit = noOfCommit;
	}
	
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	public int getNoOfCommit() {
		return noOfCommit;
	}
	public void setNoOfCommit(int noOfCommit) {
		this.noOfCommit = noOfCommit;
	}
}
