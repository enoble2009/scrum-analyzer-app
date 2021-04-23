package com.daylight.devleague.services.stats.starters;

import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.dto.jira.JiraBatchEntity;
import com.daylight.devleague.services.stats.JiraStatsBatchStarterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatsUsersBatchStarterService extends JiraStatsBatchStarterService {

	@Override
	public JiraBatchEntity getEntity() {
		return JiraBatchEntity.USERS;
	}

	@Override
	public void startBatchProcess(JiraBatchLog log) {
		// TODO: Create this starter processing.
	}

}
