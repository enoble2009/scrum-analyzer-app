package com.daylight.devleague.daos.jira.issues;

import org.springframework.stereotype.Repository;

import com.daylight.devleague.daos.jira.JiraIssueDao;
import com.daylight.devleague.domain.jira.issues.JiraBug;

@Repository
public interface JiraBugDao extends JiraIssueDao<JiraBug, Long> {

}
