package com.daylight.devleague.controllers.gitlab;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.services.gitlab.GitLabBatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GitLabDataController {

	private final GitLabBatchService service;
	
	@PostMapping("gitlab")
	public void updateGitLab() throws Exception {
		service.load();
	}
	
}
