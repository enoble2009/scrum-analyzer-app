package com.daylight.devleague.domain.jira.issues;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraIssue;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.JiraStatus;
import com.daylight.devleague.domain.jira.JiraUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "jira_tasks")
@NoArgsConstructor @AllArgsConstructor
public class JiraTask extends JiraIssue {

	@Column(name = "registered_hours")
	private Double registeredHours;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "jira_user_id")
	private JiraUser user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "jira_user_story_id")
	private JiraUserStory userStory;

	@Builder
	public JiraTask(Long id, Long externalId, String key, String name, String description, LocalDateTime lastUpdate, JiraStatus status, JiraClient client,
			Set<JiraSprint> sprints, JiraUser user) {
		super(id, externalId, key, name, description, lastUpdate, status, client, sprints);
		this.user = user;
	}
	
}
