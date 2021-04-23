package com.daylight.devleague.dto.projects;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GetProjectsResponseDTO implements Serializable
{
	private static final long serialVersionUID = -5152092809817974107L;
	private List<BasicProjectDataDTO> projects;
}
