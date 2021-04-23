package com.daylight.devleague.daos.gitlab;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.gitlab.GitLabCommit;

@Repository
public interface GitLabCommitDao extends PagingAndSortingRepository<GitLabCommit, Long> {

	public Page<GitLabCommit> findByCreatedDateAfter(LocalDateTime createdDate, Pageable pageable);
	
}
