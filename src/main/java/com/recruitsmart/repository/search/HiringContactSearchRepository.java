package com.recruitsmart.repository.search;

import com.recruitsmart.domain.HiringContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HiringContact entity.
 */
public interface HiringContactSearchRepository extends ElasticsearchRepository<HiringContact, Long> {
}
