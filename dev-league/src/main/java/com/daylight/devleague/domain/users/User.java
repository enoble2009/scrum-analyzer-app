package com.daylight.devleague.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.jira.JiraUser;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "email", length = 100, unique = true)
	private String email;
	
	@Column(name = "password", length = 100)
	private String password;
	
	@Column(name = "first_name", length = 100)
	private String firstName;
	
	@Column(name = "last_name", length = 100)
	private String lastName;
	
	@Column(name = "image", length = 1000)
	private String image;
	
	@Column(name = "nick_name", length = 100)
	private String nickName;

	@Column(name = "external_id", unique = true, nullable = false)
	private String externalId;

	@Enumerated
	@Column(name = "user_role", nullable = false)
	private UserRole role;
	
	@OneToOne(mappedBy = "user")
	private JiraUser jiraUser;

}
