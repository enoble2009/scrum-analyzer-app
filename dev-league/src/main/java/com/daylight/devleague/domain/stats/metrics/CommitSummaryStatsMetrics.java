package com.daylight.devleague.domain.stats.metrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.stats.dimensions.CommitSummaryStatsDimension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "stats_commit_sprint_summary_metrics")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CommitSummaryStatsMetrics {

	@Id
	@Column(name = "dimension_id", insertable = false, updatable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dimension_id", nullable = false)
	private CommitSummaryStatsDimension dimension;

	@Column(name = "created_lines", nullable = false)
	private Long createdLines;
	
	@Column(name = "removed_lines", nullable = false)
	private Long removedLines;
	
	@Column(name = "total_lines", nullable = false)
	private Long totalLines;

}
