package com.daylight.devleague.services.stats.starters;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.domain.gitlab.GitLabCommit;
import com.daylight.devleague.domain.stats.dimensions.CommitSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.CommitSummaryStatsMetrics;
import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;
import com.daylight.devleague.services.stats.GitLabStatsBatchStarterService;
import com.daylight.devleague.services.stats.fetch.GitLabStatsCommitsFetchService;
import com.daylight.devleague.services.stats.process.GitLabStatsCommitsProcessService;
import com.daylight.devleague.services.stats.store.GitLabStatsCommitsStoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabStatsCommitsBatchStarterService extends GitLabStatsBatchStarterService {

	private final GitLabStatsCommitsFetchService fetchService;
	private final GitLabStatsCommitsProcessService processService;
	private final GitLabStatsCommitsStoreService storeService;
	
	@Override
	public GitLabBatchEntity getEntity() {
		return GitLabBatchEntity.COMMITS;
	}

	@Override
	public void startBatchProcess(GitLabBatchLog log) {
		int page = 0;
		List<GitLabCommit> commits;
		while (!(commits = fetchService.fetch(log.getStartDate(), page)).isEmpty()) {
			Map<CommitSummaryStatsDimension, CommitSummaryStatsMetrics> summaryStats = processService.process(commits);
			storeService.store(summaryStats);
			page++;
		}
	}

}
