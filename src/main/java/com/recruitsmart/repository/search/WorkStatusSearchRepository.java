package com.recruitsmart.repository.search;

import com.recruitsmart.domain.WorkStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WorkStatus entity.
 */
public interface WorkStatusSearchRepository extends ElasticsearchRepository<WorkStatus, Long> {
}
