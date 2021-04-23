package com.daylight.devleague.daos.jira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraStatus;

@Repository
public interface JiraStatusDao extends JpaRepository<JiraStatus, Long> {

	public boolean existsByExternalId(Long externalId);
	public Optional<JiraStatus> findByExternalId(Long externalId);
	
}
