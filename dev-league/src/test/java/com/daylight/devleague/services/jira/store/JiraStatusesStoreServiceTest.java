package com.daylight.devleague.services.jira.store;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atlassian.jira.rest.client.api.domain.Status;
import com.daylight.devleague.daos.jira.JiraStatusDao;
import com.daylight.devleague.domain.jira.JiraStatus;

@ExtendWith(MockitoExtension.class)
public class JiraStatusesStoreServiceTest {

	@InjectMocks private JiraStatusesStoreService service;
	
	@Mock private JiraStatusDao dao;
	
	@BeforeEach
	public void before() {
		service = new JiraStatusesStoreService(dao);
	}
	
	@Test
	public void should_do_nothing_when_list_is_empty() {
		Stream<Status> types = new ArrayList<Status>().stream();
		
		service.store(types);
		
		verify(dao, never()).save(any());
		verify(dao, never()).existsByExternalId(anyLong());
	}
	
	@Test
	public void should_not_save_when_item_exists() {
		Status type = new Status(null, 1l, null, null, null, null);
		Stream<Status> types = Arrays.asList(type).stream();

		when(dao.existsByExternalId(eq(type.getId()))).thenReturn(true);
		
		service.store(types);
		
		verify(dao, never()).save(any());
		verify(dao, times(1)).existsByExternalId(eq(type.getId()));
	}
	
	@Test
	public void should_save_when_item_not_exists() {
		Status type = new Status(null, 1l, "Name", null, null, null);
		Stream<Status> types = Arrays.asList(type).stream();

		when(dao.existsByExternalId(eq(type.getId()))).thenReturn(false);
		
		service.store(types);
		
		JiraStatus issueType = JiraStatus
				.builder()
				.externalId(type.getId())
				.name(type.getName())
				.build();
		verify(dao, times(1)).save(eq(issueType));
		verify(dao, times(1)).existsByExternalId(eq(type.getId()));
	}
	
}
