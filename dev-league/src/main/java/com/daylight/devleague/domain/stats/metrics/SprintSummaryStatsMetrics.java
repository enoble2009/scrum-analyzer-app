package com.daylight.devleague.domain.stats.metrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "stats_sprint_summary_metrics")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SprintSummaryStatsMetrics {

	@Id
	@Column(name = "dimension_id", insertable = false, updatable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dimension_id", nullable = false)
	private SprintSummaryStatsDimension dimension;

	@Column(name = "issues_quantity")
	private Byte issuesQuantity; // Always one.

	@Column(name = "story_points")
	private Byte storyPoints;

	@Column(name = "dev_hours")
	private Float devHours;

	@Column(name = "qa_hours")
	private Float qaHours;

	@Column(name = "manage_hours")
	private Float manageHours;

	@Column(name = "previous_issues_quantity")
	private Byte previousIssuesQuantity; // Always one.

	@Column(name = "previous_story_points")
	private Byte previousStoryPoints;

	@Column(name = "previous_dev_hours")
	private Float previousDevHours;

	@Column(name = "previous_qa_hours")
	private Float previousQaHours;

	@Column(name = "previous_manage_hours")
	private Float previousManageHours;
	
}
