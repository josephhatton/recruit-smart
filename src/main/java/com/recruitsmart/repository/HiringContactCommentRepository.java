package com.recruitsmart.repository;

import com.recruitsmart.domain.HiringContactComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HiringContactComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HiringContactCommentRepository extends JpaRepository<HiringContactComment, Long> {

}
