package com.quodai.githubmetric.util;

public class NumberUtils {
	
	public static String NUM_FORMAT_FOR_TIME = "%02d";
	
	public static String formatNumberForDate(int num) {
		return String.format(NUM_FORMAT_FOR_TIME, num);
	}
}
