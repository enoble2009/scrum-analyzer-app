package com.daylight.devleague.dto.projects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentAndPreviousSprintsDTO {

	private Long previousSprint;
	private Long currentSprint;
	
}
