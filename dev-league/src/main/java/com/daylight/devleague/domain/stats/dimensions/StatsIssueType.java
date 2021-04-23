package com.daylight.devleague.domain.stats.dimensions;

import com.daylight.devleague.domain.jira.JiraIssue;
import com.daylight.devleague.domain.jira.issues.JiraBug;
import com.daylight.devleague.domain.jira.issues.JiraEpic;
import com.daylight.devleague.domain.jira.issues.JiraSupportTicket;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;

public enum StatsIssueType {

	USER_STORY, TASK, BUG, SUPPORT_TICKET, EPIC;
	
	public static StatsIssueType valueOf(JiraIssue issue) {
		if (issue instanceof JiraUserStory) return USER_STORY;
		else if (issue instanceof JiraTask) return TASK;
		else if (issue instanceof JiraBug) return BUG;
		else if (issue instanceof JiraSupportTicket) return SUPPORT_TICKET;
		else if (issue instanceof JiraEpic) return EPIC;
		else return null;
	}
	
}
