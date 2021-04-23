package com.daylight.devleague.dto.notifications;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWarningNotificationsDTO implements Serializable {

	private static final long serialVersionUID = 7628309922413630140L;
	
	private List<NotificationDTO> notifications;

}
