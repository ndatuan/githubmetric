package com.quodai.githubmetric.main;

import java.io.IOException;
import java.io.InputStream;

import com.quodai.githubmetric.service.GithubEventDownloadingService;
import com.quodai.githubmetric.service.GithubEventUrlBuildingService;
import com.quodai.githubmetric.util.DateUtils;

public class HealthScoreCalculator {
	
	public static void main(String[] args) throws IOException {
		String startDateInput = "2019-01-01T00:00:00Z";
		String endDateInput = "2019-01-02T00:00:00Z";
		String requestUrl = GithubEventUrlBuildingService.newInstance().buildUrl(DateUtils.convertIso8601Format(startDateInput), DateUtils.convertIso8601Format(endDateInput));
		System.out.println(requestUrl);
		InputStream inputStream = GithubEventDownloadingService.newInstance().getGitHubEvent(requestUrl);
		
	}
	
}
