package com.recruitsmart.repository;

import com.recruitsmart.domain.HiringContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HiringContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HiringContactRepository extends JpaRepository<HiringContact, Long> {

}
