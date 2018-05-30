package com.recruitsmart.repository;

import com.recruitsmart.domain.CompanyInternalComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyInternalComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyInternalCommentRepository extends JpaRepository<CompanyInternalComment, Long> {

}
