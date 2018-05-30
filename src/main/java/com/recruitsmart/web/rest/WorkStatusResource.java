package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.WorkStatus;

import com.recruitsmart.repository.WorkStatusRepository;
import com.recruitsmart.repository.search.WorkStatusSearchRepository;
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
 * REST controller for managing WorkStatus.
 */
@RestController
@RequestMapping("/api")
public class WorkStatusResource {

    private final Logger log = LoggerFactory.getLogger(WorkStatusResource.class);

    private static final String ENTITY_NAME = "workStatus";

    private final WorkStatusRepository workStatusRepository;

    private final WorkStatusSearchRepository workStatusSearchRepository;

    public WorkStatusResource(WorkStatusRepository workStatusRepository, WorkStatusSearchRepository workStatusSearchRepository) {
        this.workStatusRepository = workStatusRepository;
        this.workStatusSearchRepository = workStatusSearchRepository;
    }

    /**
     * POST  /work-statuses : Create a new workStatus.
     *
     * @param workStatus the workStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workStatus, or with status 400 (Bad Request) if the workStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-statuses")
    @Timed
    public ResponseEntity<WorkStatus> createWorkStatus(@RequestBody WorkStatus workStatus) throws URISyntaxException {
        log.debug("REST request to save WorkStatus : {}", workStatus);
        if (workStatus.getId() != null) {
            throw new BadRequestAlertException("A new workStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkStatus result = workStatusRepository.save(workStatus);
        workStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/work-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-statuses : Updates an existing workStatus.
     *
     * @param workStatus the workStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workStatus,
     * or with status 400 (Bad Request) if the workStatus is not valid,
     * or with status 500 (Internal Server Error) if the workStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-statuses")
    @Timed
    public ResponseEntity<WorkStatus> updateWorkStatus(@RequestBody WorkStatus workStatus) throws URISyntaxException {
        log.debug("REST request to update WorkStatus : {}", workStatus);
        if (workStatus.getId() == null) {
            return createWorkStatus(workStatus);
        }
        WorkStatus result = workStatusRepository.save(workStatus);
        workStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-statuses : get all the workStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workStatuses in body
     */
    @GetMapping("/work-statuses")
    @Timed
    public List<WorkStatus> getAllWorkStatuses() {
        log.debug("REST request to get all WorkStatuses");
        return workStatusRepository.findAll();
        }

    /**
     * GET  /work-statuses/:id : get the "id" workStatus.
     *
     * @param id the id of the workStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workStatus, or with status 404 (Not Found)
     */
    @GetMapping("/work-statuses/{id}")
    @Timed
    public ResponseEntity<WorkStatus> getWorkStatus(@PathVariable Long id) {
        log.debug("REST request to get WorkStatus : {}", id);
        WorkStatus workStatus = workStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workStatus));
    }

    /**
     * DELETE  /work-statuses/:id : delete the "id" workStatus.
     *
     * @param id the id of the workStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkStatus(@PathVariable Long id) {
        log.debug("REST request to delete WorkStatus : {}", id);
        workStatusRepository.delete(id);
        workStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/work-statuses?query=:query : search for the workStatus corresponding
     * to the query.
     *
     * @param query the query of the workStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/work-statuses")
    @Timed
    public List<WorkStatus> searchWorkStatuses(@RequestParam String query) {
        log.debug("REST request to search WorkStatuses for query {}", query);
        return StreamSupport
            .stream(workStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
