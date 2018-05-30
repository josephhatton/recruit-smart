package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.SkillCategory;

import com.recruitsmart.repository.SkillCategoryRepository;
import com.recruitsmart.repository.search.SkillCategorySearchRepository;
import com.recruitsmart.web.rest.errors.BadRequestAlertException;
import com.recruitsmart.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SkillCategory.
 */
@RestController
@RequestMapping("/api")
public class SkillCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryResource.class);

    private static final String ENTITY_NAME = "skillCategory";

    private final SkillCategoryRepository skillCategoryRepository;

    private final SkillCategorySearchRepository skillCategorySearchRepository;

    public SkillCategoryResource(SkillCategoryRepository skillCategoryRepository, SkillCategorySearchRepository skillCategorySearchRepository) {
        this.skillCategoryRepository = skillCategoryRepository;
        this.skillCategorySearchRepository = skillCategorySearchRepository;
    }

    /**
     * POST  /skill-categories : Create a new skillCategory.
     *
     * @param skillCategory the skillCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillCategory, or with status 400 (Bad Request) if the skillCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-categories")
    @Timed
    public ResponseEntity<SkillCategory> createSkillCategory(@RequestBody SkillCategory skillCategory) throws URISyntaxException {
        log.debug("REST request to save SkillCategory : {}", skillCategory);
        if (skillCategory.getId() != null) {
            throw new BadRequestAlertException("A new skillCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillCategory result = skillCategoryRepository.save(skillCategory);
        skillCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/skill-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-categories : Updates an existing skillCategory.
     *
     * @param skillCategory the skillCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillCategory,
     * or with status 400 (Bad Request) if the skillCategory is not valid,
     * or with status 500 (Internal Server Error) if the skillCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-categories")
    @Timed
    public ResponseEntity<SkillCategory> updateSkillCategory(@RequestBody SkillCategory skillCategory) throws URISyntaxException {
        log.debug("REST request to update SkillCategory : {}", skillCategory);
        if (skillCategory.getId() == null) {
            return createSkillCategory(skillCategory);
        }
        SkillCategory result = skillCategoryRepository.save(skillCategory);
        skillCategorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-categories : get all the skillCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillCategories in body
     */
    @GetMapping("/skill-categories")
    @Timed
    public List<SkillCategory> getAllSkillCategories() {
        log.debug("REST request to get all SkillCategories");
        return skillCategoryRepository.findAll();
        }

    /**
     * GET  /skill-categories/:id : get the "id" skillCategory.
     *
     * @param id the id of the skillCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillCategory, or with status 404 (Not Found)
     */
    @GetMapping("/skill-categories/{id}")
    @Timed
    public ResponseEntity<SkillCategory> getSkillCategory(@PathVariable Long id) {
        log.debug("REST request to get SkillCategory : {}", id);
        SkillCategory skillCategory = skillCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skillCategory));
    }

    /**
     * DELETE  /skill-categories/:id : delete the "id" skillCategory.
     *
     * @param id the id of the skillCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillCategory(@PathVariable Long id) {
        log.debug("REST request to delete SkillCategory : {}", id);
        skillCategoryRepository.delete(id);
        skillCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/skill-categories?query=:query : search for the skillCategory corresponding
     * to the query.
     *
     * @param query the query of the skillCategory search
     * @return the result of the search
     */
    @GetMapping("/_search/skill-categories")
    @Timed
    public List<SkillCategory> searchSkillCategories(@RequestParam String query) {
        log.debug("REST request to search SkillCategories for query {}", query);
        return StreamSupport
            .stream(skillCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
