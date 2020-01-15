package com.quodai.githubmetric.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

public class HealthScoreComparator implements Comparator<BigDecimal>{

	@Override
	public int compare(BigDecimal arg0, BigDecimal arg1) {
		return arg1.compareTo(arg0);
	}


}
