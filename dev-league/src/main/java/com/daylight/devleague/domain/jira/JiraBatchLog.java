package com.daylight.devleague.domain.jira;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.daylight.devleague.dto.jira.JiraBatchEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "jira_batch_log")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JiraBatchLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "entity", nullable = false)
	private JiraBatchEntity entity;
	
	@Column(name = "run_date", nullable = false)
	private LocalDateTime runDate;
	
	@Column(name = "start_date")
	private LocalDateTime startDate;
	
	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDate;
	
	@Column(name = "pages", nullable = false)
	private Integer pages;
	
	@Column(name = "completed", nullable = false)
	private Boolean completed;
	
	@Column(name = "stats_updated", nullable = false)
	private Boolean statsUpdated;

	public JiraBatchLog newBatchStep() {
		pages++;
		return this;
	}

	public JiraBatchLog complete() {
		completed = true;
		endDate = LocalDateTime.now();
		return this;
	}
	
}
