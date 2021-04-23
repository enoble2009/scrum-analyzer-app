package com.daylight.devleague.domain.stats.dimension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.domain.jira.JiraIssue;
import com.daylight.devleague.domain.jira.issues.JiraBug;
import com.daylight.devleague.domain.jira.issues.JiraEpic;
import com.daylight.devleague.domain.jira.issues.JiraSupportTicket;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.domain.stats.dimensions.StatsIssueType;

public class StatsIssueTypeTest {

	@Test
	public void should_return_user_story_when_issue_is_jirauserstory() {
		JiraIssue issue = new JiraUserStory();
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertEquals(StatsIssueType.USER_STORY, type);
	}

	@Test
	public void should_return_task_when_issue_is_jiratask() {
		JiraIssue issue = new JiraTask();
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertEquals(StatsIssueType.TASK, type);
	}
	
	@Test
	public void should_return_bug_when_issue_is_jirabug() {
		JiraIssue issue = new JiraBug();
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertEquals(StatsIssueType.BUG, type);
	}
	
	@Test
	public void should_return_support_ticket_when_issue_is_jirasupportticket() {
		JiraIssue issue = new JiraSupportTicket();
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertEquals(StatsIssueType.SUPPORT_TICKET, type);
	}
	
	@Test
	public void should_return_epic_when_issue_is_jiraepic() {
		JiraIssue issue = new JiraEpic();
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertEquals(StatsIssueType.EPIC, type);
	}

	@Test
	public void should_return_null_when_issue_is_other_option() {
		JiraIssue issue = new JiraIssue() {};
		StatsIssueType type = StatsIssueType.valueOf(issue);
		assertNull(type);
	}
	
}
