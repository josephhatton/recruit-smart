package com.recruitsmart.repository;

import com.recruitsmart.domain.HotList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HotList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotListRepository extends JpaRepository<HotList, Long> {

}
