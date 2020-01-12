package com.quodai.githubmetric.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateUtils {
	
	public static Date convertIso8601Format(String date) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	    TemporalAccessor accessor = timeFormatter.parse(date);
	    return Date.from(Instant.from(accessor));
	}
	
}
