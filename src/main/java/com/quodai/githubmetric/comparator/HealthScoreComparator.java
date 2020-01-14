package com.quodai.githubmetric.comparator;

import java.util.Comparator;

public class HealthScoreComparator implements Comparator<Integer>{

	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return o2 - o1;
	}

}
