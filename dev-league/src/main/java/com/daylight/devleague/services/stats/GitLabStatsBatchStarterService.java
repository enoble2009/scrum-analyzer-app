package com.daylight.devleague.services.stats;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;

public abstract class GitLabStatsBatchStarterService {

	public abstract GitLabBatchEntity getEntity();
	public abstract void startBatchProcess(GitLabBatchLog log);
	
}
