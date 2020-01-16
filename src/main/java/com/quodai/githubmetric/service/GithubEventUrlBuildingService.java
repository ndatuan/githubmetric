package com.quodai.githubmetric.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.quodai.githubmetric.constant.Constants;
import com.quodai.githubmetric.util.DateUtils;
import com.quodai.githubmetric.util.NumberUtils;

public class GithubEventUrlBuildingService {
	
	private static String GH_ARCHIVE_URL = "https://data.gharchive.org/";

	public static GithubEventUrlBuildingService newInstance() {
		return new GithubEventUrlBuildingService();
	}

	public List<String> buildUrl(String startDate, String endDate) {
		Date startTime = DateUtils.convertIso8601Format(startDate);
		Date endTime = DateUtils.convertIso8601Format(endDate);
		List<String> requestUrls = new ArrayList<>();
		String url = StringUtils.EMPTY;
		while(startTime.equals(endTime) || startTime.before(endTime)) {
			Calendar startDateCalendar = org.apache.commons.lang3.time.DateUtils.toCalendar(startTime);
			startDateCalendar.add(Calendar.HOUR_OF_DAY, 1);
			StringBuilder builder = new StringBuilder(GH_ARCHIVE_URL);
			builder.append(startDateCalendar.get(Calendar.YEAR));
			builder.append(Constants.MINUS_SEPARATOR);
			builder.append(NumberUtils.formatNumberForDate(startDateCalendar.get(Calendar.MONTH) + 1));
			builder.append(Constants.MINUS_SEPARATOR);
			builder.append(NumberUtils.formatNumberForDate(startDateCalendar.get(Calendar.DATE)));
			builder.append(Constants.MINUS_SEPARATOR);
			builder.append(NumberUtils.formatNumberForDate(startDateCalendar.get(Calendar.HOUR_OF_DAY)));
			builder.append(".json.gz");
			url = builder.toString();
			System.out.println(url);
			requestUrls.add(url);
			startDateCalendar.add(Calendar.HOUR_OF_DAY, 1);
			startTime = startDateCalendar.getTime();
		}
		return requestUrls;
	}
}
