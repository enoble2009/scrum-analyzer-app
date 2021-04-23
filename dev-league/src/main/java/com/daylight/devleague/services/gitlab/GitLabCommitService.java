package com.daylight.devleague.services.gitlab;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.gitlab.GitLabCommitDao;
import com.daylight.devleague.domain.gitlab.GitLabCommit;
import com.daylight.devleague.domain.gitlab.GitLabProject;
import com.daylight.devleague.dto.gitlab.GitLabCommitDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabCommitService {

	private final GitLabCommitDao dao;
	
	public void save(GitLabCommitDTO dto, GitLabProject project) {
		GitLabCommit commit = GitLabCommit.builder()
				.externalId(dto.getExternalId())
				.code(dto.getCode())
				.title(dto.getTitle())
				.email(dto.getAuthor())
				.createdDate(dto.getCreatedDate())
				.createdLines(dto.getCreatedLines())
				.removedLines(dto.getRemovedLines())
				.totalLines(dto.getTotalLines())
				.project(project)
				.build();
		
		dao.save(commit);
	}

}
