package com.recruitsmart.repository.search;

import com.recruitsmart.domain.WorkHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WorkHistory entity.
 */
public interface WorkHistorySearchRepository extends ElasticsearchRepository<WorkHistory, Long> {
}
