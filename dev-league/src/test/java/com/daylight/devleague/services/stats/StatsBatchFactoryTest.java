package com.daylight.devleague.services.stats;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.dto.jira.JiraBatchEntity;
import com.daylight.devleague.services.stats.starters.GitLabStatsCommitsBatchStarterService;
import com.daylight.devleague.services.stats.starters.JiraStatsIssuesBatchStarterService;
import com.daylight.devleague.services.stats.starters.JiraStatsUsersBatchStarterService;

@ExtendWith(MockitoExtension.class)
public class StatsBatchFactoryTest {

	@InjectMocks private StatsBatchFactory factory;
	
	@Mock List<JiraStatsBatchStarterService> jiraStarters;
	@Mock List<GitLabStatsBatchStarterService> gitLabStarters;
	
	@BeforeEach
	public void before() {
		jiraStarters.add(new JiraStatsIssuesBatchStarterService(null, null, null));
		jiraStarters.add(new JiraStatsUsersBatchStarterService());
		gitLabStarters.add(new GitLabStatsCommitsBatchStarterService(null, null, null));
		factory = new StatsBatchFactory(jiraStarters, gitLabStarters);
	}
	
	@Test
	public void should_create_map_when_get_starters_first_time() {
		factory.getStarter((JiraBatchEntity) null);
		verify(jiraStarters, times(1)).stream();
	}
	
	@Test
	public void should_not_create_map_when_get_starters_after_first_time() {
		factory.getStarter((JiraBatchEntity) null);
		factory.getStarter((JiraBatchEntity) null);
		verify(jiraStarters, times(1)).stream();
	}

	@Test
	public void should_return_issues_starter_when_using_issues_enum_value() {
		JiraStatsBatchStarterService starter = factory.getStarter(JiraBatchEntity.ISSUES);
		verify(jiraStarters, times(1)).stream();
		assertEquals(starter, jiraStarters.get(0));
	}

	@Test
	public void should_return_users_starter_when_using_users_enum_value() {
		JiraStatsBatchStarterService starter = factory.getStarter(JiraBatchEntity.USERS);
		verify(jiraStarters, times(1)).stream();
		assertEquals(starter, jiraStarters.get(1));
	}
	
}
