package com.daylight.devleague.services.jira.store.issues;

import java.util.stream.Stream;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.daos.jira.issues.JiraTaskDao;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.services.jira.store.AbstractIssuesJiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class JiraTasksStoreService extends AbstractIssuesJiraStoreService<JiraTask> {
	
	private final JiraTaskDao dao;

	@Override
	public void store(Stream<Issue> issues) {
		issues.forEach(dto -> {
			try {
				JiraTask task = dao.findByExternalId(dto.getId()).orElse(new JiraTask());
				updateIssue(task, dto);
	
				dao.save(task);
				log.debug("Saved task with external id %s.");
			} catch (Throwable e) {
				log.error(String.format("Error when fetching task with id %d.", dto.getId()), e);
			}
		});
	}

	@Override
	protected void updateIssue(JiraTask issue, Issue dto) throws JSONException {
		super.updateIssue(issue, dto);
		issue.setUser(getUser(dto));
		issue.setRegisteredHours(getRegisteredHours(dto));
	}

}
