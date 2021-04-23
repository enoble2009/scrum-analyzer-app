package com.daylight.devleague.domain.stats.dimensions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "stats_sprint_summary_dimension")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SprintSummaryStatsDimension {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private Long id;

	@Column(name = "issue_id")
	private Long issueId;
	@Column(name = "sprint_id")
	private Long sprintId;
	@Column(name = "status_id")
	private Long statusId;
	@Column(name = "client_id")
	private Long clientId;
	@Column(name = "project_id")
	private Long projectId;
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "issue_type")
	private StatsIssueType issueType;
	
}
