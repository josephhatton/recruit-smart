package com.recruitsmart.repository.search;

import com.recruitsmart.domain.CompanyInternalComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyInternalComment entity.
 */
public interface CompanyInternalCommentSearchRepository extends ElasticsearchRepository<CompanyInternalComment, Long> {
}
