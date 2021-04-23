package com.daylight.devleague.services.stats;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.dto.jira.JiraBatchEntity;
import com.daylight.devleague.services.gitlab.GitLabBatchLogService;
import com.daylight.devleague.services.jira.JiraBatchLogService;
import com.daylight.devleague.services.stats.starters.JiraStatsIssuesBatchStarterService;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
	
	@Mock private StatsBatchFactory batchServers;
	@Mock private JiraBatchLogService jiraLogService;
	@Mock private GitLabBatchLogService gitLabLogService;
	
	@InjectMocks private StatsService service;
	
	@BeforeEach
	public void before() {
		service = new StatsService(jiraLogService, gitLabLogService, batchServers);
	}
	
	@Test
	public void needed_calls_are_done_in_update_scheduler_when_log_exists() {
		JiraBatchLog log = new JiraBatchLog(null, JiraBatchEntity.ISSUES, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), 1, true, true);
		
		JiraStatsIssuesBatchStarterService batchStarterService = mock(JiraStatsIssuesBatchStarterService.class);
		
		when(jiraLogService.popFirstLogToUpdateStats()).thenReturn(log);
		when(batchServers.getStarter(JiraBatchEntity.ISSUES)).thenReturn(batchStarterService);
		
		service.updateJiraStats();
		verify(jiraLogService, times(1)).popFirstLogToUpdateStats();
		verify(batchServers, times(1)).getStarter(JiraBatchEntity.ISSUES);
	}
	
	@Test
	public void needed_calls_are_done_in_update_scheduler_when_log_is_null() {
		JiraBatchLog nullLog = null;
		
		when(jiraLogService.popFirstLogToUpdateStats()).thenReturn(nullLog);
		
		service.updateJiraStats();
		verify(jiraLogService, times(1)).popFirstLogToUpdateStats();
		verify(batchServers, never()).getStarter(JiraBatchEntity.ISSUES);
	}

	@Test
	public void needed_rollback_call_in_update_scheduler_when_error_with_starter() {
		JiraBatchLog log = new JiraBatchLog(null, JiraBatchEntity.ISSUES, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), 1, true, true);
		
		JiraStatsIssuesBatchStarterService issuesBatchStarterService = mock(JiraStatsIssuesBatchStarterService.class);
		
		when(jiraLogService.popFirstLogToUpdateStats()).thenReturn(log);
		when(batchServers.getStarter(JiraBatchEntity.ISSUES)).thenReturn(issuesBatchStarterService);
		Mockito.doThrow(new RuntimeException()).when(issuesBatchStarterService).startBatchProcess(log);
		
		service.updateJiraStats();
		verify(jiraLogService, times(1)).popFirstLogToUpdateStats();
		verify(batchServers, times(1)).getStarter(JiraBatchEntity.ISSUES);
		verify(jiraLogService, times(1)).rollbackStatsUpdate(log);
	}
	
}
