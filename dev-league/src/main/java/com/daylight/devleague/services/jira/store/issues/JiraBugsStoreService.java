package com.daylight.devleague.services.jira.store.issues;

import java.util.stream.Stream;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.daos.jira.issues.JiraBugDao;
import com.daylight.devleague.domain.jira.issues.JiraBug;
import com.daylight.devleague.services.jira.store.AbstractIssuesJiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraBugsStoreService extends AbstractIssuesJiraStoreService<JiraBug> {
	
	private final JiraBugDao dao;

	@Override
	public void store(Stream<Issue> issues) {
		issues.forEach(dto -> {
			try {
				JiraBug bug = dao.findByExternalId(dto.getId()).orElse(new JiraBug());
				updateIssue(bug, dto);
	
				dao.save(bug);
				log.debug("Saved bug with external id %s.");
			} catch (Throwable e) {
				log.error(String.format("Error when fetching bug with id %d.", dto.getId()), e);
			}
		});
	}

	@Override
	protected void updateIssue(JiraBug issue, Issue dto) throws JSONException {
		super.updateIssue(issue, dto);
		issue.setUser(getUser(dto));
		issue.setRegisteredHours(getRegisteredHours(dto));
	}
	
}
