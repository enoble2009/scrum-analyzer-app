package com.daylight.devleague.services.users.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.domain.users.User;
import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.dto.users.login.LoginUserResponseDTO;
import com.daylight.devleague.exceptions.user.login.InvalidLoginException;
import com.daylight.devleague.services.jwt.JwtTokenService;

@Service
public class UserLoginService {

	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public LoginUserResponseDTO login(String username, String password) throws InvalidLoginException {
		System.out.println(encoder.encode(password));
		Optional<User> user = dao.findByEmail(username);
		if (isUserNotFoundOrPasswordInvalid(password, user)) {
			throw new InvalidLoginException();
		}
		LoggedUserDTO loggedUser = new LoggedUserDTO(user.get());
		String jwtToken = jwtTokenService.getToken(username, loggedUser);
		return LoginUserResponseDTO.builder().token(jwtToken).userId(1l).build();
	}

	private boolean isUserNotFoundOrPasswordInvalid(String password, Optional<User> user) {
		return !user.isPresent() || !encoder.matches(password, user.get().getPassword());
	}

}
