package com.daylight.devleague.controllers.commits;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommitsController {

	@GetMapping("commits/summary")
	public void getCommitsSummary() {
		
	}
	
}
