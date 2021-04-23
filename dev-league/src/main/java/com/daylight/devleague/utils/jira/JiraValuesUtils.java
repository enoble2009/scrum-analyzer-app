package com.daylight.devleague.utils.jira;

public class JiraValuesUtils {

	public static String cutoff(String text, int maxLength) {
		if (text == null) {
			return "";
		}
		return text.substring(0, Math.min(maxLength, text.length()));
	}
	
}
