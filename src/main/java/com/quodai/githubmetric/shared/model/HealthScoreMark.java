package com.quodai.githubmetric.shared.model;

import java.math.BigDecimal;

public class HealthScoreMark {

	private BigDecimal healthScore;

	public HealthScoreMark(BigDecimal healthScore) {
		this.healthScore = healthScore;
	}

	public BigDecimal getHealthScore() {
		return healthScore;
	}

	public void setHealthScore(BigDecimal healthScore) {
		this.healthScore = healthScore;
	}

}
