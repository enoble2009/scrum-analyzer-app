package com.daylight.devleague.services.jira.store;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.daylight.devleague.daos.jira.JiraIssueTypeDao;
import com.daylight.devleague.domain.jira.JiraIssueType;
import com.daylight.devleague.services.jira.JiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraTypesStoreService extends JiraStoreService<IssueType> {
	
	private final JiraIssueTypeDao dao;
	
	@Override
	public void store(Stream<IssueType> issueTypes) {
		issueTypes.forEach(dto -> {
			if (!dao.existsByExternalId(dto.getId())) {
				JiraIssueType type = JiraIssueType
						.builder()
						.externalId(dto.getId())
						.name(dto.getName())
						.build();
				dao.save(type);
				log.debug("Saved issue type with external id %s.");
			}
		});
	}

}
