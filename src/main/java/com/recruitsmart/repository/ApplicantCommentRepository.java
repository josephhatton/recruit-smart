package com.recruitsmart.repository;

import com.recruitsmart.domain.ApplicantComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ApplicantComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantCommentRepository extends JpaRepository<ApplicantComment, Long> {

}
