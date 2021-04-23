package com.daylight.devleague.services.jira.store;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.daos.jira.JiraSprintDao;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.dto.jira.JiraSprintDTO;

@ExtendWith(MockitoExtension.class)
public class JiraSprintsStoreServiceTest {

	@InjectMocks private JiraSprintsStoreService service;
	
	@Mock private JiraSprintDao dao;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	
	@BeforeEach
	public void before() {
		service = new JiraSprintsStoreService(dao);
	}
	
	@Test
	public void should_do_nothing_when_list_is_empty() {
		Stream<JiraSprintDTO> sprints = new ArrayList<JiraSprintDTO>().stream();
		
		service.store(sprints);
		
		verify(dao, never()).save(any());
		verify(dao, never()).existsByExternalId(anyInt());
	}
	
	@Test
	public void should_not_save_when_item_exists() {
		JiraSprintDTO sprint = new JiraSprintDTO();
		Stream<JiraSprintDTO> sprints = Arrays.asList(sprint).stream();

		when(dao.existsByExternalId(eq(sprint.getId()))).thenReturn(true);
		
		service.store(sprints);
		
		verify(dao, never()).save(any());
		verify(dao, times(1)).existsByExternalId(eq(sprint.getId()));
	}
	
	@Test
	public void should_save_when_item_not_exists() {
		JiraSprintDTO sprint = new JiraSprintDTO();
		sprint.setId(12);
		sprint.setName("Name");
		sprint.setGoal("Goal");
		Stream<JiraSprintDTO> sprints = Arrays.asList(sprint).stream();

		when(dao.existsByExternalId(eq(sprint.getId()))).thenReturn(false);
		
		service.store(sprints);
		
		JiraSprint newSprint = JiraSprint
				.builder()
				.externalId(sprint.getId())
				.name(sprint.getName())
				.goal(sprint.getGoal())
				.startDate(parse(sprint.getStartDate()))
				.endDate(parse(sprint.getEndDate()))
				.build();
		verify(dao, times(1)).save(eq(newSprint));
		verify(dao, times(1)).existsByExternalId(eq(sprint.getId()));
	}
	
	private LocalDateTime parse(String dateString) {
		try {
			return LocalDateTime.parse(dateString, formatter);
		} catch (Throwable e) {
			return null;
		}
	}
	
}
