package com.daylight.devleague.dto.users.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponseDTO {

	private Long userId;
	private String token;
	
}
