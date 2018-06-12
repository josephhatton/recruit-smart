package com.recruitsmart.repository;

import com.recruitsmart.domain.HotListBucket;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HotListBucket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotListBucketRepository extends JpaRepository<HotListBucket, Long> {

}
