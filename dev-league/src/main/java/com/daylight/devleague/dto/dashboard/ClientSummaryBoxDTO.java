package com.daylight.devleague.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClientSummaryBoxDTO extends MainSummaryBoxDTO {

	private static final long serialVersionUID = 5629879672128170043L;
	private final String name;
	
	@Builder(builderMethodName = "clientBuilder")
	public ClientSummaryBoxDTO(String name, SummaryBoxDTO current, SummaryBoxDTO previous) {
		super(current, previous);
		this.name = name;
	}
	
}
