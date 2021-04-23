package com.daylight.devleague.services.stats.starters;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;
import com.daylight.devleague.dto.jira.JiraBatchEntity;
import com.daylight.devleague.services.stats.JiraStatsBatchStarterService;
import com.daylight.devleague.services.stats.fetch.JiraStatsUserStoriesFetchService;
import com.daylight.devleague.services.stats.process.JiraStatsUserStoriesProcessService;
import com.daylight.devleague.services.stats.store.JiraStatsUserStoriesStoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatsIssuesBatchStarterService extends JiraStatsBatchStarterService {

	private final JiraStatsUserStoriesFetchService fetchService;
	private final JiraStatsUserStoriesProcessService processService;
	private final JiraStatsUserStoriesStoreService storeService;
	
	@Override
	public JiraBatchEntity getEntity() {
		return JiraBatchEntity.ISSUES;
	}

	@Override
	public void startBatchProcess(JiraBatchLog log) {
		int page = 0;
		List<JiraUserStory> userStories;
		while (!(userStories = fetchService.fetch(log.getStartDate(), page)).isEmpty()) {
			Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats = processService.process(userStories);
			storeService.store(summaryStats);
			page++;
		}
	}

}
