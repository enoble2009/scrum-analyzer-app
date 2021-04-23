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
@Table(name = "jira_user_stories")
@NoArgsConstructor @AllArgsConstructor
public class JiraUserStory extends JiraIssue {

	@Column(name = "story_points")
	private Double storyPoints;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "jira_user_id")
	private JiraUser user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "jira_epic_id")
	private JiraEpic epic;
	
	@Column(name = "registered_hours")
	private Double registeredHours;

	@Builder
	public JiraUserStory(Long id, Long externalId, String key, String name, String description, LocalDateTime lastUpdate, JiraStatus status,
			Set<JiraSprint> sprints, Double storyPoints, JiraUser user, JiraClient client, JiraEpic epic, Double registeredHours) {
		super(id, externalId, key, name, description, lastUpdate, status, client, sprints);
		this.storyPoints = storyPoints;
		this.user = user;
		this.epic = epic;
		this.registeredHours = registeredHours;
	}
	
}
