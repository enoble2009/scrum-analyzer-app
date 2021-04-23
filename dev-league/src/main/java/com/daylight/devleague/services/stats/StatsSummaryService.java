package com.daylight.devleague.services.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daylight.devleague.daos.stats.SprintSummaryStatsMetricsDao;
import com.daylight.devleague.dto.dashboard.ClientSummaryBoxDTO;
import com.daylight.devleague.dto.dashboard.DashboardUserStoryDTO;
import com.daylight.devleague.dto.dashboard.GetSprintsVelocityDTO;
import com.daylight.devleague.dto.dashboard.MainSummaryBoxDTO;
import com.daylight.devleague.dto.dashboard.SprintVelocityDTO;
import com.daylight.devleague.dto.dashboard.SummaryBoxDTO;
import com.daylight.devleague.dto.projects.ClientIdAndNameDTO;
import com.daylight.devleague.dto.projects.CurrentAndPreviousSprintsDTO;
import com.daylight.devleague.dto.stats.SummarySprintAndPointsDTO;
import com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO;
import com.daylight.devleague.utils.stats.StatsUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsSummaryService {

	private final SprintSummaryStatsMetricsDao dao;
	
	public MainSummaryBoxDTO getSummaryBySprint(CurrentAndPreviousSprintsDTO sprintsDTO, List<Long> inProgressStatus, List<Long> finishedStatus) {
		MainSummaryBoxDTO main = MainSummaryBoxDTO.builder()
				.current(searchSummaryInfo(sprintsDTO.getCurrentSprint(), inProgressStatus, finishedStatus))
				.previous(searchPreviousSummaryInfo(sprintsDTO.getCurrentSprint(), sprintsDTO.getPreviousSprint(), inProgressStatus, finishedStatus))
				.build();
		return main;
	}
	
	public ClientSummaryBoxDTO getSummaryBySprintAndClient(CurrentAndPreviousSprintsDTO sprintsDTO, ClientIdAndNameDTO client, List<Long> inProgressStatus, List<Long> finishedStatus) {
		return ClientSummaryBoxDTO.clientBuilder()
				.name(client.getName())
				.current(searchSummaryInfo(sprintsDTO.getCurrentSprint(), client.getId(), inProgressStatus, finishedStatus))
				.previous(searchPreviousSummaryInfo(sprintsDTO.getCurrentSprint(), sprintsDTO.getPreviousSprint(), client.getId(), inProgressStatus, finishedStatus))
				.build();
	}

	public List<DashboardUserStoryDTO> getUserStoriesSummary(Long sprintId) {
		return dao.getIssuesAndStoryPointsAndHoursBySprintId(sprintId);
	}

	public GetSprintsVelocityDTO getSprintsVelocity(Long projectId, Long clientId, List<Long> statuses) {
		Pageable pageable = PageRequest.of(0, 10);
		List<SummarySprintAndPointsDTO> storyPointsBySprint = dao.getSprintsStoryPoints(projectId, clientId, statuses, pageable);
		storyPointsBySprint = Lists.reverse(storyPointsBySprint);

		Queue<Integer> sumStoryPoints = Queues.newArrayBlockingQueue(5);
		List<SprintVelocityDTO> sprints = new ArrayList<>();
		storyPointsBySprint.forEach(item -> generateSprintVelocityDTO(sumStoryPoints, sprints, item));
		
		return GetSprintsVelocityDTO.builder()
				.sprints(sprints).build();
	}

	private void generateSprintVelocityDTO(Queue<Integer> sumStoryPoints, List<SprintVelocityDTO> sprints,
			SummarySprintAndPointsDTO item) {
		if (sumStoryPoints.size() == 5) sumStoryPoints.poll();
		sumStoryPoints.add(item.getStoryPoints().intValue());

		SprintVelocityDTO dto = SprintVelocityDTO.builder()
				.title(item.getSprint())
				.releaseSpeed(item.getStoryPoints().intValue())
				.build();
		dto.setAvgSpeed(StatsUtils.calculateAverage(sumStoryPoints));
		dto.setStdevSpeed(StatsUtils.calculateStandardDeviation(sumStoryPoints));
		
		sprints.add(dto);
	}
	
	private SummaryBoxDTO searchSummaryInfo(Long sprintId, List<Long> inProgressStatus, List<Long> finishedStatus) {
		SummaryStatsIssuesAndPointsDTO totalDTO = dao.countIssuesAndStoryPointsBySprintId(sprintId);
		SummaryStatsIssuesAndPointsDTO inProgressDTO = dao.countIssuesAndStoryPointsBySprintIdAndStatusId(sprintId, inProgressStatus);
		SummaryStatsIssuesAndPointsDTO finishedDTO = dao.countIssuesAndStoryPointsBySprintIdAndStatusId(sprintId, finishedStatus);
		return generateSummaryBox(totalDTO, inProgressDTO, finishedDTO);
	}

	private SummaryBoxDTO searchSummaryInfo(Long sprintId, Long clientId, List<Long> inProgressStatus, List<Long> finishedStatus) {
		SummaryStatsIssuesAndPointsDTO totalDTO = dao.countIssuesAndStoryPointsBySprintIdAndClientId(sprintId, clientId);
		SummaryStatsIssuesAndPointsDTO inProgressDTO = dao.countIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(sprintId, clientId, inProgressStatus);
		SummaryStatsIssuesAndPointsDTO finishedDTO = dao.countIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(sprintId, clientId, finishedStatus);
		return generateSummaryBox(totalDTO, inProgressDTO, finishedDTO);
	}

	private SummaryBoxDTO searchPreviousSummaryInfo(Long sprintId, Long previousSprintId, List<Long> inProgressStatus, List<Long> finishedStatus) {
		SummaryStatsIssuesAndPointsDTO totalDTO = dao.countPreviousIssuesAndStoryPointsBySprintId(sprintId)
				.plus(dao.countIssuesAndStoryPointsBySprintId(previousSprintId));
		SummaryStatsIssuesAndPointsDTO inProgressDTO = dao.countPreviousIssuesAndStoryPointsBySprintIdAndStatusId(sprintId, inProgressStatus)
				.plus(dao.countIssuesAndStoryPointsBySprintIdAndStatusId(previousSprintId, inProgressStatus));
		SummaryStatsIssuesAndPointsDTO finishedDTO = dao.countPreviousIssuesAndStoryPointsBySprintIdAndStatusId(sprintId, finishedStatus)
				.plus(dao.countIssuesAndStoryPointsBySprintIdAndStatusId(previousSprintId, finishedStatus));
		return generateSummaryBox(totalDTO, inProgressDTO, finishedDTO);
	}

	private SummaryBoxDTO searchPreviousSummaryInfo(Long sprintId, Long previousSprintId, Long clientId, List<Long> inProgressStatus, List<Long> finishedStatus) {
		SummaryStatsIssuesAndPointsDTO totalDTO = dao.countPreviousIssuesAndStoryPointsBySprintIdAndClientId(sprintId, clientId)
				.plus(dao.countIssuesAndStoryPointsBySprintIdAndClientId(previousSprintId, clientId));
		SummaryStatsIssuesAndPointsDTO inProgressDTO = dao.countPreviousIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(sprintId, clientId, inProgressStatus)
				.plus(dao.countIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(previousSprintId, clientId, inProgressStatus));
		SummaryStatsIssuesAndPointsDTO finishedDTO = dao.countPreviousIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(sprintId, clientId, finishedStatus)
				.plus(dao.countIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(previousSprintId, clientId, finishedStatus));
		return generateSummaryBox(totalDTO, inProgressDTO, finishedDTO);
	}
	
	private SummaryBoxDTO generateSummaryBox(SummaryStatsIssuesAndPointsDTO totalDTO, SummaryStatsIssuesAndPointsDTO inProgressDTO, SummaryStatsIssuesAndPointsDTO finishedDTO) {
		return SummaryBoxDTO.builder()
				.totalHUs(totalDTO.getIssues())
				.totalSPs(totalDTO.getStoryPoints())
				.workingHUs(inProgressDTO.getIssues())
				.workingSPs(inProgressDTO.getStoryPoints())
				.finishedHUs(finishedDTO.getIssues())
				.finishedSPs(finishedDTO.getStoryPoints())
				.build();
	}


}
