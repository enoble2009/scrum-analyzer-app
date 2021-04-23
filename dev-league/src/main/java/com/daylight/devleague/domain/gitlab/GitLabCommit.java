package com.daylight.devleague.domain.gitlab;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "gitlab_commits")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GitLabCommit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "external_id", nullable = false)
	private String externalId;

	@Column(name = "code")
	private String code;

	@Column(name = "title")
	private String title;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "created_lines", nullable = false)
	private Long createdLines;
	
	@Column(name = "removed_lines", nullable = false)
	private Long removedLines;
	
	@Column(name = "total_lines", nullable = false)
	private Long totalLines;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private GitLabProject project;
	
}
