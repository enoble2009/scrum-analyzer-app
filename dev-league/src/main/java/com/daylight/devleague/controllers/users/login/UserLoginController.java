package com.daylight.devleague.controllers.users.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.dto.users.login.LoginUserResponseDTO;
import com.daylight.devleague.services.users.login.UserLoginService;
import com.daylight.devleague.utils.users.LoggedUserUtils;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserLoginController {

	private final UserLoginService service;
	
	@GetMapping("logged")
	public LoggedUserDTO isLogged() throws Exception {
		return LoggedUserUtils.getLoggedUser();
	}
	
	@PostMapping("login")
	public ResponseEntity<LoginUserResponseDTO> login(@RequestParam String user, @RequestParam String password) throws Exception {
		LoginUserResponseDTO dto = service.login(user, password);
		return ResponseEntity.ok(dto);
	}
	
}
