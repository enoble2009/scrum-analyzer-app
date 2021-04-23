package com.daylight.devleague.daos.notifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.notifications.Notification;
import com.daylight.devleague.domain.notifications.NotificationType;

@Repository
public interface NotificationDao extends PagingAndSortingRepository<Notification, Long> {

	public Page<Notification> findByTypeOrderByIdDesc(NotificationType type, Pageable pageable);
	
}
