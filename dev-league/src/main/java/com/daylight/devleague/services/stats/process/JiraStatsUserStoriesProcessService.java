package com.daylight.devleague.services.stats.process;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.issues.JiraTaskDao;
import com.daylight.devleague.daos.projects.ProjectDao;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;
import com.daylight.devleague.domain.stats.dimensions.StatsIssueType;
import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;
import com.daylight.devleague.domain.users.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraStatsUserStoriesProcessService {

	private final ProjectDao projectDao;
	private final JiraTaskDao dao;
	
	public Map<SprintSummaryStatsDimension, SprintSummaryStatsMetrics> process(List<JiraUserStory> userStories) {
		return userStories.stream()
				.filter(story -> !story.getSprints().isEmpty())
				.collect(Collectors.toMap(story -> parseDimension(story), story -> parseMetrics(story)));
	}
	
	public SprintSummaryStatsDimension parseDimension(JiraUserStory story) {
		long[] sprintsIds = story.getSprints().stream().sorted((sprint1, sprint2) -> compareStartDate(sprint1, sprint2)).mapToLong(JiraSprint::getId).limit(2l).toArray();
		Long clientId = story.getClient() != null? story.getClient().getId(): null;
		
		Long sprintId = sprintsIds.length > 0? sprintsIds[0]: null;
		return SprintSummaryStatsDimension.builder()
				.issueId(story.getId())
				.sprintId(sprintId)
				.statusId(story.getStatus().getId())
				.clientId(clientId)
				.issueType(StatsIssueType.valueOf(story))
				.projectId(projectDao.findIdBySprintId(sprintId))
				.build();
	}
	
	private int compareStartDate(JiraSprint sprint1, JiraSprint sprint2) {
		if (sprint1 == null || sprint2 == null || sprint1.getStartDate() == null || sprint2.getStartDate() == null) return 0;
		return sprint1.getStartDate().compareTo(sprint2.getStartDate()) * -1;
	}

	public SprintSummaryStatsMetrics parseMetrics(JiraUserStory story) {
		float devHours = 0f, qaHours = 0f, manageHours = 0f;
		
		List<JiraTask> subTasks = dao.findByUserStory(story);
		for (JiraTask subTask: subTasks) {
			if (subTask.getUser() != null && subTask.getUser().getUser() != null) {
				if (subTask.getUser().getUser().getRole().equals(UserRole.DEVELOPER)) {
					devHours += subTask.getRegisteredHours() != null? subTask.getRegisteredHours().floatValue(): 0f;
				} else if (subTask.getUser().getUser().getRole().equals(UserRole.TESTER)) {
					qaHours += subTask.getRegisteredHours() != null? subTask.getRegisteredHours().floatValue(): 0f;
				} else if (subTask.getUser().getUser().getRole().equals(UserRole.MANAGER)) {
					manageHours += subTask.getRegisteredHours() != null? subTask.getRegisteredHours().floatValue(): 0f;
				}
			}
		}
		
		SprintSummaryStatsMetrics metrics = SprintSummaryStatsMetrics.builder()
				.issuesQuantity((byte) 1)
				.storyPoints((byte) (story.getStoryPoints() == null? 0: Math.round(story.getStoryPoints())))
				.manageHours(manageHours)
				.devHours(devHours)
				.qaHours(qaHours)
				.build();
		
		boolean wasInPreviousSprint = story.getSprints().size() > 2;
		if (wasInPreviousSprint) {
			metrics.setPreviousIssuesQuantity((byte) 1);
			metrics.setPreviousStoryPoints((byte) 1);
			metrics.setPreviousDevHours(devHours);
			metrics.setPreviousQaHours(qaHours);
			metrics.setPreviousManageHours(manageHours);
		}
		
		return metrics;
	}

}
