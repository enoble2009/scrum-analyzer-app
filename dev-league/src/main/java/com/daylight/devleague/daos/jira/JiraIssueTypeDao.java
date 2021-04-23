package com.daylight.devleague.daos.jira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraIssueType;

@Repository
public interface JiraIssueTypeDao extends JpaRepository<JiraIssueType, Long> {

	public boolean existsByExternalId(Long externalId);
	public Optional<JiraIssueType> findByExternalId(Long externalId);
	
}
