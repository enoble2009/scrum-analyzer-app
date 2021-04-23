package com.daylight.devleague.domain.jira;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.daylight.devleague.domain.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "jira_users")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JiraUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "external_id", unique = true, nullable = false)
	private String externalId;

	@Column(name = "name")
	private String name;
	
	@Column(name = "image")
	private String image;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
