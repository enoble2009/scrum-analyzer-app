package com.daylight.devleague.domain.jira;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "jira_issues")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor @AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class JiraIssue {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private Long id;

	@Column(name = "external_id", unique = true, nullable = false)
	private Long externalId;

	@Column(name = "external_key", unique = true, nullable = false, length = 10)
	private String key;

	@Column(name = "name", nullable = false, length = 500)
	private String name;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "last_update")
	private LocalDateTime lastUpdate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "jira_status_id")
	private JiraStatus status;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "jira_client_id")
	private JiraClient client;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(name = "jira_issues_sprints", joinColumns = @JoinColumn(name = "jira_issue_id"), inverseJoinColumns = @JoinColumn(name = "jira_sprint_id"))
	private Set<JiraSprint> sprints;
	
}
