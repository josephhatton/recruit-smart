package com.recruitsmart.repository.search;

import com.recruitsmart.domain.ApplicantInternalComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ApplicantInternalComment entity.
 */
public interface ApplicantInternalCommentSearchRepository extends ElasticsearchRepository<ApplicantInternalComment, Long> {
}
