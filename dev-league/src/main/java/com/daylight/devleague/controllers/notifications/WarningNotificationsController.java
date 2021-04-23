package com.daylight.devleague.controllers.notifications;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daylight.devleague.domain.notifications.NotificationType;
import com.daylight.devleague.dto.notifications.GetWarningNotificationsDTO;
import com.daylight.devleague.services.notifications.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WarningNotificationsController {

	private final NotificationService service;
	
	@GetMapping("notifications/warning")
	public GetWarningNotificationsDTO getWarningNotifications(@RequestParam int page) {
		return GetWarningNotificationsDTO.builder()
				.notifications(service.getNotifications(NotificationType.WARNING, page))
				.build();
	}
	
}
