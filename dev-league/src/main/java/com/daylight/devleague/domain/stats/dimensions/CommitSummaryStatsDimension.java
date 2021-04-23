package com.daylight.devleague.domain.stats.dimensions;

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
@Table(name = "stats_commit_sprint_summary_dimension")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CommitSummaryStatsDimension {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private Long id;

	@Column(name = "issue_id")
	private Long issueId;
	@Column(name = "sprint_id")
	private Long sprintId;
	@Column(name = "client_id")
	private Long clientId;
	@Column(name = "story_points")
	private Byte storyPoints;
	@Column(name = "project_id")
	private Long projectId;
	@Column(name = "commit_id")
	private Long commitId;
	
}
