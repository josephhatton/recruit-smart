package com.recruitsmart.repository.search;

import com.recruitsmart.domain.CompanyComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompanyComment entity.
 */
public interface CompanyCommentSearchRepository extends ElasticsearchRepository<CompanyComment, Long> {
}
