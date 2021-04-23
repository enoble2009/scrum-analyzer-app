package com.daylight.devleague.domain.jira.issues;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.domain.AbstractPOJOTest;
import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.JiraStatus;
import com.daylight.devleague.domain.jira.JiraUser;

public class JiraUserStoryTest extends AbstractPOJOTest {

	@Test
	public void testBean() {
		assertThat(JiraUserStory.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
				hasValidBeanHashCode(), hasValidBeanEquals()));
	}

	@Test
	public void testBuilder() {
		JiraStatus status = new JiraStatus();
		JiraClient client = new JiraClient();
		Set<JiraSprint> sprints = new HashSet<>();
		JiraUser user = new JiraUser();
		LocalDateTime lastUpdate = LocalDateTime.of(2021, 1, 1, 0, 0);
		Double storyPoints = 0.0;
		JiraEpic epic = new JiraEpic();
		Double registeredHours = 0.2;
		JiraUserStory userStory = new JiraUserStory(1l, 2l, "Key", "Name", "Description", lastUpdate, status, sprints,
				storyPoints, user, client, epic, registeredHours);

		assertEquals(1l, userStory.getId().longValue());
		assertEquals(2l, userStory.getExternalId().longValue());
		assertEquals("Key", userStory.getKey());
		assertEquals("Name", userStory.getName());
		assertEquals("Description", userStory.getDescription());
		assertEquals(lastUpdate, userStory.getLastUpdate());
		assertEquals(status, userStory.getStatus());
		assertEquals(client, userStory.getClient());
		assertEquals(sprints, userStory.getSprints());
		assertEquals(user, userStory.getUser());
		assertEquals(storyPoints, userStory.getStoryPoints());
		assertEquals(registeredHours, userStory.getRegisteredHours());
	}

}
