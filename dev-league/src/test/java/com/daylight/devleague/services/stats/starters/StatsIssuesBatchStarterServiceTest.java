package com.daylight.devleague.services.stats.starters;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;
import com.daylight.devleague.dto.jira.JiraBatchEntity;
import com.daylight.devleague.services.stats.fetch.JiraStatsUserStoriesFetchService;
import com.daylight.devleague.services.stats.process.JiraStatsUserStoriesProcessService;
import com.daylight.devleague.services.stats.store.JiraStatsUserStoriesStoreService;

@ExtendWith(MockitoExtension.class)
public class StatsIssuesBatchStarterServiceTest {

	@Mock private JiraStatsUserStoriesFetchService fetchService;
	@Mock private JiraStatsUserStoriesProcessService processService;
	@Mock private JiraStatsUserStoriesStoreService storeService;
	
	@InjectMocks private JiraStatsIssuesBatchStarterService service;

	@BeforeEach
	public void before() {
		service = new JiraStatsIssuesBatchStarterService(fetchService, processService, storeService);
	}
	
	@Test
	public void entity_is_issues() {
		JiraBatchEntity entity = service.getEntity();
		assertEquals(entity, JiraBatchEntity.ISSUES);
	}
	
	@Test
	public void should_not_call_processor_and_store_when_fetch_list_is_empty() {
		List<JiraUserStory> emptyUserStories = new ArrayList<>();
		JiraBatchLog log = new JiraBatchLog();
		log.setStartDate(LocalDateTime.now());
		
		when(fetchService.fetch(any(), anyInt())).thenReturn(emptyUserStories);
		
		service.startBatchProcess(log);
		
		verify(fetchService, times(1)).fetch(any(), anyInt());
		verify(processService, never()).process(any());
		verify(storeService, never()).store(any());
	}

	@Test
	public void should_call_processor_and_store_same_times_minus_one_than_fetch_is_called() {
		List<JiraUserStory> notEmptyUserStories = new ArrayList<>();
		notEmptyUserStories.add(new JiraUserStory());
		List<JiraUserStory> emptyUserStories = new ArrayList<>();
		Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> stats = new HashMap<>();
		stats.put(new SprintSummaryStatsDimension(), new SprintSummaryStatsMetrics());
		JiraBatchLog log = new JiraBatchLog();
		log.setStartDate(LocalDateTime.now());
		
		when(fetchService.fetch(any(), eq(0))).thenReturn(notEmptyUserStories);
		when(fetchService.fetch(any(), eq(1))).thenReturn(notEmptyUserStories);
		when(fetchService.fetch(any(), eq(2))).thenReturn(notEmptyUserStories);
		when(fetchService.fetch(any(), eq(3))).thenReturn(emptyUserStories);
		when(processService.process(eq(notEmptyUserStories))).thenReturn(stats);
		
		service.startBatchProcess(log);
		
		verify(fetchService, times(4)).fetch(any(), anyInt());
		verify(processService, times(3)).process(eq(notEmptyUserStories));
		verify(storeService, times(3)).store(eq(stats));
	}
	
}
