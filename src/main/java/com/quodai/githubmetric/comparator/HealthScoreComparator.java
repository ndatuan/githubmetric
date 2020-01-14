package com.quodai.githubmetric.comparator;

import java.util.Comparator;

import com.quodai.githubmetric.shared.model.HealthScoreMark;

public class HealthScoreComparator implements Comparator<HealthScoreMark>{

	@Override
	public int compare(HealthScoreMark arg0, HealthScoreMark arg1) {
		return arg1.getHealthScore().compareTo(arg0.getHealthScore());
	}


}
