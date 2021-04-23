package com.daylight.devleague.domain.jira.issues;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraIssue;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.JiraStatus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "jira_epics")
@NoArgsConstructor
public class JiraEpic extends JiraIssue {

	@Builder
	public JiraEpic(Long id, Long externalId, String key, String name, String description, LocalDateTime lastUpdate, JiraStatus status, JiraClient client,
			Set<JiraSprint> sprints) {
		super(id, externalId, key, name, description, lastUpdate, status, client, sprints);
	}
	
}
