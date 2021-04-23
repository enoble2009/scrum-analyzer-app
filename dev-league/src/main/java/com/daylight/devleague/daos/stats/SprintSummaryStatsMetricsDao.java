package com.daylight.devleague.daos.stats;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.stats.metrics.SprintSummaryStatsMetrics;
import com.daylight.devleague.dto.dashboard.DashboardUserStoryDTO;
import com.daylight.devleague.dto.stats.SummarySprintAndPointsDTO;
import com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO;

@Repository
public interface SprintSummaryStatsMetricsDao extends PagingAndSortingRepository<SprintSummaryStatsMetrics, Long> {

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummarySprintAndPointsDTO"
			+ "(s.name, coalesce(sum(m.storyPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d, JiraSprint s "
			+ "WHERE s.id = d.sprintId AND d.projectId = ?1 AND (?2 IS NULL OR d.clientId = ?2) AND d.statusId IN (?3) "
			+ "GROUP BY d.sprintId, s.name, s.startDate ORDER BY s.startDate DESC")
	public List<SummarySprintAndPointsDTO> getSprintsStoryPoints(Long projectId, Long clientId, List<Long> statusId, Pageable pageable);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.issuesQuantity), 0) as issues, coalesce(sum(m.storyPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1")
	public SummaryStatsIssuesAndPointsDTO countIssuesAndStoryPointsBySprintId(Long sprintId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.issuesQuantity), 0) as issues, coalesce(sum(m.storyPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.statusId IN (?2)")
	public SummaryStatsIssuesAndPointsDTO countIssuesAndStoryPointsBySprintIdAndStatusId(Long sprintId, List<Long> statusId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.issuesQuantity), 0) as issues, coalesce(sum(m.storyPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.clientId = ?2")
	public SummaryStatsIssuesAndPointsDTO countIssuesAndStoryPointsBySprintIdAndClientId(Long sprintId, Long clientId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.issuesQuantity), 0) as issues, coalesce(sum(m.storyPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.clientId = ?2 AND d.statusId IN (?3)")
	public SummaryStatsIssuesAndPointsDTO countIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(Long sprintId, Long clientId, List<Long> statusId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.dashboard.DashboardUserStoryDTO"
			+ "(d.issueId as id, us.key as key, us.name as name, s.name, u.name, c.name, coalesce(m.storyPoints, 0) as storyPoints, coalesce(m.devHours, 0) as devHours, coalesce(m.qaHours, 0) as qaHours) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d, JiraUserStory us LEFT JOIN us.client c LEFT JOIN us.user u JOIN us.status s "
			+ "WHERE us.id = d.issueId AND d.sprintId = ?1 ORDER BY d.clientId, d.statusId")
	public List<DashboardUserStoryDTO> getIssuesAndStoryPointsAndHoursBySprintId(Long sprintId);

	
	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.previousIssuesQuantity), 0) as issues, coalesce(sum(m.previousStoryPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1")
	public SummaryStatsIssuesAndPointsDTO countPreviousIssuesAndStoryPointsBySprintId(Long sprintId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.previousIssuesQuantity), 0) as issues, coalesce(sum(m.previousStoryPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.statusId IN (?2)")
	public SummaryStatsIssuesAndPointsDTO countPreviousIssuesAndStoryPointsBySprintIdAndStatusId(Long sprintId, List<Long> statusId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.previousIssuesQuantity), 0) as issues, coalesce(sum(m.previousStoryPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.clientId = ?2")
	public SummaryStatsIssuesAndPointsDTO countPreviousIssuesAndStoryPointsBySprintIdAndClientId(Long sprintId, Long clientId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.stats.SummaryStatsIssuesAndPointsDTO"
			+ "(coalesce(sum(m.previousIssuesQuantity), 0) as issues, coalesce(sum(m.previousStoryPoints), 0) as storyPoints) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d WHERE d.sprintId = ?1 AND d.clientId = ?2 AND d.statusId IN (?3)")
	public SummaryStatsIssuesAndPointsDTO countPreviousIssuesAndStoryPointsBySprintIdAndClientIdAndStatusId(Long sprintId, Long clientId, List<Long> statusId);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.dashboard.DashboardUserStoryDTO"
			+ "(d.issueId as id, us.key as key, us.name as name, s.name, u.name, c.name, coalesce(m.previousStoryPoints, 0) as storyPoints, coalesce(m.previousDevHours, 0) as devHours, coalesce(m.previousQaHours, 0) as qaHours) "
			+ "FROM SprintSummaryStatsMetrics m JOIN m.dimension d, JiraUserStory us LEFT JOIN us.client c LEFT JOIN us.user u JOIN us.status s "
			+ "WHERE us.id = d.issueId AND d.sprintId = ?1 ORDER BY d.clientId, d.statusId")
	public List<DashboardUserStoryDTO> getPreviousIssuesAndStoryPointsAndHoursBySprintId(Long sprintId);
}
