package com.daylight.devleague.daos.projects;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.projects.Project;
import com.daylight.devleague.dto.projects.BasicProjectDataDTO;
import com.daylight.devleague.dto.projects.ClientIdAndNameDTO;

@Repository
public interface ProjectDao extends PagingAndSortingRepository<Project, Long> {

	@Query("SELECT NEW com.daylight.devleague.dto.projects.BasicProjectDataDTO"
			+ "(p.id, p.name) FROM Project p")
	public List<BasicProjectDataDTO> getProjects();

	@Query(value = "SELECT s.id FROM Project p JOIN p.sprints s WHERE p.id = ?1 ORDER BY s.startDate DESC")
	public Page<Long> getLastSprints(Long id, Pageable pageable);

	@Query(value = "SELECT NEW com.daylight.devleague.dto.projects.ClientIdAndNameDTO(c.id, c.name) FROM Project p JOIN p.clients c WHERE p.id = ?1")
	public List<ClientIdAndNameDTO> getClientsIdAndName(Long projectId);

	@Query(value = "SELECT p.id FROM Project p JOIN p.sprints s WHERE s.id = ?1")
	public Long findIdBySprintId(Long sprintId);
	
}
