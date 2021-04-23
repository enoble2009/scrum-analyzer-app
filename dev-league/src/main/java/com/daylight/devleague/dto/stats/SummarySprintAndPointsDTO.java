package com.daylight.devleague.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class SummarySprintAndPointsDTO {

	private String sprint;
	private Long storyPoints;

}
