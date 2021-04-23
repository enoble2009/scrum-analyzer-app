package com.daylight.devleague.services.gitlab;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.gitlab.GitLabProjectDao;
import com.daylight.devleague.domain.gitlab.GitLabProject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabProjectService {

	private final GitLabProjectDao dao;
	
	public List<GitLabProject> getProjects() {
		return dao.findAll();
	}

	public List<Long> getProjectsExternalIds() {
		return dao.getAllExternalIds();
	}

}
