package com.recruitsmart.repository;

import com.recruitsmart.domain.SkillCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SkillCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {

}
