package com.daylight.devleague.daos.jira;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.daylight.devleague.domain.jira.JiraIssue;

public abstract interface JiraIssueDao<T extends JiraIssue, H extends Serializable> extends PagingAndSortingRepository<T, H> {

	public boolean existsByExternalId(Long externalId);
	public Optional<T> findByExternalId(Long externalId);
	public Optional<T> findByKey(String key);
	public Page<T> findByLastUpdateAfter(LocalDateTime lastUpdate, Pageable pageable);

}
