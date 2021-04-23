package com.daylight.devleague.services.stats.starters;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.dto.jira.JiraBatchEntity;

@ExtendWith(MockitoExtension.class)
public class JiraStatsUsersBatchStarterServiceTest {

	@InjectMocks private JiraStatsUsersBatchStarterService service;

	@BeforeEach
	public void before() {
		service = new JiraStatsUsersBatchStarterService();
	}
	
	@Test
	public void entity_is_users() {
		JiraBatchEntity entity = service.getEntity();
		assertEquals(entity, JiraBatchEntity.USERS);
	}
	
	@Test
	public void start_batch_process_do_nothing() {
		service.startBatchProcess(null);
	}

}
