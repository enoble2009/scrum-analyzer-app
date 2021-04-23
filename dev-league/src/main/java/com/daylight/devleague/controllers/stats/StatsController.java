package com.daylight.devleague.controllers.stats;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.services.stats.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StatsController {

	private final StatsService service;
	
	@PostMapping("stats/gitlab/refresh")
	public void refreshGitLabStats() {
		service.updateGitLabStats();
	}
	
	@PostMapping("stats/jira/refresh")
	public void refreshJiraStats() {
		service.updateJiraStats();
	}
	
}
