package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.JobOrderComment;

import com.recruitsmart.repository.JobOrderCommentRepository;
import com.recruitsmart.repository.search.JobOrderCommentSearchRepository;
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
 * REST controller for managing JobOrderComment.
 */
@RestController
@RequestMapping("/api")
public class JobOrderCommentResource {

    private final Logger log = LoggerFactory.getLogger(JobOrderCommentResource.class);

    private static final String ENTITY_NAME = "jobOrderComment";

    private final JobOrderCommentRepository jobOrderCommentRepository;

    private final JobOrderCommentSearchRepository jobOrderCommentSearchRepository;

    public JobOrderCommentResource(JobOrderCommentRepository jobOrderCommentRepository, JobOrderCommentSearchRepository jobOrderCommentSearchRepository) {
        this.jobOrderCommentRepository = jobOrderCommentRepository;
        this.jobOrderCommentSearchRepository = jobOrderCommentSearchRepository;
    }

    /**
     * POST  /job-order-comments : Create a new jobOrderComment.
     *
     * @param jobOrderComment the jobOrderComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobOrderComment, or with status 400 (Bad Request) if the jobOrderComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-order-comments")
    @Timed
    public ResponseEntity<JobOrderComment> createJobOrderComment(@RequestBody JobOrderComment jobOrderComment) throws URISyntaxException {
        log.debug("REST request to save JobOrderComment : {}", jobOrderComment);
        if (jobOrderComment.getId() != null) {
            throw new BadRequestAlertException("A new jobOrderComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOrderComment result = jobOrderCommentRepository.save(jobOrderComment);
        jobOrderCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-order-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-order-comments : Updates an existing jobOrderComment.
     *
     * @param jobOrderComment the jobOrderComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobOrderComment,
     * or with status 400 (Bad Request) if the jobOrderComment is not valid,
     * or with status 500 (Internal Server Error) if the jobOrderComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-order-comments")
    @Timed
    public ResponseEntity<JobOrderComment> updateJobOrderComment(@RequestBody JobOrderComment jobOrderComment) throws URISyntaxException {
        log.debug("REST request to update JobOrderComment : {}", jobOrderComment);
        if (jobOrderComment.getId() == null) {
            return createJobOrderComment(jobOrderComment);
        }
        JobOrderComment result = jobOrderCommentRepository.save(jobOrderComment);
        jobOrderCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOrderComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-order-comments : get all the jobOrderComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobOrderComments in body
     */
    @GetMapping("/job-order-comments")
    @Timed
    public List<JobOrderComment> getAllJobOrderComments() {
        log.debug("REST request to get all JobOrderComments");
        return jobOrderCommentRepository.findAll();
        }

    /**
     * GET  /job-order-comments/:id : get the "id" jobOrderComment.
     *
     * @param id the id of the jobOrderComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobOrderComment, or with status 404 (Not Found)
     */
    @GetMapping("/job-order-comments/{id}")
    @Timed
    public ResponseEntity<JobOrderComment> getJobOrderComment(@PathVariable Long id) {
        log.debug("REST request to get JobOrderComment : {}", id);
        JobOrderComment jobOrderComment = jobOrderCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobOrderComment));
    }

    /**
     * DELETE  /job-order-comments/:id : delete the "id" jobOrderComment.
     *
     * @param id the id of the jobOrderComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-order-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobOrderComment(@PathVariable Long id) {
        log.debug("REST request to delete JobOrderComment : {}", id);
        jobOrderCommentRepository.delete(id);
        jobOrderCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-order-comments?query=:query : search for the jobOrderComment corresponding
     * to the query.
     *
     * @param query the query of the jobOrderComment search
     * @return the result of the search
     */
    @GetMapping("/_search/job-order-comments")
    @Timed
    public List<JobOrderComment> searchJobOrderComments(@RequestParam String query) {
        log.debug("REST request to search JobOrderComments for query {}", query);
        return StreamSupport
            .stream(jobOrderCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
