package com.daylight.devleague.services.jira.store;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.User;
import com.daylight.devleague.daos.jira.JiraUserDao;
import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.domain.jira.JiraUser;
import com.daylight.devleague.services.jira.JiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraUsersStoreService extends JiraStoreService<User> {

	private final UserDao userDao;
	private final JiraUserDao jiraUserDao;
	
	@Transactional
	@Override
	public void store(Stream<User> users) {
		List<JiraUser> jiraUsers = jiraUserDao.findAll();
		users.forEach(u -> {
			com.daylight.devleague.domain.users.User appUser = fetchOrCreateUser(u);
			
			JiraUser jiraUserToUpdate = jiraUsers.parallelStream()
				.filter(ju -> ju.getExternalId().equals(u.getAccountId()))
				.findAny().orElse(new JiraUser());
			
			jiraUserToUpdate.setName(u.getDisplayName());
			jiraUserToUpdate.setImage(u.getAvatarUri().toString());
			jiraUserToUpdate.setExternalId(u.getAccountId());
			jiraUserDao.save(jiraUserToUpdate);

			if (jiraUserToUpdate.getUser() == null) {
				jiraUserToUpdate.setUser(appUser);
				jiraUserDao.save(jiraUserToUpdate);
			}

			log.debug(String.format("User %s was fetched and saved from JIRA data.", u.getEmailAddress()));
		});
	}

	private com.daylight.devleague.domain.users.User fetchOrCreateUser(User u) {
		com.daylight.devleague.domain.users.User appUser = userDao.findByExternalId(u.getAccountId()).orElse(null);
		if (appUser != null) {
			appUser.setFirstName(u.getDisplayName().split(" ")[0]);
			appUser.setLastName(u.getDisplayName().split(" ")[1]);
			appUser.setImage(u.getAvatarUri().toString());
			userDao.save(appUser);
		}
		return appUser;
	}
	
}
