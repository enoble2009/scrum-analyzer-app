package com.daylight.devleague.daos.gitlab;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.gitlab.GitLabBatchLog;
import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;

@Repository
public interface GitLabBatchLogDao extends JpaRepository<GitLabBatchLog, Long> {

	public Optional<GitLabBatchLog> findFirstByEntityAndCompletedOrderByIdDesc(GitLabBatchEntity entity, Boolean completed);
	public Optional<GitLabBatchLog> findFirstByStatsUpdatedOrderByIdAsc(Boolean statsUpdated);
	
}
