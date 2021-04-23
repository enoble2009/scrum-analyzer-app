package com.daylight.devleague.services.jira;

import static com.daylight.devleague.dto.jira.JiraBatchEntity.ISSUES;
import static com.daylight.devleague.dto.jira.JiraBatchEntity.USERS;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.services.jira.fetch.JiraIssuesFetchService;
import com.daylight.devleague.services.jira.fetch.JiraUsersFetchService;
import com.daylight.devleague.services.jira.store.JiraIssuesStoreService;
import com.daylight.devleague.services.jira.store.JiraUsersStoreService;

@Service
public class JiraBatchService {

	@Autowired
	private JiraUsersFetchService usersFetchService;
	@Autowired
	private JiraIssuesFetchService issuesFetchService;
	@Autowired
	private JiraUsersStoreService usersStoreService;
	@Autowired
	private JiraIssuesStoreService issuesStoreService;
	@Autowired
	private JiraBatchLogService jiraBatchLogService;
	
	public void loadAllUsers() throws URISyntaxException {
		JiraBatchLog log = jiraBatchLogService.initialize(USERS);
		
		usersStoreService.store(usersFetchService.fetch(log).stream());
		
		log.newBatchStep().complete();
		jiraBatchLogService.save(log);
	}
	
	@Async
	public void loadAllSprints() throws Exception {
		JiraBatchLog log = jiraBatchLogService.initialize(ISSUES);
		
		List<Issue> issues;
		while (!(issues = issuesFetchService.fetch(log)).isEmpty()) {
			issuesStoreService.store(issues.stream());
			log.newBatchStep();
		}
		
		log.complete();
		jiraBatchLogService.save(log);
	}
	
}
