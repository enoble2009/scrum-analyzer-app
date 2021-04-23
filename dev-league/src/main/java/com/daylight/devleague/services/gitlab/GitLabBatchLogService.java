package com.daylight.devleague.services.gitlab;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.gitlab.GitLabBatchLogDao;
import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;

@Service
public class GitLabBatchLogService {

	@Autowired
	private GitLabBatchLogDao dao;

	public GitLabBatchLog initialize(GitLabBatchEntity entity) {
		GitLabBatchLog previousLog = dao.findFirstByEntityAndCompletedOrderByIdDesc(entity, true).orElse(null);
		LocalDateTime startDate = null;
		if (previousLog != null) {
			startDate = previousLog.getRunDate();
		}

		return GitLabBatchLog.builder().runDate(LocalDateTime.now()).startDate(startDate).entity(entity).pages(0)
				.completed(false).statsUpdated(false).build();
	}
	
	public GitLabBatchLog popFirstLogToUpdateStats() {
		GitLabBatchLog log = dao.findFirstByStatsUpdatedOrderByIdAsc(false).orElse(null);
		if (log != null) {
			log.setStatsUpdated(true);
			save(log);
		}
		return log;
	}
	
	public void rollbackStatsUpdate(GitLabBatchLog log) {
		log.setStatsUpdated(false);
		save(log);
	}

	public void save(GitLabBatchLog log) {
		dao.save(log);
	}

}
