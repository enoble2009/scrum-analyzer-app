package com.daylight.devleague.services.jira.fetch;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.User;
import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.domain.jira.JiraBatchLog;
import com.daylight.devleague.services.jira.JiraFetchService;

import io.atlassian.util.concurrent.Promise;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JiraUsersFetchService extends JiraFetchService<User> {

	@Autowired
	private UserDao userDao;
	
	public List<User> fetch(JiraBatchLog batchLog) throws URISyntaxException {
		final JiraRestClient restClient = getRestClient();
		List<User> usersList = new ArrayList<>();
		userDao.findAll().stream().forEach(u -> {
			try {
				final Promise<User> user = restClient.getUserClient().getUser(new URI(getEndpoint(u)));
				usersList.add(user.get());
			} catch (Exception e) {
				log.warn(String.format("User %s was not able to be downloaded from Jira.", u.getEmail()));
			}
		});
		return usersList;
	}

	private String getEndpoint(com.daylight.devleague.domain.users.User u) {
		return String.format("%s/rest/api/3/user?accountId=%s", jiraServerEndpoint, u.getExternalId());
	}
	
}
