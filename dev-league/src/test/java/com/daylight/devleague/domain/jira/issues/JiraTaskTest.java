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

public class JiraTaskTest extends AbstractPOJOTest {

	@Test
    public void testBean() {
        assertThat(JiraTask.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCode(),
                hasValidBeanEquals()
        ));
    }
	
	@Test
    public void testBuilder() {
		JiraStatus status = new JiraStatus();
		JiraClient client = new JiraClient();
		Set<JiraSprint> sprints = new HashSet<>();
		JiraUser user = new JiraUser();
		LocalDateTime lastUpdate = LocalDateTime.of(2021, 1, 1, 0, 0);
		JiraTask task = new JiraTask(1l, 2l, "Key", "Name", "Description", lastUpdate, status, client, sprints, user);
		
		assertEquals(1l, task.getId().longValue());
		assertEquals(2l, task.getExternalId().longValue());
		assertEquals("Key", task.getKey());
		assertEquals("Name", task.getName());
		assertEquals("Description", task.getDescription());
		assertEquals(lastUpdate, task.getLastUpdate());
		assertEquals(status, task.getStatus());
		assertEquals(client, task.getClient());
		assertEquals(user, task.getUser());
		assertEquals(sprints, task.getSprints());
    }
	
}
