package com.daylight.devleague.services.jira.store.issues;

import java.util.stream.Stream;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.daos.jira.issues.JiraEpicDao;
import com.daylight.devleague.domain.jira.issues.JiraEpic;
import com.daylight.devleague.services.jira.store.AbstractIssuesJiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class JiraEpicsStoreService extends AbstractIssuesJiraStoreService<JiraEpic> {

	private final JiraEpicDao dao;

	@Override
	public void store(Stream<Issue> issues) {
		issues.forEach(dto -> {
			try {
				JiraEpic epic = dao.findByExternalId(dto.getId()).orElse(new JiraEpic());
				updateIssue(epic, dto);
	
				dao.save(epic);
				log.debug("Saved epic with external id %s.");
			} catch (Throwable e) {
				log.error(String.format("Error when fetching epic with id %d.", dto.getId()), e);
			}
		});
	}

	@Override
	protected void updateIssue(JiraEpic issue, Issue dto) throws JSONException {
		super.updateIssue(issue, dto);
	}

}
