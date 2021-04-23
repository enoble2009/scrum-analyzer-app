package com.daylight.devleague.dto.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ClientIdAndNameDTO {

	private Long id;
	private String name;
	
}
