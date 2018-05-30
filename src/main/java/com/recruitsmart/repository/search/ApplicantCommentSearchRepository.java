package com.recruitsmart.repository.search;

import com.recruitsmart.domain.ApplicantComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ApplicantComment entity.
 */
public interface ApplicantCommentSearchRepository extends ElasticsearchRepository<ApplicantComment, Long> {
}
