package com.daylight.devleague.dto.dashboard;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MainSummaryBoxDTO implements Serializable {

	private static final long serialVersionUID = 7368731083184186874L;
	private final SummaryBoxDTO current;
	private final SummaryBoxDTO previous;
	
}
