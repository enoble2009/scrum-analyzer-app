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

public class JiraBugTest extends AbstractPOJOTest {

	@Test
    public void testBean() {
        assertThat(JiraBug.class, allOf(
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
		Double registeredHours = 0.0;
		JiraBug bug = new JiraBug(1l, 2l, "Key", "Name", "Description", lastUpdate, status, client, sprints, registeredHours, user);
		
		assertEquals(1l, bug.getId().longValue());
		assertEquals(2l, bug.getExternalId().longValue());
		assertEquals("Key", bug.getKey());
		assertEquals("Name", bug.getName());
		assertEquals("Description", bug.getDescription());
		assertEquals(lastUpdate, bug.getLastUpdate());
		assertEquals(status, bug.getStatus());
		assertEquals(client, bug.getClient());
		assertEquals(sprints, bug.getSprints());
		assertEquals(user, bug.getUser());
		assertEquals(registeredHours, bug.getRegisteredHours());
    }
	
}
