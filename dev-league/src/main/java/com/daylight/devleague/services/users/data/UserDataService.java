package com.daylight.devleague.services.users.data;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.dto.users.data.GetUserDataResponseDTO;
import com.daylight.devleague.exceptions.user.data.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDataService {

	private final UserDao dao;
	
	public GetUserDataResponseDTO getDataById(Long id) {
		return dao.getDataById(id).orElseThrow(UserNotFoundException::new);
	}

}
