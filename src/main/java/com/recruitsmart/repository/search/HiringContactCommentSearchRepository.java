package com.recruitsmart.repository.search;

import com.recruitsmart.domain.HiringContactComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HiringContactComment entity.
 */
public interface HiringContactCommentSearchRepository extends ElasticsearchRepository<HiringContactComment, Long> {
}
