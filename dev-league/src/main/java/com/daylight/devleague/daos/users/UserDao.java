package com.daylight.devleague.daos.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.users.User;
import com.daylight.devleague.dto.users.data.GetUserDataResponseDTO;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);
	public Optional<User> findByExternalId(String externalId);

	@Query("select new com.daylight.devleague.dto.users.data.GetUserDataResponseDTO(email, firstName + ' ' + lastName as fullName, image, nickName) from User where id = :id")
	public Optional<GetUserDataResponseDTO> getDataById(@Param("id") Long id);

}
