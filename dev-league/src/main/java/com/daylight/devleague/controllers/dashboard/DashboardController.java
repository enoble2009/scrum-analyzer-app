package com.daylight.devleague.controllers.dashboard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.dto.dashboard.GetSprintsVelocityDTO;
import com.daylight.devleague.dto.dashboard.GetSummaryResponseDTO;
import com.daylight.devleague.dto.dashboard.GetUserStoriesResponseDTO;
import com.daylight.devleague.services.dashboard.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService service;
	
	@GetMapping("dashboard/summary")
	public GetSummaryResponseDTO getSummary(@RequestParam Long projectId) {
		return service.getSummary(projectId);
	}
	
	@GetMapping("dashboard/sprints-velocity")
	public GetSprintsVelocityDTO getSprintsVelocity(@RequestParam Long projectId,
			@RequestParam(required = false) Long clientId) {
		return service.getSprintsVelocity(projectId, clientId);
	}
	
	@GetMapping("dashboard/user-stories")
	public GetUserStoriesResponseDTO getUserStores(@RequestParam Long projectId) {
		return GetUserStoriesResponseDTO.builder()
				.userStories(service.getUserStories(projectId))
				.build();
	}
	
}
