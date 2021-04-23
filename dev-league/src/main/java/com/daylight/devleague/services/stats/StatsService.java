package com.daylight.devleague.services.stats;

import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.services.gitlab.GitLabBatchLogService;
import com.daylight.devleague.services.jira.JiraBatchLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatsService {

	private final JiraBatchLogService jiraLogService;
	private final GitLabBatchLogService gitLabLogService;

	private final StatsBatchFactory batchServers;


	public void updateJiraStats() {
		JiraBatchLog batchLog = jiraLogService.popFirstLogToUpdateStats();
		if (batchLog == null) {
			log.debug("There are not new batch logs to update stats.");
			return;
		}

		try {
			JiraStatsBatchStarterService starter = batchServers.getStarter(batchLog.getEntity());
			starter.startBatchProcess(batchLog);
		} catch (Throwable e) {
			log.error("Error when working on stats batch", e);
			jiraLogService.rollbackStatsUpdate(batchLog);
		}
	}

	public void updateGitLabStats() {
		GitLabBatchLog batchLog = gitLabLogService.popFirstLogToUpdateStats();
		if (batchLog == null) {
			log.debug("There are not new batch logs to update stats.");
			return;
		}

		try {
			GitLabStatsBatchStarterService starter = batchServers.getStarter(batchLog.getEntity());
			starter.startBatchProcess(batchLog);
		} catch (Throwable e) {
			log.error("Error when working on stats batch", e);
			gitLabLogService.rollbackStatsUpdate(batchLog);
		}
	}

}
