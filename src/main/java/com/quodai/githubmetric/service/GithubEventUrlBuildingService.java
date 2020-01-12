package com.quodai.githubmetric.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.quodai.githubmetric.constant.Constants;

public class GithubEventUrlBuildingService {
	
	private String url = "https://data.gharchive.org/";
	
	public static GithubEventUrlBuildingService newInstance() {
		return new GithubEventUrlBuildingService();
	}
	
	public String buildUrl(Date startDate, Date endDate) {
		Calendar startDateCalendar = DateUtils.toCalendar(startDate);
		Calendar endDateCalendar = DateUtils.toCalendar(endDate);
		StringBuilder builder = new StringBuilder(url);
		builder.append(startDateCalendar.get(Calendar.YEAR));
		builder.append(Constants.MINUS_SEPARATOR);
		builder.append(startDateCalendar.get(Calendar.MONTH) + 1);
		builder.append(Constants.MINUS_SEPARATOR);
		buildTimeForRequest(startDateCalendar, endDateCalendar, builder, Calendar.DATE);
		builder.append(Constants.MINUS_SEPARATOR);
		buildTimeForRequest(startDateCalendar, endDateCalendar, builder, Calendar.HOUR);
		return builder.toString();
	}

	private void buildTimeForRequest(Calendar startDateCalendar, Calendar endDateCalendar, StringBuilder builder, int field) {
		int startTime = startDateCalendar.get(field);
		int endTime = endDateCalendar.get(field);
		if(startTime != endTime) {
			String format = String.format("{%s..%s}", startTime, endTime);
			builder.append(format);
		} else {
			builder.append(startTime);
		}
	}
	
}
