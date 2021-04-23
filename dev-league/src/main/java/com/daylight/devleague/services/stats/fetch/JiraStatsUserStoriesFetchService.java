package com.daylight.devleague.services.stats.fetch;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.issues.JiraUserStoryDao;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatsUserStoriesFetchService {

	private static final int MAX_PER_PAGE = 100;
	
	private final JiraUserStoryDao userStoriesDao;
	
	public List<JiraUserStory> fetch(LocalDateTime startDate, int page) {
		Pageable pageable = PageRequest.of(page, MAX_PER_PAGE);
		if (startDate != null) {
			return userStoriesDao.findByLastUpdateAfter(startDate, pageable).getContent();
		} else {
			return userStoriesDao.findAll(pageable).getContent();
		}
	}
	
}
