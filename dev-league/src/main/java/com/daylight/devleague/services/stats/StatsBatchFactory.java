package com.daylight.devleague.services.stats;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.daylight.devleague.dto.gitlab.GitLabBatchEntity;
import com.daylight.devleague.dto.jira.JiraBatchEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsBatchFactory {

	private final List<JiraStatsBatchStarterService> jiraStarters;
	private final List<GitLabStatsBatchStarterService> gitLabStarters;

	private Map<JiraBatchEntity, JiraStatsBatchStarterService> jiraStartersMap;
	private Map<GitLabBatchEntity, GitLabStatsBatchStarterService> gitLabStartersMap;
	
	public JiraStatsBatchStarterService getStarter(JiraBatchEntity entity) {
		if (jiraStartersMap == null) {
			jiraStartersMap = jiraStarters.stream()
					.collect(
							Collectors.toMap(
									JiraStatsBatchStarterService::getEntity, 
									starter -> starter
							)
					);
		}
		return jiraStartersMap.get(entity);
	}
	
	public GitLabStatsBatchStarterService getStarter(GitLabBatchEntity entity) {
		if (gitLabStartersMap == null) {
			gitLabStartersMap = gitLabStarters.stream()
					.collect(
							Collectors.toMap(
									GitLabStatsBatchStarterService::getEntity, 
									starter -> starter
							)
					);
		}
		return gitLabStartersMap.get(entity);
	}
	
}
