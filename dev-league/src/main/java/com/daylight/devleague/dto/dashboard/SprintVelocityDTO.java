package com.daylight.devleague.dto.dashboard;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class SprintVelocityDTO implements Serializable {

	private static final long serialVersionUID = -5284603909096730765L;

	private String title;
	private Integer teamMembers;
	private Integer releaseSpeed;
	private Double avgSpeed; // For the last 5 sprints.
	private Double stdevSpeed; // For the last 5 sprints.
	
	public Double getMinSpeed() {
		if (avgSpeed == null || stdevSpeed == null) return null;
		return avgSpeed - stdevSpeed;
	}
	public Double getMaxSpeed() {
		if (avgSpeed == null || stdevSpeed == null) return null;
		return avgSpeed + stdevSpeed;
	}
	
}
