package com.daylight.devleague.services.jira.store;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.dto.jira.JiraClientDTO;
import com.daylight.devleague.dto.jira.JiraSprintDTO;
import com.daylight.devleague.services.jira.JiraStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraBugsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraEpicsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraSupportTicketsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraTasksStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraUserStoriesStoreService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class JiraIssuesStoreService extends JiraStoreService<Issue> {

	private final JiraSprintsStoreService sprintsStoreService;
	private final JiraStatusesStoreService statusesStoreService;
	private final JiraTypesStoreService typesStoreService;
	private final JiraEpicsStoreService epicsStoreService;
	private final JiraUserStoriesStoreService userStoriesStoreService;
	private final JiraTasksStoreService tasksStoreService;
	private final JiraBugsStoreService bugsStoreService;
	private final JiraSupportTicketsStoreService supportTicketsStoreService;
	private final JiraClientsStoreService clientsStoreService;

	@Override
	public void store(Stream<Issue> issuesStream) {
		List<Issue> issues = issuesStream.collect(Collectors.toList());
		if (issues.isEmpty()) {
			return;
		}
		try {
			statusesStoreService.store(issues.stream().map(Issue::getStatus).distinct());
			typesStoreService.store(issues.stream().map(Issue::getIssueType).distinct());
			sprintsStoreService.store(issues.stream().flatMap(issue -> getSprintFromFields("Sprint", issue))
					.filter(sprint -> sprint != null).distinct());
			clientsStoreService.store(issues.stream().map(issue -> getClientFromField(issue)).filter(client -> client != null).distinct());
			epicsStoreService.store(issues.stream().filter(issue -> isEpic(issue)));
			tasksStoreService.store(issues.stream().filter(issue -> isTask(issue)));
			bugsStoreService.store(issues.stream().filter(issue -> isBug(issue)));
			userStoriesStoreService.store(issues.stream().filter(issue -> isUserStory(issue)));
			supportTicketsStoreService.store(issues.stream().filter(issue -> isSupport(issue)));

			log.debug("Issues batch section store completed.");
		} catch (Throwable e) {
			log.error("Error when loading issues in batch process.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Stream<JiraSprintDTO> getSprintFromFields(String fieldName, Issue issue) {
		Type listType = new TypeToken<List<JiraSprintDTO>>() { }.getType();
		String arrayJson = issue.getFieldByName(fieldName).getValue().toString();
		List<JiraSprintDTO> sprints = (List<JiraSprintDTO>) new Gson().fromJson(arrayJson, listType);
		return sprints.stream();
	}

	protected JiraClientDTO getClientFromField(Issue dto) {
		if (dto.getFieldByName("Operador").getValue() == null) return null;
		
		String json = dto.getFieldByName("Operador").getValue().toString();
		JiraClientDTO client = (JiraClientDTO) new Gson().fromJson(json, JiraClientDTO.class);
		return client;
	}

	private boolean isTask(Issue issue) {
		return hasIssueType(issue, "Subtarea");
	}

	private boolean isBug(Issue issue) {
		return hasIssueType(issue, "Error");
	}

	private boolean isSupport(Issue issue) {
		return hasIssueType(issue, "Soporte");
	}

	private boolean isUserStory(Issue issue) {
		return hasIssueType(issue, "Historia Desarrollo");
	}

	private boolean isEpic(Issue issue) {
		return hasIssueType(issue, "Épica");
	}

	private boolean hasIssueType(Issue issue, String status) {
		return issue.getIssueType().getName().equals(status);
	}

}
