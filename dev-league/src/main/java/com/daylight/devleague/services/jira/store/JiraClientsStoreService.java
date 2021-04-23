package com.daylight.devleague.services.jira.store;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.JiraClientDao;
import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.dto.jira.JiraClientDTO;
import com.daylight.devleague.services.jira.JiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraClientsStoreService extends JiraStoreService<JiraClientDTO> {
	
	private final JiraClientDao dao;
	
	@Override
	public void store(Stream<JiraClientDTO> clients) {
		clients.forEach(dto -> {
			if (!dao.existsByExternalId(dto.getId())) {
				JiraClient client = JiraClient
						.builder()
						.externalId(dto.getId())
						.name(dto.getValue())
						.build();
				dao.save(client);
				log.debug("Saved client with external id %s.");
			}
		});
	}

}
