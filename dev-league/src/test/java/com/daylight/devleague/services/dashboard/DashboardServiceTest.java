package com.daylight.devleague.services.dashboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.services.jira.JiraStatusService;
import com.daylight.devleague.services.projects.ProjectDataService;
import com.daylight.devleague.services.stats.StatsSummaryService;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

	@InjectMocks
	private DashboardService service;

	@Mock private StatsSummaryService statsSummaryService;
	@Mock private ProjectDataService projectDataService;
	@Mock private JiraStatusService statusService;

	@BeforeEach
	public void before() {
		service = new DashboardService(statsSummaryService, projectDataService, statusService);
	}
	
	// TODO: Create all tests.

}
