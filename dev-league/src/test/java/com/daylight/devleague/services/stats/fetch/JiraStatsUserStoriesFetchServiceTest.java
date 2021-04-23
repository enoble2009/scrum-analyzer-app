package com.daylight.devleague.services.stats.fetch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.daylight.devleague.daos.jira.issues.JiraUserStoryDao;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;

@ExtendWith(MockitoExtension.class)
public class JiraStatsUserStoriesFetchServiceTest {

	@Mock private JiraUserStoryDao userStoriesDao;
	
	@InjectMocks private JiraStatsUserStoriesFetchService service;
	
	@BeforeEach()
	public void before() {
		service = new JiraStatsUserStoriesFetchService(userStoriesDao);
	}
	
	@Test
	public void should_call_last_update_after_function_when_startdate_is_not_null() {
		List<JiraUserStory> stories = new ArrayList<>();
		Page<JiraUserStory> storiesPaginated = new PageImpl<>(stories);
		
		LocalDateTime localDateTime = LocalDateTime.now();
		when(userStoriesDao.findByLastUpdateAfter(eq(localDateTime), any())).thenReturn(storiesPaginated);
		
		service.fetch(localDateTime, 0);
		
		verify(userStoriesDao, times(1)).findByLastUpdateAfter(eq(localDateTime), any());
		verify(userStoriesDao, never()).findAll((Pageable) any());
	}

	@Test
	public void should_call_find_all_function_when_startdate_is_null() {
		List<JiraUserStory> stories = new ArrayList<>();
		Page<JiraUserStory> storiesPaginated = new PageImpl<>(stories);
		
		LocalDateTime localDateTime = null;
		when(userStoriesDao.findAll((Pageable) any())).thenReturn(storiesPaginated);
		
		service.fetch(localDateTime, 0);
		
		verify(userStoriesDao, never()).findByLastUpdateAfter(any(), any());
		verify(userStoriesDao, times(1)).findAll((Pageable) any());
	}
	
}
