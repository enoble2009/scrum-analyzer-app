package com.daylight.devleague.dto.dashboard;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DashboardUserStoryDTO implements Serializable {

	private static final long serialVersionUID = -8143577054163639208L;
	
	private Long id;
	private String key;
	private String name;
	private String status;
	private String responsible;
	private String client;
	private Byte storyPoints;
	private Float devHours;
	private Float qaHours;

}
