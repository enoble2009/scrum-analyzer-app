package com.daylight.devleague.services.projects;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.projects.ProjectDao;
import com.daylight.devleague.dto.projects.BasicProjectDataDTO;
import com.daylight.devleague.dto.projects.ClientIdAndNameDTO;
import com.daylight.devleague.dto.projects.CurrentAndPreviousSprintsDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDataService {

	private final ProjectDao dao;
	
	public List<BasicProjectDataDTO> getProjects() {
		return dao.getProjects();
	}
	
	public CurrentAndPreviousSprintsDTO getCurrentAndPreviousSprint(Long projectId) {
		List<Long> lastSprints = dao.getLastSprints(projectId, PageRequest.of(0, 2)).getContent();
		Long currentSprint = !lastSprints.isEmpty()? lastSprints.get(0): null;
		Long previousSprint = lastSprints.size() > 1? lastSprints.get(1): null;
		
		return CurrentAndPreviousSprintsDTO.builder()
				.previousSprint(previousSprint)
				.currentSprint(currentSprint)
				.build();
	}
	
	public List<ClientIdAndNameDTO> getClientsIdAndName(Long projectId) {
		List<ClientIdAndNameDTO> clients = dao.getClientsIdAndName(projectId);
		return clients;
	}
	
}
