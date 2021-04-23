package com.daylight.devleague.services.stats.store;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.stats.SprintSummaryStatsDimensionDao;
import com.daylight.devleague.daos.stats.SprintSummaryStatsMetricsDao;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatsUserStoriesStoreService {

	private final SprintSummaryStatsDimensionDao sprintSummaryDimensionDao;
	private final SprintSummaryStatsMetricsDao sprintSummaryMetricsDao;

	public void store(Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> summaryStats) {
		summaryStats.forEach((dimension, metrics) -> {
			SprintSummaryStatsDimension actualDimension = searchDimension(dimension);
			SprintSummaryStatsMetrics actualMetrics = searchMetrics(metrics, actualDimension);
			updateDimension(dimension, actualDimension);
			updateMetrics(metrics, actualMetrics);
		});
	}

	private void updateDimension(SprintSummaryStatsDimension dimension, SprintSummaryStatsDimension actualDimension) {
		actualDimension.setIssueId(dimension.getIssueId());
		actualDimension.setSprintId(dimension.getSprintId());
		actualDimension.setClientId(dimension.getClientId());
		actualDimension.setStatusId(dimension.getStatusId());
		actualDimension.setIssueType(dimension.getIssueType());
		
		sprintSummaryDimensionDao.save(actualDimension);
	}

	private void updateMetrics(SprintSummaryStatsMetrics metrics, SprintSummaryStatsMetrics actualMetrics) {
		actualMetrics.setIssuesQuantity(metrics.getIssuesQuantity());
		actualMetrics.setStoryPoints(metrics.getStoryPoints());
		actualMetrics.setDevHours(metrics.getDevHours());
		actualMetrics.setQaHours(metrics.getQaHours());
		actualMetrics.setManageHours(metrics.getManageHours());

		actualMetrics.setPreviousIssuesQuantity(metrics.getPreviousIssuesQuantity());
		actualMetrics.setPreviousStoryPoints(metrics.getPreviousStoryPoints());
		actualMetrics.setPreviousDevHours(metrics.getPreviousDevHours());
		actualMetrics.setPreviousQaHours(metrics.getPreviousQaHours());
		actualMetrics.setPreviousManageHours(metrics.getPreviousManageHours());

		sprintSummaryMetricsDao.save(actualMetrics);
	}

	private SprintSummaryStatsMetrics searchMetrics(SprintSummaryStatsMetrics metrics,
			SprintSummaryStatsDimension actualDimension) {
		SprintSummaryStatsMetrics actualMetrics = sprintSummaryMetricsDao.findById(actualDimension.getId()).orElse(null);
		if (actualMetrics == null) {
			actualMetrics = metrics;
			actualMetrics.setId(actualDimension.getId());
		}
		return actualMetrics;
	}

	private SprintSummaryStatsDimension searchDimension(SprintSummaryStatsDimension dimension) {
		return sprintSummaryDimensionDao
				.findByIssueId(dimension.getIssueId())
				.orElse(sprintSummaryDimensionDao.save(dimension));
	}

}
