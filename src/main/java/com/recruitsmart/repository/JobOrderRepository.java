package com.recruitsmart.repository;

import com.recruitsmart.domain.JobOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the JobOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobOrderRepository extends JpaRepository<JobOrder, Long> {

    List<JobOrder> findAllByCompanyId(Long companyId);

}
