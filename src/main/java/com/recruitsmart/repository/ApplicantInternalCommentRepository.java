package com.recruitsmart.repository;

import com.recruitsmart.domain.ApplicantInternalComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ApplicantInternalComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantInternalCommentRepository extends JpaRepository<ApplicantInternalComment, Long> {

}
