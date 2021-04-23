package com.daylight.devleague.services.gitlab.store;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daylight.devleague.domain.gitlab.GitLabProject;
import com.daylight.devleague.dto.gitlab.GitLabCommitDTO;
import com.daylight.devleague.services.gitlab.GitLabCommitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabCommitsStoreService {
	
	private final GitLabCommitService service;
	
	public void store(List<GitLabCommitDTO> commits, GitLabProject project) {
		commits.forEach(commit -> {
			service.save(commit, project);
		});
	}

}
