package com.daylight.devleague.services.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.daylight.devleague.dto.dashboard.ClientSummaryBoxDTO;
import com.daylight.devleague.dto.dashboard.DashboardUserStoryDTO;
import com.daylight.devleague.dto.dashboard.GetSprintsVelocityDTO;
import com.daylight.devleague.dto.dashboard.GetSummaryResponseDTO;
import com.daylight.devleague.dto.dashboard.MainSummaryBoxDTO;
import com.daylight.devleague.dto.projects.ClientIdAndNameDTO;
import com.daylight.devleague.dto.projects.CurrentAndPreviousSprintsDTO;
import com.daylight.devleague.services.jira.JiraStatusService;
import com.daylight.devleague.services.projects.ProjectDataService;
import com.daylight.devleague.services.stats.StatsSummaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final StatsSummaryService statsSummaryService;
	
	private final ProjectDataService projectDataService;
	
	private final JiraStatusService statusService;

	public GetSummaryResponseDTO getSummary(Long projectId) {
		CurrentAndPreviousSprintsDTO sprints = projectDataService.getCurrentAndPreviousSprint(projectId);
		List<Long> inProgressStatus = statusService.getInProgressStatusIds();
		List<Long> finishedStatus = statusService.getFinishedStatusIds();

		MainSummaryBoxDTO main = statsSummaryService.getSummaryBySprint(sprints, inProgressStatus, finishedStatus);
		List<ClientSummaryBoxDTO> clients = generateClientsSummary(projectId, sprints, inProgressStatus, finishedStatus);
		
		return GetSummaryResponseDTO.builder().main(main).clients(clients).build();
	}
	
	private List<ClientSummaryBoxDTO> generateClientsSummary(Long projectId, CurrentAndPreviousSprintsDTO sprints, List<Long> inProgressStatus, List<Long> finishedStatus) {
		List<ClientIdAndNameDTO> allClientsFromProject = projectDataService.getClientsIdAndName(projectId);
		return allClientsFromProject.stream().map(client -> {
			return statsSummaryService.getSummaryBySprintAndClient(sprints, client, inProgressStatus, finishedStatus);
		}).collect(Collectors.toList());
	}

	public List<DashboardUserStoryDTO> getUserStories(Long projectId) {
		CurrentAndPreviousSprintsDTO sprints = projectDataService.getCurrentAndPreviousSprint(projectId);
		if (sprints.getCurrentSprint() == null) return null;
		
		return statsSummaryService.getUserStoriesSummary(sprints.getCurrentSprint());
	}

	public GetSprintsVelocityDTO getSprintsVelocity(Long projectId, Long clientId) {
		List<Long> statuses = statusService.getFinishedStatusIds();
		
		return statsSummaryService.getSprintsVelocity(projectId, clientId, statuses);
	}

}
