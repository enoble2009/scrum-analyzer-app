package com.daylight.devleague.controllers.projects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.dto.projects.GetProjectsResponseDTO;
import com.daylight.devleague.services.projects.ProjectDataService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectDataController {

	private final ProjectDataService service;
	
	@GetMapping("projects")
	public GetProjectsResponseDTO getProjects() {
		GetProjectsResponseDTO dto = new GetProjectsResponseDTO();
		dto.setProjects(service.getProjects());
		return dto;
	}
	
}
