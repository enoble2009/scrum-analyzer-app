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

public class JiraEpicTest extends AbstractPOJOTest {

	@Test
    public void testBean() {
        assertThat(JiraEpic.class, allOf(
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
		LocalDateTime lastUpdate = LocalDateTime.of(2021, 1, 1, 0, 0);
		JiraEpic epic = new JiraEpic(1l, 2l, "Key", "Name", "Description", lastUpdate, status, client, sprints);
		
		assertEquals(1l, epic.getId().longValue());
		assertEquals(2l, epic.getExternalId().longValue());
		assertEquals("Key", epic.getKey());
		assertEquals("Name", epic.getName());
		assertEquals("Description", epic.getDescription());
		assertEquals(lastUpdate, epic.getLastUpdate());
		assertEquals(status, epic.getStatus());
		assertEquals(client, epic.getClient());
		assertEquals(sprints, epic.getSprints());
    }
	
}
