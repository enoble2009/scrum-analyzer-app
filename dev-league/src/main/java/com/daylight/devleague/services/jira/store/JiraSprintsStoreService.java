package com.daylight.devleague.services.jira.store;

import static com.daylight.devleague.utils.jira.JiraValuesUtils.cutoff;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.JiraSprintDao;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.dto.jira.JiraSprintDTO;
import com.daylight.devleague.services.jira.JiraStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JiraSprintsStoreService extends JiraStoreService<JiraSprintDTO> {
	
	private final JiraSprintDao dao;
	
	@Override
	public void store(Stream<JiraSprintDTO> sprints) {
		sprints.forEach(dto -> {
			if (!dao.existsByExternalId(dto.getId())) {
				JiraSprint sprint = JiraSprint
						.builder()
						.externalId(dto.getId())
						.name(cutoff(dto.getName(), MAX_NAME_LENGTH))
						.goal(cutoff(dto.getGoal(), MAX_GOAL_LENGTH))
						.startDate(parse(dto.getStartDate()))
						.endDate(parse(dto.getEndDate()))
						.build();
				dao.save(sprint);
				log.debug("Saved sprint with external id %s.");
			} else {
				log.debug("Not exists.");
			}
		});
	}

}
