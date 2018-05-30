package com.recruitsmart.repository.search;

import com.recruitsmart.domain.SkillCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SkillCategory entity.
 */
public interface SkillCategorySearchRepository extends ElasticsearchRepository<SkillCategory, Long> {
}
