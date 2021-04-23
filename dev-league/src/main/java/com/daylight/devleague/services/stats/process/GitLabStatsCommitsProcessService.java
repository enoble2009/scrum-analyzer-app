package com.daylight.devleague.services.stats.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.jira.issues.JiraUserStoryDao;
import com.daylight.devleague.domain.gitlab.GitLabCommit;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.issues.JiraUserStory;
import com.daylight.devleague.domain.stats.dimensions.CommitSummaryStatsDimension;
import com.daylight.devleague.domain.stats.metrics.CommitSummaryStatsMetrics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitLabStatsCommitsProcessService {

	private final JiraUserStoryDao userStorydao;
	
	
	public Map<CommitSummaryStatsDimension, CommitSummaryStatsMetrics> process(List<GitLabCommit> commits) {
		Map<CommitSummaryStatsDimension, CommitSummaryStatsMetrics> res = new HashMap<>();
		
		commits.stream()
				.filter(commit -> StringUtils.isNotEmpty(commit.getCode()))
				.forEach(commit -> {
					Optional<JiraUserStory> userStory = userStorydao.findByKey(commit.getCode());
					userStory.ifPresent(us -> {
						res.put(parseDimension(commit, userStory.get()), parseMetrics(commit, userStory.get()));
					});
				});
		
		return res;
	}
	
	public CommitSummaryStatsDimension parseDimension(GitLabCommit commit, JiraUserStory userStory) {
		long externalSprintId = userStory.getSprints().stream().mapToLong(JiraSprint::getExternalId).max().getAsLong();
		JiraSprint sprint = userStory.getSprints().stream().filter(x -> x.getExternalId().longValue() == externalSprintId).findAny().orElse(null);
		Long clientId = userStory.getClient() != null? userStory.getClient().getId(): null;
		
		return CommitSummaryStatsDimension.builder()
				.issueId(userStory.getId())
				.sprintId(sprint.getId())
				.clientId(clientId)
				.storyPoints(userStory.getStoryPoints().byteValue())
				.commitId(commit.getId())
				.projectId(commit.getProject().getId())
				.build();
	}
	
	public CommitSummaryStatsMetrics parseMetrics(GitLabCommit commit, JiraUserStory userStory) {
		return CommitSummaryStatsMetrics.builder()
				.createdLines(commit.getCreatedLines())
				.removedLines(commit.getRemovedLines())
				.totalLines(commit.getTotalLines())
				.build();
	}

}
