package com.recruitsmart.repository.search;

import com.recruitsmart.domain.JobOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobOrder entity.
 */
public interface JobOrderSearchRepository extends ElasticsearchRepository<JobOrder, Long> {
}
