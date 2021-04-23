package com.daylight.devleague;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.daylight.devleague.controllers.dashboard.DashboardController;
import com.daylight.devleague.controllers.jira.JiraDataController;
import com.daylight.devleague.controllers.stats.StatsController;

@SpringBootTest
class DevLeagueApplicationTests {

	@Autowired private DashboardController dashboardController;
	@Autowired private JiraDataController jiraDataController;
	@Autowired private StatsController statsController;
	
	private DevLeagueApplication application;
	
	@Test
	void contextLoads() {
		assertThat(dashboardController).isNotNull();
		assertThat(jiraDataController).isNotNull();
		assertThat(statsController).isNotNull();
	}

	@SuppressWarnings("static-access")
	@Test
	void should_run_spring_application_when_run_main() {
		application = new DevLeagueApplication();
		application.main(new String[0]);
	}
	
}
