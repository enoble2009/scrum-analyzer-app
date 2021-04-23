package com.daylight.devleague.daos.jira.issues;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.daylight.devleague.daos.jira.JiraIssueDao;
import com.daylight.devleague.domain.jira.issues.JiraEpic;

@Repository
public interface JiraEpicDao extends JiraIssueDao<JiraEpic, Long> {

	public Optional<JiraEpic> findByKey(String key);

}
