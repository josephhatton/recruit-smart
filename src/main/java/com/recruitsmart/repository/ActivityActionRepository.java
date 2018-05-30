package com.recruitsmart.repository;

import com.recruitsmart.domain.ActivityAction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActivityAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityActionRepository extends JpaRepository<ActivityAction, Long> {

}
