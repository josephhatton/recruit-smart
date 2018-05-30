package com.recruitsmart.repository;

import com.recruitsmart.domain.JobOrderInternalComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobOrderInternalComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobOrderInternalCommentRepository extends JpaRepository<JobOrderInternalComment, Long> {

}
