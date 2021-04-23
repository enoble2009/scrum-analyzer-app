package com.daylight.devleague.services.jira.store.issues;

import java.util.stream.Stream;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.daylight.devleague.daos.jira.issues.JiraSupportTicketDao;
import com.daylight.devleague.domain.jira.issues.JiraSupportTicket;
import com.daylight.devleague.services.jira.store.AbstractIssuesJiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class JiraSupportTicketsStoreService extends AbstractIssuesJiraStoreService<JiraSupportTicket> {
	
	private final JiraSupportTicketDao dao;
	
	@Override
	public void store(Stream<Issue> issues) {
		issues.forEach(dto -> {
			try {
				JiraSupportTicket ticket = dao.findByExternalId(dto.getId()).orElse(new JiraSupportTicket());
				updateIssue(ticket, dto);
	
				dao.save(ticket);
				log.debug("Saved ticket with external id %s.");
			} catch (Throwable e) {
				log.error(String.format("Error when fetching ticket with id %d.", dto.getId()), e);
			}
		});
	}

	@Override
	protected void updateIssue(JiraSupportTicket issue, Issue dto) throws JSONException {
		super.updateIssue(issue, dto);
		issue.setUser(getUser(dto));
		issue.setRegisteredHours(getRegisteredHours(dto));
	}

}
