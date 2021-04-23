package com.daylight.devleague.daos.metadata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daylight.devleague.domain.metadata.StatusMetadata;
import com.daylight.devleague.domain.metadata.StatusType;

@Repository
public interface StatusMetadataDao extends JpaRepository<StatusMetadata, Long> {

	@Query(value = "SELECT s.id FROM StatusMetadata s WHERE s.type = ?1")
	List<Long> getStatusIdsByType(StatusType type);

}
