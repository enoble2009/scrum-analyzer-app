package com.daylight.devleague.daos.jira.issues;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.daylight.devleague.daos.jira.JiraIssueDao;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;

@Repository
public interface JiraTaskDao extends JiraIssueDao<JiraTask, Long> {

	public List<JiraTask> findByUserStory(JiraUserStory userStory);
	public Optional<JiraTask> findByKey(String key);

}
