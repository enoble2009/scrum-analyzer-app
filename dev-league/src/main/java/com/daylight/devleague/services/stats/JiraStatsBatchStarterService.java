package com.daylight.devleague.services.stats;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.dto.jira.JiraBatchEntity;

public abstract class JiraStatsBatchStarterService {

	public abstract JiraBatchEntity getEntity();
	public abstract void startBatchProcess(JiraBatchLog log);
	
}
