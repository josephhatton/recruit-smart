package com.recruitsmart.repository.search;

import com.recruitsmart.domain.ActivityAction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActivityAction entity.
 */
public interface ActivityActionSearchRepository extends ElasticsearchRepository<ActivityAction, Long> {
}
