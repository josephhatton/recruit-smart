package com.recruitsmart.repository.search;

import com.recruitsmart.domain.ApplicantStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ApplicantStatus entity.
 */
public interface ApplicantStatusSearchRepository extends ElasticsearchRepository<ApplicantStatus, Long> {
}
