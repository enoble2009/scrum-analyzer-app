package com.daylight.devleague.daos.jira.issues;

import org.springframework.stereotype.Repository;

import com.daylight.devleague.daos.jira.JiraIssueDao;
import com.daylight.devleague.domain.jira.issues.JiraSupportTicket;

@Repository
public interface JiraSupportTicketDao extends JiraIssueDao<JiraSupportTicket, Long> {

}
