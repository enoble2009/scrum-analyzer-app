package com.daylight.devleague.domain.jira.issues;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.domain.AbstractPOJOTest;
import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.JiraStatus;
import com.daylight.devleague.domain.jira.JiraUser;

public class JiraSupportTicketTest extends AbstractPOJOTest {

	@Test
    public void testBean() {
        assertThat(JiraSupportTicket.class, allOf(
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
		JiraSupportTicket ticket = new JiraSupportTicket(1l, 2l, "Key", "Name", "Description", lastUpdate, status, client, sprints, registeredHours, user);
		
		assertEquals(1l, ticket.getId().longValue());
		assertEquals(2l, ticket.getExternalId().longValue());
		assertEquals("Key", ticket.getKey());
		assertEquals("Name", ticket.getName());
		assertEquals("Description", ticket.getDescription());
		assertEquals(lastUpdate, ticket.getLastUpdate());
		assertEquals(status, ticket.getStatus());
		assertEquals(client, ticket.getClient());
		assertEquals(sprints, ticket.getSprints());
		assertEquals(user, ticket.getUser());
		assertEquals(registeredHours, ticket.getRegisteredHours());
    }
	
}
