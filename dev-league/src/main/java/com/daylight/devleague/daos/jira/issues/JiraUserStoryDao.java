package com.daylight.devleague.daos.jira.issues;

import org.springframework.stereotype.Repository;

import com.daylight.devleague.daos.jira.JiraIssueDao;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;

@Repository
public interface JiraUserStoryDao extends JiraIssueDao<JiraUserStory, Long> {

}
