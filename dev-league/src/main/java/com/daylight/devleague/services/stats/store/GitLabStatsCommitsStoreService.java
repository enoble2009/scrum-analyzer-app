package com.daylight.devleague.services.stats.store;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.stats.CommitSummaryStatsDimensionDao;
import com.daylight.devleague.daos.stats.CommitSummaryStatsMetricsDao;
import com.daylight.devleague.domain.stats.dimensions.CommitSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.CommitSummaryStatsMetrics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabStatsCommitsStoreService {

	private final CommitSummaryStatsDimensionDao commitSummaryDimensionDao;
	private final CommitSummaryStatsMetricsDao commitSummaryMetricsDao;

	public void store(Map<CommitSummaryStatsDimension, CommitSummaryStatsMetrics> summaryStats) {
		summaryStats.forEach((dimension, metrics) -> {
			CommitSummaryStatsDimension actualDimension = searchDimension(dimension);
			CommitSummaryStatsMetrics actualMetrics = searchMetrics(metrics, actualDimension);
			updateMetrics(metrics, actualMetrics);
		});
	}

	private void updateMetrics(CommitSummaryStatsMetrics metrics, CommitSummaryStatsMetrics actualMetrics) {
		actualMetrics.setCreatedLines(metrics.getCreatedLines());
		actualMetrics.setRemovedLines(metrics.getRemovedLines());
		actualMetrics.setTotalLines(metrics.getTotalLines());

		commitSummaryMetricsDao.save(actualMetrics);
	}

	private CommitSummaryStatsMetrics searchMetrics(CommitSummaryStatsMetrics metrics,
			CommitSummaryStatsDimension actualDimension) {
		CommitSummaryStatsMetrics actualMetrics = commitSummaryMetricsDao.findById(actualDimension.getId()).orElse(null);
		if (actualMetrics == null) {
			actualMetrics = metrics;
			actualMetrics.setId(actualDimension.getId());
		}
		return actualMetrics;
	}

	private CommitSummaryStatsDimension searchDimension(CommitSummaryStatsDimension dimension) {
		return commitSummaryDimensionDao
				.findByIssueIdAndSprintIdAndClientIdAndStoryPointsAndProjectIdAndCommitId(dimension.getIssueId(),
						dimension.getSprintId(), dimension.getClientId(), dimension.getStoryPoints(),
						dimension.getProjectId(), dimension.getCommitId())
				.orElse(commitSummaryDimensionDao.save(dimension));
	}

}
