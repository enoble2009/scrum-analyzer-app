package com.daylight.devleague.services.notifications;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.notifications.NotificationDao;
import com.daylight.devleague.domain.notifications.Notification;
import com.daylight.devleague.domain.notifications.NotificationType;
import com.daylight.devleague.dto.notifications.NotificationDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	
	private static final int ITEMS_PER_PAGE = 10;
	private final NotificationDao dao;
	
	public List<NotificationDTO> getNotifications(NotificationType type, int page) {
		Pageable pageable = PageRequest.of(page * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
		return dao.findByTypeOrderByIdDesc(type, pageable).getContent().stream()
				.map(notification -> generateNotificationDTO(notification))
				.collect(Collectors.toList());
	}

	private NotificationDTO generateNotificationDTO(Notification notification) {
		return NotificationDTO.builder()
				.text(notification.getText())
				.createdDate(notification.getCreatedDate())
				.build();
	}

}
