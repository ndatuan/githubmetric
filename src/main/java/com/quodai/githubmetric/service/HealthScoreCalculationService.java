package com.quodai.githubmetric.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.quodai.githubmetric.comparator.HealthScoreComparator;
import com.quodai.githubmetric.shared.model.GitRepositoryOverview;
import com.quodai.githubmetric.shared.model.GithubRawData;
import com.quodai.githubmetric.shared.model.GitRepoMaxData;

public class HealthScoreCalculationService {

	private Map<BigDecimal, List<GitRepositoryOverview>> bucketsForSameHeathScoreRepository;
	
	public static HealthScoreCalculationService newInstance() {
		return new HealthScoreCalculationService();
	}
	
	private HealthScoreCalculationService() {
		bucketsForSameHeathScoreRepository = new HashMap<>();
	}
	
	public TreeMap<BigDecimal, List<GitRepositoryOverview>> calculate(GithubRawData rawData) throws IOException {
		Map<String, GitRepositoryOverview> gitRepos = rawData.getRepoIdToGitRepos();
		GitRepoMaxData hourRepoOverview = rawData.getGitRepoMaxData();
		TreeMap<BigDecimal, List<GitRepositoryOverview>> sortedGitsRepoByHealthScore = new TreeMap<BigDecimal, List<GitRepositoryOverview>>(new HealthScoreComparator());
		gitRepos.entrySet().forEach(entry -> {
			GitRepositoryOverview repoOverview = calculateHealthScoreForRepo(hourRepoOverview, entry);
			addRepoToSortedHealthScoreBinaryMap(sortedGitsRepoByHealthScore, repoOverview);
		});
		return sortedGitsRepoByHealthScore;
	}

	private void addRepoToSortedHealthScoreBinaryMap(
			TreeMap<BigDecimal, List<GitRepositoryOverview>> sortedGitsRepoByHealthScore,
			GitRepositoryOverview repoOverview) {
		List<GitRepositoryOverview> gitRepositoryOverviews;
		BigDecimal commitHealthScore = repoOverview.getCommitHealthScore();
		if(bucketsForSameHeathScoreRepository.containsKey(commitHealthScore)) {
			gitRepositoryOverviews = bucketsForSameHeathScoreRepository.get(commitHealthScore);
			gitRepositoryOverviews.add(repoOverview);
		} else {
			gitRepositoryOverviews = new ArrayList<>();
			gitRepositoryOverviews.add(repoOverview);
			sortedGitsRepoByHealthScore.put(commitHealthScore, gitRepositoryOverviews);
			bucketsForSameHeathScoreRepository.put(commitHealthScore, gitRepositoryOverviews);
		}
	}

	private GitRepositoryOverview calculateHealthScoreForRepo(GitRepoMaxData hourRepoOverview, Entry<String, GitRepositoryOverview> entry) {
		GitRepositoryOverview repoOverview = entry.getValue();
		BigDecimal commitHealthScore = calculateHealthScoreByCommits(repoOverview, hourRepoOverview.getMaxCommit());
		repoOverview.setCommitHealthScore(commitHealthScore);
		return repoOverview;
	}

	private BigDecimal calculateHealthScoreByCommits(GitRepositoryOverview gitRepositoryOverview, int maxCommits) {
		return BigDecimal.valueOf(gitRepositoryOverview.getNoOfCommit()).divide(BigDecimal.valueOf(maxCommits), 10, RoundingMode.HALF_UP);
	}

}
