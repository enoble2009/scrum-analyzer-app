package com.daylight.devleague.dto.jira;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class JiraSprintDTO {
	
	private Integer id;
	private String name;
	private String goal;
	private String startDate;
	private String endDate;
	
}
