package com.recruitsmart.repository;

import com.recruitsmart.domain.ApplicantStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ApplicantStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantStatusRepository extends JpaRepository<ApplicantStatus, Long> {

}
