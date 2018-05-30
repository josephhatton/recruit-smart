package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.WorkHistory;

import com.recruitsmart.repository.WorkHistoryRepository;
import com.recruitsmart.repository.search.WorkHistorySearchRepository;
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
 * REST controller for managing WorkHistory.
 */
@RestController
@RequestMapping("/api")
public class WorkHistoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkHistoryResource.class);

    private static final String ENTITY_NAME = "workHistory";

    private final WorkHistoryRepository workHistoryRepository;

    private final WorkHistorySearchRepository workHistorySearchRepository;

    public WorkHistoryResource(WorkHistoryRepository workHistoryRepository, WorkHistorySearchRepository workHistorySearchRepository) {
        this.workHistoryRepository = workHistoryRepository;
        this.workHistorySearchRepository = workHistorySearchRepository;
    }

    /**
     * POST  /work-histories : Create a new workHistory.
     *
     * @param workHistory the workHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workHistory, or with status 400 (Bad Request) if the workHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-histories")
    @Timed
    public ResponseEntity<WorkHistory> createWorkHistory(@RequestBody WorkHistory workHistory) throws URISyntaxException {
        log.debug("REST request to save WorkHistory : {}", workHistory);
        if (workHistory.getId() != null) {
            throw new BadRequestAlertException("A new workHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkHistory result = workHistoryRepository.save(workHistory);
        workHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/work-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-histories : Updates an existing workHistory.
     *
     * @param workHistory the workHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workHistory,
     * or with status 400 (Bad Request) if the workHistory is not valid,
     * or with status 500 (Internal Server Error) if the workHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-histories")
    @Timed
    public ResponseEntity<WorkHistory> updateWorkHistory(@RequestBody WorkHistory workHistory) throws URISyntaxException {
        log.debug("REST request to update WorkHistory : {}", workHistory);
        if (workHistory.getId() == null) {
            return createWorkHistory(workHistory);
        }
        WorkHistory result = workHistoryRepository.save(workHistory);
        workHistorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-histories : get all the workHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workHistories in body
     */
    @GetMapping("/work-histories")
    @Timed
    public List<WorkHistory> getAllWorkHistories() {
        log.debug("REST request to get all WorkHistories");
        return workHistoryRepository.findAll();
        }

    /**
     * GET  /work-histories/:id : get the "id" workHistory.
     *
     * @param id the id of the workHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workHistory, or with status 404 (Not Found)
     */
    @GetMapping("/work-histories/{id}")
    @Timed
    public ResponseEntity<WorkHistory> getWorkHistory(@PathVariable Long id) {
        log.debug("REST request to get WorkHistory : {}", id);
        WorkHistory workHistory = workHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workHistory));
    }

    /**
     * DELETE  /work-histories/:id : delete the "id" workHistory.
     *
     * @param id the id of the workHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkHistory(@PathVariable Long id) {
        log.debug("REST request to delete WorkHistory : {}", id);
        workHistoryRepository.delete(id);
        workHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/work-histories?query=:query : search for the workHistory corresponding
     * to the query.
     *
     * @param query the query of the workHistory search
     * @return the result of the search
     */
    @GetMapping("/_search/work-histories")
    @Timed
    public List<WorkHistory> searchWorkHistories(@RequestParam String query) {
        log.debug("REST request to search WorkHistories for query {}", query);
        return StreamSupport
            .stream(workHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
