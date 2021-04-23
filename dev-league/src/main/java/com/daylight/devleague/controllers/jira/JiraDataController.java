package com.daylight.devleague.controllers.jira;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.services.jira.JiraBatchService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JiraDataController {

	private final JiraBatchService batchService;
	
	@PostMapping("jira/users")
	public void fetchUsers() throws Exception {
		batchService.loadAllUsers();
	}

	@PostMapping("jira/issues")
	public void fetchSprints() throws Exception {
		batchService.loadAllSprints();
	}
	
}
