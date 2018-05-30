package com.recruitsmart.repository.search;

import com.recruitsmart.domain.JobOrderInternalComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobOrderInternalComment entity.
 */
public interface JobOrderInternalCommentSearchRepository extends ElasticsearchRepository<JobOrderInternalComment, Long> {
}
