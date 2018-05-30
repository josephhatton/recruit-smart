package com.recruitsmart.repository.search;

import com.recruitsmart.domain.JobOrderComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobOrderComment entity.
 */
public interface JobOrderCommentSearchRepository extends ElasticsearchRepository<JobOrderComment, Long> {
}
