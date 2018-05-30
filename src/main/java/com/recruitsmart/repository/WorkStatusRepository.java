package com.recruitsmart.repository;

import com.recruitsmart.domain.WorkStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkStatusRepository extends JpaRepository<WorkStatus, Long> {

}
