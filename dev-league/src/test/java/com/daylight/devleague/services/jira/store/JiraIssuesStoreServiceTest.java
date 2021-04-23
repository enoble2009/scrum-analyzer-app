package com.daylight.devleague.services.jira.store;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.daylight.devleague.services.jira.store.issues.JiraBugsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraEpicsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraSupportTicketsStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraTasksStoreService;
import com.daylight.devleague.services.jira.store.issues.JiraUserStoriesStoreService;
import com.daylight.devleague.utils.jira.JiraValuesUtils;

@ExtendWith(MockitoExtension.class)
public class JiraIssuesStoreServiceTest {

	@InjectMocks private JiraIssuesStoreService service;
	
	@Mock private JiraSprintsStoreService sprintsStoreService;
	@Mock private JiraStatusesStoreService statusesStoreService;
	@Mock private JiraTypesStoreService typesStoreService;
	@Mock private JiraEpicsStoreService epicsStoreService;
	@Mock private JiraUserStoriesStoreService userStoriesStoreService;
	@Mock private JiraTasksStoreService tasksStoreService;
	@Mock private JiraBugsStoreService bugsStoreService;
	@Mock private JiraSupportTicketsStoreService supportTicketsStoreService;
	@Mock private JiraClientsStoreService clientsStoreService;
	
	@BeforeEach
	public void before() {
		mock(JiraValuesUtils.class);
		service = new JiraIssuesStoreService(sprintsStoreService, statusesStoreService, typesStoreService,
				epicsStoreService, userStoriesStoreService, tasksStoreService, bugsStoreService, supportTicketsStoreService,
				clientsStoreService);
	}
	
	@Test
	public void should_not_store_when_list_is_empty() {
		Stream<Issue> issues = new ArrayList<Issue>().stream();
		
		service.store(issues);
		
		verify(sprintsStoreService, never()).store(any());
		verify(statusesStoreService, never()).store(any());
		verify(typesStoreService, never()).store(any());
		verify(epicsStoreService, never()).store(any());
		verify(userStoriesStoreService, never()).store(any());
		verify(tasksStoreService, never()).store(any());
		verify(bugsStoreService, never()).store(any());
		verify(supportTicketsStoreService, never()).store(any());
		verify(clientsStoreService, never()).store(any());
	}
	
	@Test
	public void should_call_all_stores_when_list_is_not_empty() {
		Status status = new Status(null, 1l, "Name", "Description", null, null);
		IssueType type1 = new IssueType(null, 1l, "Épica", false, "Description", null);
		IssueType type2 = new IssueType(null, 1l, "Subtarea", false, "Description", null);
		IssueType type3 = new IssueType(null, 1l, "Soporte", false, "Description", null);
		IssueType type4 = new IssueType(null, 1l, "Historia Desarrollo", false, "Description", null);
		IssueType type5 = new IssueType(null, 1l, "Error", false, "Description", null);
		Issue us1 = new Issue(null, null, "PG-XXX1", 1l, null, type1, status, "Description", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		Issue us2 = new Issue(null, null, "PG-XXX2", 1l, null, type2, status, "Description", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		Issue us3 = new Issue(null, null, "PG-XXX3", 1l, null, type3, status, "Description", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		Issue us4 = new Issue(null, null, "PG-XXX4", 1l, null, type4, status, "Description", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		Issue us5 = new Issue(null, null, "PG-XXX5", 1l, null, type5, status, "Description", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

		List<Status> statuses = new ArrayList<Status>();
		statuses.add(status);

		List<Issue> issues = new ArrayList<Issue>();
		issues.add(us1);
		issues.add(us2);
		issues.add(us3);
		issues.add(us4);
		issues.add(us5);
		
		doNothing().when(statusesStoreService).store(any());
		doNothing().when(typesStoreService).store(any());
		doNothing().when(sprintsStoreService).store(any());
		doNothing().when(epicsStoreService).store(any());
		doNothing().when(tasksStoreService).store(any());
		doNothing().when(bugsStoreService).store(any());
		doNothing().when(userStoriesStoreService).store(any());
		doNothing().when(supportTicketsStoreService).store(any());
		doNothing().when(clientsStoreService).store(any());
		
		service.store(issues.stream());
		
		verify(statusesStoreService, times(1)).store(any());
		verify(typesStoreService, times(1)).store(any());
		verify(sprintsStoreService, times(1)).store(any());
		verify(epicsStoreService, times(1)).store(any());
		verify(userStoriesStoreService, times(1)).store(any());
		verify(tasksStoreService, times(1)).store(any());
		verify(bugsStoreService, times(1)).store(any());
		verify(supportTicketsStoreService, times(1)).store(any());
		verify(clientsStoreService, times(1)).store(any());
	}
	
}
