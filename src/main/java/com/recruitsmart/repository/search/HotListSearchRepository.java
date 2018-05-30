package com.recruitsmart.repository.search;

import com.recruitsmart.domain.HotList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HotList entity.
 */
public interface HotListSearchRepository extends ElasticsearchRepository<HotList, Long> {
}
