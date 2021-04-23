package com.daylight.devleague.dto.dashboard;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SummaryBoxDTO implements Serializable {

	private static final long serialVersionUID = -4045807066672380047L;
	
	private Long totalHUs;
	private Long workingHUs;
	private Long finishedHUs;

	private Long totalSPs;
	private Long workingSPs;
	private Long finishedSPs;

}
