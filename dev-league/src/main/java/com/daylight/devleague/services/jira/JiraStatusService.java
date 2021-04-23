package com.daylight.devleague.services.jira;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.JiraStatusDao;
import com.daylight.devleague.daos.metadata.StatusMetadataDao;
import com.daylight.devleague.domain.metadata.StatusType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatusService {

	@SuppressWarnings("unused")
	private final JiraStatusDao dao;
	
	private final StatusMetadataDao metadataDao;
	
	public List<Long> getInProgressStatusIds() {
		return metadataDao.getStatusIdsByType(StatusType.STARTED);
	}

	public List<Long> getFinishedStatusIds() {
		return metadataDao.getStatusIdsByType(StatusType.FINISHED);
	}
	
}
