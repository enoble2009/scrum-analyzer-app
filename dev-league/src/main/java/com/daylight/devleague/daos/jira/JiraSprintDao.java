package com.daylight.devleague.daos.jira;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.jira.JiraSprint;

@Repository
public interface JiraSprintDao extends JpaRepository<JiraSprint, Long> {

	public boolean existsByExternalId(Integer externalId);
	public List<JiraSprint> findByExternalIdIn(List<Integer> externalId);
	
}
