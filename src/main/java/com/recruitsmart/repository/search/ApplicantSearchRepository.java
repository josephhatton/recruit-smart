package com.recruitsmart.repository.search;

import com.recruitsmart.domain.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Applicant entity.
 */
public interface ApplicantSearchRepository extends ElasticsearchRepository<Applicant, Long> {
}
