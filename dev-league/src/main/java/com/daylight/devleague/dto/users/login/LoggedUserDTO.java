package com.daylight.devleague.dto.users.login;

import java.util.HashMap;

import com.daylight.devleague.domain.users.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoggedUserDTO {
	
	private Long id;
	private String email;
	private String fullName;
	private String image;
	private String nickName;
	
	public LoggedUserDTO(HashMap<String, Object> data) {
		id = ((Integer) data.get("id")).longValue();
		email = (String) data.get("email");
		fullName = (String) data.get("fullName");
		image = (String) data.get("image");
		nickName = (String) data.get("nickName");
	}

	public LoggedUserDTO(User user) {
		id = user.getId();
		email = user.getEmail();
		fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
		image = user.getImage();
		nickName = user.getNickName();
	}

}
