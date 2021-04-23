package com.daylight.devleague.services.stats.fetch;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.gitlab.GitLabCommitDao;
import com.daylight.devleague.domain.gitlab.GitLabCommit;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabStatsCommitsFetchService {

	private static final int MAX_PER_PAGE = 100;
	
	private final GitLabCommitDao dao;
	
	public List<GitLabCommit> fetch(LocalDateTime startDate, int page) {
		Pageable pageable = PageRequest.of(page, MAX_PER_PAGE);
		if (startDate != null) {
			return dao.findByCreatedDateAfter(startDate, pageable).getContent();
		} else {
			return dao.findAll(pageable).getContent();
		}
	}
	
}
