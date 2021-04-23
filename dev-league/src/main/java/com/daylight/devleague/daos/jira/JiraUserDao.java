package com.daylight.devleague.daos.jira;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraUser;

@Repository
public interface JiraUserDao extends JpaRepository<JiraUser, Long> {

	public Optional<JiraUser> findByExternalId(String externalId);
	
}
