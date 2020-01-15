package com.quodai.githubmetric.shared.model;

import java.math.BigDecimal;

public class GitRepositoryOverview {

	String repoId;
	String repoName;
	int noOfCommit;
	BigDecimal commitHealthScore;

	public GitRepositoryOverview() {
	}

	public GitRepositoryOverview(String orgId, String repoName) {
		this.repoId = orgId;
		this.repoName = repoName;
		this.commitHealthScore = BigDecimal.ZERO;
		this.noOfCommit = 0;
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

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public BigDecimal getCommitHealthScore() {
		return commitHealthScore;
	}

	public void setCommitHealthScore(BigDecimal healthScore) {
		this.commitHealthScore = healthScore;
	}

}
