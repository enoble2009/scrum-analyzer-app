package com.daylight.devleague.domain.gitlab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "gitlab_projects")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GitLabProject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "external_id")
	private Long externalId;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;

}
