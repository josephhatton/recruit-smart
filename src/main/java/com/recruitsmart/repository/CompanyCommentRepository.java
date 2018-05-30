package com.recruitsmart.repository;

import com.recruitsmart.domain.CompanyComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCommentRepository extends JpaRepository<CompanyComment, Long> {

}
