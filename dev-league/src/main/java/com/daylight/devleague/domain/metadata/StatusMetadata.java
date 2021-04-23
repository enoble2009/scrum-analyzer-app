package com.daylight.devleague.domain.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.jira.JiraStatus;

import lombok.Data;

@Entity
@Table(name = "status_metadata")
@Data
public class StatusMetadata {

	@Id
	@Column(name = "jira_status_id", insertable = false, updatable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jira_status_id", nullable = false)
	private JiraStatus status;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type")
	private StatusType type;

}
