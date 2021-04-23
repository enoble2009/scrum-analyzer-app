package com.daylight.devleague.services.jira;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.JiraBatchLogDao;
import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.dto.jira.JiraBatchEntity;

@Service
public class JiraBatchLogService {

	@Autowired
	private JiraBatchLogDao dao;

	public JiraBatchLog initialize(JiraBatchEntity entity) {
		JiraBatchLog previousLog = dao.findFirstByEntityAndCompletedOrderByIdDesc(entity, true).orElse(null);
		LocalDateTime startDate = null;
		if (previousLog != null) {
			startDate = previousLog.getRunDate();
		}

		return JiraBatchLog.builder().runDate(LocalDateTime.now()).startDate(startDate).entity(entity).pages(0)
				.completed(false).statsUpdated(false).build();
	}
	
	public JiraBatchLog popFirstLogToUpdateStats() {
		JiraBatchLog log = dao.findFirstByStatsUpdatedOrderByIdAsc(false).orElse(null);
		if (log != null) {
			log.setStatsUpdated(true);
			save(log);
		}
		return log;
	}
	
	public void rollbackStatsUpdate(JiraBatchLog log) {
		log.setStatsUpdated(false);
		save(log);
	}

	public void save(JiraBatchLog log) {
		dao.save(log);
	}

}
