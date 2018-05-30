package com.recruitsmart.repository;

import com.recruitsmart.domain.HiringContactInternalComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HiringContactInternalComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HiringContactInternalCommentRepository extends JpaRepository<HiringContactInternalComment, Long> {

}
