package com.daylight.devleague.services.jira.fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.services.jira.JiraFetchService;
import com.google.common.collect.Sets;

import io.atlassian.util.concurrent.Promise;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JiraIssuesFetchService extends JiraFetchService<Issue> {

	private static final int MAX_RESULTS = 100;

	private Set<String> fields = Sets.newHashSet("summary", "description", "issuetype", "assignee", "project",
			"created", "updated", "customfield_10004", "status", "customfield_12203", "customfield_10007",
			"customfield_10006", "aggregatetimespent", "worklogs", "subtasks", "customfield_12652");

	private String issueTypes = "Error, \"Historia Desarrollo\", Soporte, Epica, Subtarea";

	@Override
	public List<Issue> fetch(JiraBatchLog batchLog) throws Exception {
		final JiraRestClient restClient = getRestClient();
		String updatedCondition = batchLog.getStartDate() == null? "": "AND updated > '" + format(batchLog.getStartDate()) + "'";
		String jql = String.format(
				"project IN (%s) AND issuetype IN (%s) AND Sprint IS NOT NULL %s order by updated ASC",
				jiraPreselectedProject, issueTypes, updatedCondition);
		int nextRowToFetch = MAX_RESULTS * batchLog.getPages();

		Promise<SearchResult> items = restClient.getSearchClient().searchJql(jql, MAX_RESULTS, nextRowToFetch, fields);
		SearchResult searchResults = items.get();

		List<Issue> issues = new ArrayList<>();
		searchResults.getIssues().forEach(issues::add);

		log.debug(String.format("Read %d issues from JIRA", issues.size()));
		return issues;
	}

}
