package com.daylight.devleague.services.jira.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atlassian.jira.rest.client.api.domain.User;
import com.daylight.devleague.daos.jira.JiraUserDao;
import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.domain.jira.JiraUser;

@ExtendWith(MockitoExtension.class)
public class JiraUsersStoreServiceTest {

	@InjectMocks private JiraUsersStoreService service;
	
	@Mock private UserDao userDao;
	@Mock private JiraUserDao jiraUserDao;
	
	@BeforeEach
	public void before() {
		service = new JiraUsersStoreService(userDao, jiraUserDao);
	}
	
	@Test
	public void should_do_nothing_when_list_is_empty() {
		Stream<User> users = new ArrayList<User>().stream();
		
		service.store(users);
		
		verify(jiraUserDao, times(1)).findAll();
		verify(userDao, never()).findByExternalId(anyString());
		verify(userDao, never()).save(any());
		verify(jiraUserDao, never()).save(any());
	}
	
	@Test
	public void should_not_save_when_item_exists() throws URISyntaxException {
		Map<String, URI> avatarUris = new HashMap<>();
		avatarUris.put(User.S48_48, new URI("https://localhost:2021"));
		User user = new User(null, null, "Display Name", "AccountId", null, false, null, avatarUris, null);
		Stream<User> users = Arrays.asList(user).stream();
		com.daylight.devleague.domain.users.User userFound = new com.daylight.devleague.domain.users.User();
		Optional<com.daylight.devleague.domain.users.User> returnUser = Optional.of(userFound);
		
		when(userDao.findByExternalId(eq(user.getAccountId()))).thenReturn(returnUser);
		
		service.store(users);
		
		verify(jiraUserDao, times(1)).findAll();
		verify(userDao, times(1)).findByExternalId(anyString());
		verify(userDao, times(1)).save(any());
		verify(jiraUserDao, times(2)).save(any());
	}
	
	@Test
	public void should_save_when_item_not_exists() throws URISyntaxException {
		Map<String, URI> avatarUris = new HashMap<>();
		avatarUris.put(User.S48_48, new URI("https://localhost:2021"));
		User user = new User(null, null, "Display Name", "AccountId", null, false, null, avatarUris, null);
		Stream<User> users = Arrays.asList(user).stream();
		Optional<com.daylight.devleague.domain.users.User> returnUser = Optional.empty();
		
		when(userDao.findByExternalId(eq(user.getAccountId()))).thenReturn(returnUser);
		
		service.store(users);
		
		verify(jiraUserDao, times(1)).findAll();
		verify(userDao, times(1)).findByExternalId(anyString());
		verify(userDao, never()).save(any());
		verify(jiraUserDao, times(2)).save(any());
	}
	
	@Test
	public void should_not_set_and_save_new_jirauser_when_user_was_assigned() throws URISyntaxException {
		Map<String, URI> avatarUris = new HashMap<>();
		avatarUris.put(User.S48_48, new URI("https://localhost:2021"));
		User user = new User(null, null, "Display Name", "AccountId", null, false, null, avatarUris, null);
		Stream<User> users = Arrays.asList(user).stream();
		Optional<com.daylight.devleague.domain.users.User> returnUser = Optional.empty();
		JiraUser user1 = new JiraUser();
		user1.setExternalId("AccountId");
		com.daylight.devleague.domain.users.User userIntoJiraUser = new com.daylight.devleague.domain.users.User();
		user1.setUser(userIntoJiraUser);
		JiraUser user2 = new JiraUser();
		user2.setExternalId("AccountId2");
		List<JiraUser> jiraUsers = Arrays.asList(user1, user2);
		
		when(jiraUserDao.findAll()).thenReturn(jiraUsers);
		when(userDao.findByExternalId(eq(user.getAccountId()))).thenReturn(returnUser);
		
		service.store(users);
		
		verify(jiraUserDao, times(1)).findAll();
		verify(userDao, times(1)).findByExternalId(anyString());
		verify(userDao, never()).save(any());
		verify(jiraUserDao, times(1)).save(any());
		assertEquals(userIntoJiraUser, user1.getUser());
	}

		@Test
	public void should_set_and_save_new_jirauser_when_user_was_not_assigned() throws URISyntaxException {
		Map<String, URI> avatarUris = new HashMap<>();
		avatarUris.put(User.S48_48, new URI("https://localhost:2021"));
		User user = new User(null, null, "Display Name", "AccountId", null, false, null, avatarUris, null);
		Stream<User> users = Arrays.asList(user).stream();
		Optional<com.daylight.devleague.domain.users.User> returnUser = Optional.of(new com.daylight.devleague.domain.users.User());
		JiraUser user1 = new JiraUser();
		user1.setExternalId("AccountId");
		user1.setUser(null);
		JiraUser user2 = new JiraUser();
		user2.setExternalId("AccountId2");
		List<JiraUser> jiraUsers = Arrays.asList(user1, user2);
		
		when(jiraUserDao.findAll()).thenReturn(jiraUsers);
		when(userDao.findByExternalId(eq(user.getAccountId()))).thenReturn(returnUser);
		
		service.store(users);
		
		verify(jiraUserDao, times(1)).findAll();
		verify(userDao, times(1)).findByExternalId(anyString());
		verify(userDao, times(1)).save(any());
		verify(jiraUserDao, times(2)).save(any());
		assertNotNull(user1.getUser());
	}
	
}
