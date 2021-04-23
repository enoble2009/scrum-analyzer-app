package com.daylight.devleague.daos.gitlab;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.gitlab.GitLabProject;

@Repository
public interface GitLabProjectDao extends JpaRepository<GitLabProject, Long> {

	@Query("SELECT externalId FROM GitLabProject")
	List<Long> getAllExternalIds();

}
