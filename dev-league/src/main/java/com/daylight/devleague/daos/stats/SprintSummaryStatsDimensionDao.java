package com.daylight.devleague.daos.stats;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.dimensions.StatsIssueType;

@Repository
public interface SprintSummaryStatsDimensionDao extends JpaRepository<SprintSummaryStatsDimension, Long> {

	public Optional<SprintSummaryStatsDimension> findByIssueIdAndSprintIdAndStatusIdAndClientIdAndIssueType(Long issueId, Long sprintId, Long statusId, Long clientId, StatsIssueType issueType);
	public Optional<SprintSummaryStatsDimension> findByIssueId(Long issueId);
	
}
