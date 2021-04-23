package com.daylight.devleague.services.jira;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.daylight.devleague.domain.jira.JiraBatchLog;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class JiraFetchService<T> {

	protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Value("${jira.server.endpoint}")
	protected String jiraServerEndpoint;
	
	@Value("${jira.server.user}")
	private String jiraServerUser;

	@Value("${jira.server.password}")
	private String jiraServerPassword;
	
	@Value("${jira.selected.project}")
	protected String jiraPreselectedProject;

	public abstract List<T> fetch(JiraBatchLog batchLog) throws Exception;
	
	protected JiraRestClient getRestClient() throws URISyntaxException {
		URI jiraServerUri = new URI(jiraServerEndpoint);
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		
		log.debug(String.format("Jira authentication with [%s|%s] to %s started...", jiraServerUser, jiraServerPassword, jiraServerUri));
		final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, jiraServerUser, jiraServerPassword);
		log.debug("Jira client authentication finished.");
		
		return restClient;
	}
	
	protected String format(LocalDateTime date) {
		return date.format(formatter);
	}
	
}
