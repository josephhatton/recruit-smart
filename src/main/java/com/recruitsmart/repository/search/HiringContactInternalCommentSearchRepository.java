package com.recruitsmart.repository.search;

import com.recruitsmart.domain.HiringContactInternalComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HiringContactInternalComment entity.
 */
public interface HiringContactInternalCommentSearchRepository extends ElasticsearchRepository<HiringContactInternalComment, Long> {
}
