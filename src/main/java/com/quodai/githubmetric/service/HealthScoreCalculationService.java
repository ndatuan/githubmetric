package com.quodai.githubmetric.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.quodai.githubmetric.comparator.HealthScoreComparator;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;
import com.quodai.githubmetric.shared.model.HealthScoreMark;

public class HealthScoreCalculationService {

	public static HealthScoreCalculationService newInstance() {
		return new HealthScoreCalculationService();
	}
	
	public TreeMap<HealthScoreMark, GitRepositoryOverview> calculate(GithubRawData rawData) throws IOException {
		Map<String, GitRepositoryOverview> gitRepos = rawData.getGitRepos();
		GitRepositoryOverview maxRepoData = rawData.getMaxRepoData();
		TreeMap<HealthScoreMark, GitRepositoryOverview> treeMap = new TreeMap<HealthScoreMark, GitRepositoryOverview>(new HealthScoreComparator());
		gitRepos.entrySet().forEach(entry -> {
			GitRepositoryOverview repoOverview = calculateHealthScoreForEachRepo(maxRepoData, entry);
			treeMap.put(new HealthScoreMark(repoOverview.getHealthScore()), repoOverview);
		});
		return treeMap;
	}

	private GitRepositoryOverview calculateHealthScoreForEachRepo(GitRepositoryOverview maxRepoData, Entry<String, GitRepositoryOverview> entry) {
		GitRepositoryOverview repoOverview = entry.getValue();
		BigDecimal healthScore = calculateHealthScoreByCommits(repoOverview, maxRepoData.getNoOfCommit());
		repoOverview.setHealthScore(healthScore);
		return repoOverview;
	}

	private BigDecimal calculateHealthScoreByCommits(GitRepositoryOverview gitRepositoryOverview, int maxCommits) {
		return BigDecimal.valueOf(gitRepositoryOverview.getNoOfCommit()).divide(BigDecimal.valueOf(maxCommits), 3, RoundingMode.HALF_UP);
	}

}
