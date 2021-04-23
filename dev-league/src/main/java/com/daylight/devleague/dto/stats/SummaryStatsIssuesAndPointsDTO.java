package com.daylight.devleague.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class SummaryStatsIssuesAndPointsDTO {

	private Long issues;
	private Long storyPoints;

	public SummaryStatsIssuesAndPointsDTO plus(SummaryStatsIssuesAndPointsDTO dto) {
		this.issues += dto.getIssues();
		this.storyPoints += dto.getStoryPoints();
		
		return this;
	}
	
}
