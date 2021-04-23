package com.daylight.devleague.services.jira.store.issues;

import java.util.stream.Stream;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.daos.jira.issues.JiraTaskDao;
import com.daylight.devleague.daos.jira.issues.JiraUserStoryDao;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.services.jira.store.AbstractIssuesJiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class JiraUserStoriesStoreService extends AbstractIssuesJiraStoreService<JiraUserStory> {

	private final JiraUserStoryDao dao;
	
	private final JiraTaskDao taskDao;

	@Override
	public void store(Stream<Issue> issues) {
		issues.forEach(dto -> {
			try {
				JiraUserStory userStory = dao.findByExternalId(dto.getId()).orElse(new JiraUserStory());
				updateIssue(userStory, dto);
				
				dao.save(userStory);
				log.debug("Saved user story with external id %s.");
			} catch (Throwable e) {
				log.error(String.format("Error when fetching user story with id %d.", dto.getId()), e);
			}
		});
	}

	@Override
	protected void updateIssue(JiraUserStory issue, Issue dto) throws JSONException {
		super.updateIssue(issue, dto);
		issue.setStoryPoints(getStoryPoints(dto));
		issue.setUser(getUser(dto));
		issue.setEpic(getEpic(dto));
		issue.setRegisteredHours(getRegisteredHours(dto));
		getSubTasks(dto).forEach(subTask -> {
			subTask.setUserStory(issue);
			taskDao.save(subTask);
		});
	}

}
