package com.daylight.devleague.services.stats.store;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.daos.stats.SprintSummaryStatsDimensionDao;
import com.daylight.devleague.daos.stats.SprintSummaryStatsMetricsDao;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.dimensions.StatsIssueType;
import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;

@ExtendWith(MockitoExtension.class)
public class JiraStatsUserStoriesStoreServiceTest {

	@InjectMocks
	private JiraStatsUserStoriesStoreService service;

	@Mock private SprintSummaryStatsDimensionDao sprintSummaryDimensionDao;
	@Mock private SprintSummaryStatsMetricsDao sprintSummaryMetricsDao;

	@BeforeEach
	public void before() {
		service = new JiraStatsUserStoriesStoreService(sprintSummaryDimensionDao, sprintSummaryMetricsDao);
	}

	@Test
	public void should_not_do_anything_when_stats_are_empty() {
		Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats = new HashMap<>();
		
		service.store(summaryStats);
		
		verify(sprintSummaryDimensionDao, never()).findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any());
		verify(sprintSummaryDimensionDao, never()).save(any());
		verify(sprintSummaryMetricsDao, never()).findById(anyLong());
		verify(sprintSummaryMetricsDao, never()).save(any());
	}

	@Test
	public void should_save_dimension_when_dimension_in_stats_not_exists() {
		Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats = new HashMap<>();
		SprintSummaryStatsDimension dimension = new SprintSummaryStatsDimension(1l, 1l, 1l, 1l, 1l, 1l, StatsIssueType.USER_STORY);
		SprintSummaryStatsMetrics metrics = new SprintSummaryStatsMetrics();
		summaryStats.put(dimension, metrics);
		
		when(sprintSummaryDimensionDao.findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any()))
			.thenReturn(Optional.empty());
		when(sprintSummaryDimensionDao.save(eq(dimension)))
			.thenReturn(dimension);
		
		service.store(summaryStats);
		
		verify(sprintSummaryDimensionDao, times(1)).findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any());
		verify(sprintSummaryDimensionDao, times(1)).save(eq(dimension));
		verify(sprintSummaryMetricsDao, times(1)).findById(anyLong());
		verify(sprintSummaryMetricsDao, times(1)).save(any());
	}

	@Test
	public void should_not_set_metrics_id_when_metrics_exists() {
		Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats = new HashMap<>();
		SprintSummaryStatsDimension dimension = new SprintSummaryStatsDimension(1l, 1l, 1l, 1l, 1l, 1l, StatsIssueType.USER_STORY);
		SprintSummaryStatsMetrics metrics = new SprintSummaryStatsMetrics();
		metrics.setId(124l);
		summaryStats.put(dimension, metrics);
		
		when(sprintSummaryDimensionDao.findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any()))
			.thenReturn(Optional.of(dimension));
		when(sprintSummaryMetricsDao.findById(anyLong()))
			.thenReturn(Optional.of(metrics));
		
		service.store(summaryStats);
		
		verify(sprintSummaryDimensionDao, times(1)).findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any());
		verify(sprintSummaryDimensionDao, never()).save(eq(dimension));
		verify(sprintSummaryMetricsDao, times(1)).findById(anyLong());
		verify(sprintSummaryMetricsDao, times(1)).save(any());
		assertEquals(new Long(124l), metrics.getId());
	}

	@Test
	public void should_set_metrics_id_when_metrics_not_exists() {
		Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats = new HashMap<>();
		SprintSummaryStatsDimension dimension = new SprintSummaryStatsDimension(1l, 1l, 1l, 1l, 1l, 1l, StatsIssueType.USER_STORY);
		SprintSummaryStatsMetrics metrics = new SprintSummaryStatsMetrics();
		metrics.setId(124l);
		summaryStats.put(dimension, metrics);
		
		when(sprintSummaryDimensionDao.findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any()))
			.thenReturn(Optional.of(dimension));
		when(sprintSummaryMetricsDao.findById(anyLong()))
			.thenReturn(Optional.empty());
		
		service.store(summaryStats);
		
		verify(sprintSummaryDimensionDao, times(1)).findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(anyLong(), anyLong(), anyLong(), anyLong(), any());
		verify(sprintSummaryDimensionDao, never()).save(eq(dimension));
		verify(sprintSummaryMetricsDao, times(1)).findById(anyLong());
		verify(sprintSummaryMetricsDao, times(1)).save(any());
		assertEquals(dimension.getId(), metrics.getId());
	}
	
}
