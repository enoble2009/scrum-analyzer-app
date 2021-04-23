package com.daylight.devleague.dto.users.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class GetUserDataResponseDTO {

	private String email;
	private String fullName;
	private String image;
	private String nickName; 
	
}
