package com.daylight.devleague.services.users;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.dto.users.data.GetUserDataResponseDTO;
import com.daylight.devleague.exceptions.user.data.UserNotFoundException;
import com.daylight.devleague.services.users.data.UserDataService;

@ExtendWith(MockitoExtension.class)
public class UserDataServiceTest {

	@InjectMocks
	private UserDataService service;

	@Mock
	UserDao dao;

	@BeforeEach
	public void before() {
		service = new UserDataService(dao);
	}

	@Test
	public void should_return_dto_when_sending_id_and_exists() {
		Optional<GetUserDataResponseDTO> responseDto = Optional
				.of(new GetUserDataResponseDTO("enoble2009@gmail.com", "Samuel Noble", "image.jpg", "enoble2009"));
		Long anyId = 1l;

		when(dao.getDataById(eq(anyId))).thenReturn(responseDto);

		GetUserDataResponseDTO actualResponse = service.getDataById(anyId);

		verify(dao, times(1)).getDataById(eq(anyId));
		assertEquals(responseDto.get(), actualResponse);
	}

	@Test
	public void should_throw_exception_when_user_was_not_found() {
		Optional<GetUserDataResponseDTO> responseDto = Optional.empty();
		Long anyId = 1l;

		when(dao.getDataById(eq(anyId))).thenReturn(responseDto);

		try {
			service.getDataById(anyId);
			Assert.fail();
		} catch (Exception e) {
			boolean isInstance = e instanceof UserNotFoundException;
			Assert.assertTrue(isInstance);
		} finally {
			verify(dao, times(1)).getDataById(eq(anyId));
		}
	}

}
