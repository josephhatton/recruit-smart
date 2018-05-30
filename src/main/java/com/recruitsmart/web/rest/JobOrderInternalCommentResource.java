package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.JobOrderInternalComment;

import com.recruitsmart.repository.JobOrderInternalCommentRepository;
import com.recruitsmart.repository.search.JobOrderInternalCommentSearchRepository;
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
 * REST controller for managing JobOrderInternalComment.
 */
@RestController
@RequestMapping("/api")
public class JobOrderInternalCommentResource {

    private final Logger log = LoggerFactory.getLogger(JobOrderInternalCommentResource.class);

    private static final String ENTITY_NAME = "jobOrderInternalComment";

    private final JobOrderInternalCommentRepository jobOrderInternalCommentRepository;

    private final JobOrderInternalCommentSearchRepository jobOrderInternalCommentSearchRepository;

    public JobOrderInternalCommentResource(JobOrderInternalCommentRepository jobOrderInternalCommentRepository, JobOrderInternalCommentSearchRepository jobOrderInternalCommentSearchRepository) {
        this.jobOrderInternalCommentRepository = jobOrderInternalCommentRepository;
        this.jobOrderInternalCommentSearchRepository = jobOrderInternalCommentSearchRepository;
    }

    /**
     * POST  /job-order-internal-comments : Create a new jobOrderInternalComment.
     *
     * @param jobOrderInternalComment the jobOrderInternalComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobOrderInternalComment, or with status 400 (Bad Request) if the jobOrderInternalComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-order-internal-comments")
    @Timed
    public ResponseEntity<JobOrderInternalComment> createJobOrderInternalComment(@RequestBody JobOrderInternalComment jobOrderInternalComment) throws URISyntaxException {
        log.debug("REST request to save JobOrderInternalComment : {}", jobOrderInternalComment);
        if (jobOrderInternalComment.getId() != null) {
            throw new BadRequestAlertException("A new jobOrderInternalComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOrderInternalComment result = jobOrderInternalCommentRepository.save(jobOrderInternalComment);
        jobOrderInternalCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-order-internal-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-order-internal-comments : Updates an existing jobOrderInternalComment.
     *
     * @param jobOrderInternalComment the jobOrderInternalComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobOrderInternalComment,
     * or with status 400 (Bad Request) if the jobOrderInternalComment is not valid,
     * or with status 500 (Internal Server Error) if the jobOrderInternalComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-order-internal-comments")
    @Timed
    public ResponseEntity<JobOrderInternalComment> updateJobOrderInternalComment(@RequestBody JobOrderInternalComment jobOrderInternalComment) throws URISyntaxException {
        log.debug("REST request to update JobOrderInternalComment : {}", jobOrderInternalComment);
        if (jobOrderInternalComment.getId() == null) {
            return createJobOrderInternalComment(jobOrderInternalComment);
        }
        JobOrderInternalComment result = jobOrderInternalCommentRepository.save(jobOrderInternalComment);
        jobOrderInternalCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOrderInternalComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-order-internal-comments : get all the jobOrderInternalComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobOrderInternalComments in body
     */
    @GetMapping("/job-order-internal-comments")
    @Timed
    public List<JobOrderInternalComment> getAllJobOrderInternalComments() {
        log.debug("REST request to get all JobOrderInternalComments");
        return jobOrderInternalCommentRepository.findAll();
        }

    /**
     * GET  /job-order-internal-comments/:id : get the "id" jobOrderInternalComment.
     *
     * @param id the id of the jobOrderInternalComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobOrderInternalComment, or with status 404 (Not Found)
     */
    @GetMapping("/job-order-internal-comments/{id}")
    @Timed
    public ResponseEntity<JobOrderInternalComment> getJobOrderInternalComment(@PathVariable Long id) {
        log.debug("REST request to get JobOrderInternalComment : {}", id);
        JobOrderInternalComment jobOrderInternalComment = jobOrderInternalCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobOrderInternalComment));
    }

    /**
     * DELETE  /job-order-internal-comments/:id : delete the "id" jobOrderInternalComment.
     *
     * @param id the id of the jobOrderInternalComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-order-internal-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobOrderInternalComment(@PathVariable Long id) {
        log.debug("REST request to delete JobOrderInternalComment : {}", id);
        jobOrderInternalCommentRepository.delete(id);
        jobOrderInternalCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-order-internal-comments?query=:query : search for the jobOrderInternalComment corresponding
     * to the query.
     *
     * @param query the query of the jobOrderInternalComment search
     * @return the result of the search
     */
    @GetMapping("/_search/job-order-internal-comments")
    @Timed
    public List<JobOrderInternalComment> searchJobOrderInternalComments(@RequestParam String query) {
        log.debug("REST request to search JobOrderInternalComments for query {}", query);
        return StreamSupport
            .stream(jobOrderInternalCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
