package com.daylight.devleague.controllers.users.data;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.dto.users.data.GetUserDataResponseDTO;
import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.services.users.data.UserDataService;
import com.daylight.devleague.utils.users.LoggedUserUtils;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserDataController {

	private final UserDataService service;
	
	@GetMapping("user")
	public GetUserDataResponseDTO getUserData() {
		LoggedUserDTO u = LoggedUserUtils.getLoggedUser();
		return service.getDataById(u.getId());
	}
	
}
