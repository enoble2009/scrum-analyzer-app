package com.daylight.devleague.daos.jira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraClient;

@Repository
public interface JiraClientDao extends JpaRepository<JiraClient, Long> {

	public boolean existsByExternalId(Integer externalId);
	public Optional<JiraClient> findByExternalId(Integer externalId);
	
}
