package com.recruitsmart.repository;

import com.recruitsmart.domain.JobOrderComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobOrderComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobOrderCommentRepository extends JpaRepository<JobOrderComment, Long> {

}
