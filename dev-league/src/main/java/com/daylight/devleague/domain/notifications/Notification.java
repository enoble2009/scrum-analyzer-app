package com.daylight.devleague.domain.notifications;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "text", length = 100)
	private String text;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type")
	private NotificationType type;

}
