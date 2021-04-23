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
public class GetUserStoriesResponseDTO implements Serializable {

	private static final long serialVersionUID = 237662139214337195L;

	private List<DashboardUserStoryDTO> userStories;
	
}
