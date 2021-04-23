package com.daylight.devleague.domain.jira;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "jira_sprints")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JiraSprint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "external_id", unique = true, nullable = false)
	private Integer externalId;

	@Column(name = "name", nullable = false, length = 500)
	private String name;

	@Column(name = "goal", length = 255)
	private String goal;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;
	
}
