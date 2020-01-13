package com.quodai.githubmetric.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
	
	private int distinct_size;

	public int getDistinct_size() {
		return distinct_size;
	}

	public void setDistinct_size(int distinct_size) {
		this.distinct_size = distinct_size;
	}
	
}
