package com.quodai.githubmetric.sorting.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.quodai.githubmetric.shared.model.GitRepositoryOverview;

public class MergeSort {

	public static List<GitRepositoryOverview> sortAllGitRepo(List<List<GitRepositoryOverview>> results) {
		if(results.size() == 1) {
			return results.get(0);
		}
		List<GitRepositoryOverview> lefts = sortAllGitRepo(results.subList(0, results.size()/2));
		List<GitRepositoryOverview> rights = sortAllGitRepo(results.subList(results.size()/2, results.size()));
		int leftPointer = 0;
		int rightPointer = 0;
		List<GitRepositoryOverview> mergedSortRepos = new ArrayList<>();
		while(leftPointer < lefts.size() && rightPointer < rights.size()) {
			GitRepositoryOverview leftRepo = lefts.get(leftPointer);
			GitRepositoryOverview rightRepo = rights.get(rightPointer);
			if(leftRepo.getCommitHealthScore().compareTo(rightRepo.getCommitHealthScore()) > 0) {
				mergedSortRepos.add(leftRepo);
				leftPointer++;
			} else {
				mergedSortRepos.add(rightRepo);
				rightPointer++;
			}
		}
		if(leftPointer < lefts.size()) {
			mergedSortRepos.addAll(lefts.subList(leftPointer, lefts.size()));
		}
		if(rightPointer < rights.size()) {
			mergedSortRepos.addAll(rights.subList(rightPointer, rights.size()));
		}
		return mergedSortRepos;
	}
}
