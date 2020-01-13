package com.quodai.githubmetric.model;

public class GitRepoData {
	
	String orgId;
	int noOfCommit;
	
	public GitRepoData() {}
	
	public GitRepoData(String orgId, int noOfCommit) {
		this.orgId = orgId;
		this.noOfCommit = noOfCommit;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public int getNoOfCommit() {
		return noOfCommit;
	}
	public void setNoOfCommit(int noOfCommit) {
		this.noOfCommit = noOfCommit;
	}
}
