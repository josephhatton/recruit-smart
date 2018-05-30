package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.JobOrder;

import com.recruitsmart.repository.JobOrderRepository;
import com.recruitsmart.repository.search.JobOrderSearchRepository;
import com.recruitsmart.web.rest.errors.BadRequestAlertException;
import com.recruitsmart.web.rest.util.HeaderUtil;
import com.recruitsmart.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing JobOrder.
 */
@RestController
@RequestMapping("/api")
public class JobOrderResource {

    private final Logger log = LoggerFactory.getLogger(JobOrderResource.class);

    private static final String ENTITY_NAME = "jobOrder";

    private final JobOrderRepository jobOrderRepository;

    private final JobOrderSearchRepository jobOrderSearchRepository;

    public JobOrderResource(JobOrderRepository jobOrderRepository, JobOrderSearchRepository jobOrderSearchRepository) {
        this.jobOrderRepository = jobOrderRepository;
        this.jobOrderSearchRepository = jobOrderSearchRepository;
    }

    /**
     * POST  /job-orders : Create a new jobOrder.
     *
     * @param jobOrder the jobOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobOrder, or with status 400 (Bad Request) if the jobOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-orders")
    @Timed
    public ResponseEntity<JobOrder> createJobOrder(@RequestBody JobOrder jobOrder) throws URISyntaxException {
        log.debug("REST request to save JobOrder : {}", jobOrder);
        if (jobOrder.getId() != null) {
            throw new BadRequestAlertException("A new jobOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOrder result = jobOrderRepository.save(jobOrder);
        jobOrderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-orders : Updates an existing jobOrder.
     *
     * @param jobOrder the jobOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobOrder,
     * or with status 400 (Bad Request) if the jobOrder is not valid,
     * or with status 500 (Internal Server Error) if the jobOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-orders")
    @Timed
    public ResponseEntity<JobOrder> updateJobOrder(@RequestBody JobOrder jobOrder) throws URISyntaxException {
        log.debug("REST request to update JobOrder : {}", jobOrder);
        if (jobOrder.getId() == null) {
            return createJobOrder(jobOrder);
        }
        JobOrder result = jobOrderRepository.save(jobOrder);
        jobOrderSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-orders : get all the jobOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobOrders in body
     */
    @GetMapping("/job-orders")
    @Timed
    public ResponseEntity<List<JobOrder>> getAllJobOrders(Pageable pageable) {
        log.debug("REST request to get a page of JobOrders");
        Page<JobOrder> page = jobOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-orders/:id : get the "id" jobOrder.
     *
     * @param id the id of the jobOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobOrder, or with status 404 (Not Found)
     */
    @GetMapping("/job-orders/{id}")
    @Timed
    public ResponseEntity<JobOrder> getJobOrder(@PathVariable Long id) {
        log.debug("REST request to get JobOrder : {}", id);
        JobOrder jobOrder = jobOrderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobOrder));
    }

    /**
     * DELETE  /job-orders/:id : delete the "id" jobOrder.
     *
     * @param id the id of the jobOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobOrder(@PathVariable Long id) {
        log.debug("REST request to delete JobOrder : {}", id);
        jobOrderRepository.delete(id);
        jobOrderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-orders?query=:query : search for the jobOrder corresponding
     * to the query.
     *
     * @param query the query of the jobOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/job-orders")
    @Timed
    public ResponseEntity<List<JobOrder>> searchJobOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JobOrders for query {}", query);
        Page<JobOrder> page = jobOrderSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
