package com.daylight.devleague.dto.dashboard;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class GetSprintsVelocityDTO implements Serializable {

	private static final long serialVersionUID = 4559943860032129187L;
	
	private List<SprintVelocityDTO> sprints;

}
