package com.daylight.devleague.services.jira.store;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Status;
import com.daylight.devleague.daos.jira.JiraStatusDao;
import com.daylight.devleague.domain.jira.JiraStatus;
import com.daylight.devleague.services.jira.JiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraStatusesStoreService extends JiraStoreService<Status> {
	
	private final JiraStatusDao dao;
	
	@Override
	public void store(Stream<Status> statuses) {
		statuses.forEach(dto -> {
			if (!dao.existsByExternalId(dto.getId())) {
				JiraStatus status = JiraStatus
						.builder()
						.externalId(dto.getId())
						.name(dto.getName())
						.build();
				dao.save(status);
				log.debug("Saved status with external id %s.");
			}
		});
	}

}
