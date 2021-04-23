package com.daylight.devleague.domain.projects;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "projects")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "project_jira_sprints", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "jira_sprint_id"))
	private List<JiraSprint> sprints;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "project_team_members", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> teamMembers;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "project_jira_clients", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "jira_client_id"))
	private List<JiraClient> clients;

}
