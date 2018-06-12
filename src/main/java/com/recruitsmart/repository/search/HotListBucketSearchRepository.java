package com.recruitsmart.repository.search;

import com.recruitsmart.domain.HotListBucket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HotListBucket entity.
 */
public interface HotListBucketSearchRepository extends ElasticsearchRepository<HotListBucket, Long> {
}
