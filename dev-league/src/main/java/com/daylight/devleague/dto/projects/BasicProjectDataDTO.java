package com.daylight.devleague.dto.projects;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class BasicProjectDataDTO implements Serializable {
	private static final long serialVersionUID = -2917874683338684585L;
	private Long id;
	private String name;
}
