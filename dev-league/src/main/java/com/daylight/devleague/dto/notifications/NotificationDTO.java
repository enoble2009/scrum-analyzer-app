package com.daylight.devleague.dto.notifications;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO implements Serializable {

	private static final long serialVersionUID = 6324056234564127026L;

	private String text;
	private LocalDateTime createdDate;
	
}
