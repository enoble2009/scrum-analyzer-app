package com.daylight.devleague.daos.stats;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.stats.dimensions.CommitSummaryStatsDimension;

@Repository
public interface CommitSummaryStatsDimensionDao extends JpaRepository<CommitSummaryStatsDimension, Long> {

	public Optional<CommitSummaryStatsDimension> findByIssueIdAndSprintIdAndClientIdAndStoryPointsAndProjectIdAndCommitId(Long issueId, Long sprintId, Long clientId, Byte storyPoints, Long projectId, Long commitId);
	
}
