package com.daylight.devleague.domain.jira;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "jira_statuses")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JiraStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "external_id", unique = true, nullable = false)
	private Long externalId;

	@Column(name = "name", nullable = false)
	private String name;
	
}
