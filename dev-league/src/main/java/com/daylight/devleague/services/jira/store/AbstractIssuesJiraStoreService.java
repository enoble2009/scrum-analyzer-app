package com.daylight.devleague.services.jira.store;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Subtask;
import com.daylight.devleague.daos.jira.JiraClientDao;
import com.daylight.devleague.daos.jira.JiraSprintDao;
import com.daylight.devleague.daos.jira.JiraStatusDao;
import com.daylight.devleague.daos.jira.JiraUserDao;
import com.daylight.devleague.daos.jira.issues.JiraEpicDao;
import com.daylight.devleague.daos.jira.issues.JiraTaskDao;
import com.daylight.devleague.domain.jira.JiraClient;
import com.daylight.devleague.domain.jira.JiraIssue;
import com.daylight.devleague.domain.jira.JiraSprint;
import com.daylight.devleague.domain.jira.JiraStatus;
import com.daylight.devleague.domain.jira.JiraUser;
import com.daylight.devleague.domain.jira.issues.JiraEpic;
import com.daylight.devleague.domain.jira.issues.JiraTask;
import com.daylight.devleague.dto.jira.JiraClientDTO;
import com.daylight.devleague.services.jira.JiraStoreService;
import com.daylight.devleague.utils.jira.JiraValuesUtils;
import com.google.gson.Gson;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public abstract class AbstractIssuesJiraStoreService<T extends JiraIssue> extends JiraStoreService<Issue> {

	@Autowired
	protected JiraStatusDao statusDao;
	@Autowired
	protected JiraSprintDao sprintDao;
	@Autowired
	protected JiraUserDao userDao;
	@Autowired
	protected JiraEpicDao epicDao;
	@Autowired
	protected JiraTaskDao taskDao;
	@Autowired
	protected JiraClientDao clientDao;	

	
	public AbstractIssuesJiraStoreService(JiraStatusDao statusDao, JiraSprintDao sprintDao, JiraUserDao userDao, 
			JiraEpicDao epicDao, JiraTaskDao taskDao, JiraClientDao clientDao) {
		super();
		this.statusDao = statusDao;
		this.sprintDao = sprintDao;
		this.userDao = userDao;
		this.epicDao = epicDao;
		this.taskDao = taskDao;
		this.clientDao = clientDao;
	}

	protected JiraStatus getStatus(Issue dto) {
		return statusDao.findByExternalId(dto.getStatus().getId()).get();
	}

	protected JiraClient getClient(Issue dto) {
		if (getClientFromField(dto) == null) return null;
		return clientDao.findByExternalId(getClientFromField(dto).getId()).get();
	}
	
	protected void updateIssue(T issue, Issue dto) throws JSONException {
		JiraStatus status = getStatus(dto);
		JiraClient client = getClient(dto);
		Set<JiraSprint> sprints = getAllSprints(dto);

		issue.setSprints(sprints);
		issue.setExternalId(dto.getId());
		issue.setKey(dto.getKey());
		issue.setName(JiraValuesUtils.cutoff(dto.getSummary(), MAX_NAME_LENGTH));
		issue.setDescription(JiraValuesUtils.cutoff(dto.getDescription(), MAX_DESCRIPTION_LENGTH));
		issue.setStatus(status);
		issue.setClient(client);

		Instant instant = Instant.ofEpochMilli(dto.getUpdateDate().getMillis());
		ZoneId zone = ZoneId.of(dto.getUpdateDate().getZone().getID(), ZoneId.SHORT_IDS);
		issue.setLastUpdate(LocalDateTime.ofInstant(instant, zone));
	}

	protected JiraUser getUser(Issue issue) {
		if (issue.getAssignee() == null) {
			return null;
		}
		String externalId = issue.getAssignee().getAccountId();
		return userDao.findByExternalId(externalId).orElse(null);
	}

	protected JiraEpic getEpic(Issue issue) {
		String epicLink = (String) issue.getFieldByName("Epic Link").getValue();
		return epicDao.findByKey(epicLink).orElse(null);
	}

	// TODO: Search a jira ticket to get user story related.
	protected List<JiraTask> getSubTasks(Issue dto) {
		List<JiraTask> resList = new ArrayList<>();
		Iterable<Subtask> subTasks = dto.getSubtasks();
		
		subTasks.forEach(subtask -> {
			taskDao.findByKey(subtask.getIssueKey()).ifPresent(task -> resList.add(task));
		});
		
		return resList;
	}

	// TODO: Search a jira ticket to get registered tickets.
	protected Double getRegisteredHours(Issue dto) {
		if (dto.getFieldByName("Σ Tiempo empleado") == null || dto.getFieldByName("Σ Tiempo empleado").getValue() == null) {
			return 0.0;
		}
		return ((Integer) dto.getFieldByName("Σ Tiempo empleado").getValue()).doubleValue() / 60.0 / 60.0;
	}

	protected Double getStoryPoints(Issue dto) {
		if (dto.getFieldByName("Story Points") == null || dto.getFieldByName("Story Points").getValue() == null) {
			return 0.0;
		}
		return (Double) dto.getFieldByName("Story Points").getValue();
	}

	protected Set<JiraSprint> getAllSprints(Issue issue) throws JSONException {
		JSONArray jsonSprints = (JSONArray) issue.getFieldByName("Sprint").getValue();
		int i = 0;
		List<Integer> sprintIds = new ArrayList<>();
		while (i < jsonSprints.length()) {
			JSONObject sprint = jsonSprints.getJSONObject(i);
			sprintIds.add((Integer) sprint.get("id"));
			i++;
		}

		return sprintDao.findByExternalIdIn(sprintIds).stream().collect(Collectors.toSet());
	}

	protected JiraClientDTO getClientFromField(Issue dto) {
		if (dto.getFieldByName("Operador").getValue() == null) return null;
		
		String json = dto.getFieldByName("Operador").getValue().toString();
		JiraClientDTO client = (JiraClientDTO) new Gson().fromJson(json, JiraClientDTO.class);
		return client;
	}

}
