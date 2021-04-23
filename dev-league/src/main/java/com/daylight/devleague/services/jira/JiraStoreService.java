package com.daylight.devleague.services.jira;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public abstract class JiraStoreService<T> {

	protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	protected static final int MAX_GOAL_LENGTH = 255;
	protected static final int MAX_NAME_LENGTH = 500;
	protected static final int MAX_DESCRIPTION_LENGTH = 2000;

	public abstract void store(Stream<T> items);

	protected LocalDateTime parse(String dateString) {
		try {
			return LocalDateTime.parse(dateString, formatter);
		} catch (Throwable e) {
			return null;
		}
	}

}
