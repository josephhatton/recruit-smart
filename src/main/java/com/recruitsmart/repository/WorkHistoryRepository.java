package com.recruitsmart.repository;

import com.recruitsmart.domain.WorkHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {

}
