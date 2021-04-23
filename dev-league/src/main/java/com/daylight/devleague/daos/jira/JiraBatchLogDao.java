package com.daylight.devleague.daos.jira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.dto.jira.JiraBatchEntity;

@Repository
public interface JiraBatchLogDao extends JpaRepository<JiraBatchLog, Long> {

	public Optional<JiraBatchLog> findFirstByEntityAndCompletedOrderByIdDesc(JiraBatchEntity entity, Boolean completed);
	public Optional<JiraBatchLog> findFirstByStatsUpdatedOrderByIdAsc(Boolean statsUpdated);
	
}
