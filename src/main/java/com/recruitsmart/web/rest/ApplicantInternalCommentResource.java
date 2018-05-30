package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.ApplicantInternalComment;

import com.recruitsmart.repository.ApplicantInternalCommentRepository;
import com.recruitsmart.repository.search.ApplicantInternalCommentSearchRepository;
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
 * REST controller for managing ApplicantInternalComment.
 */
@RestController
@RequestMapping("/api")
public class ApplicantInternalCommentResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantInternalCommentResource.class);

    private static final String ENTITY_NAME = "applicantInternalComment";

    private final ApplicantInternalCommentRepository applicantInternalCommentRepository;

    private final ApplicantInternalCommentSearchRepository applicantInternalCommentSearchRepository;

    public ApplicantInternalCommentResource(ApplicantInternalCommentRepository applicantInternalCommentRepository, ApplicantInternalCommentSearchRepository applicantInternalCommentSearchRepository) {
        this.applicantInternalCommentRepository = applicantInternalCommentRepository;
        this.applicantInternalCommentSearchRepository = applicantInternalCommentSearchRepository;
    }

    /**
     * POST  /applicant-internal-comments : Create a new applicantInternalComment.
     *
     * @param applicantInternalComment the applicantInternalComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicantInternalComment, or with status 400 (Bad Request) if the applicantInternalComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicant-internal-comments")
    @Timed
    public ResponseEntity<ApplicantInternalComment> createApplicantInternalComment(@RequestBody ApplicantInternalComment applicantInternalComment) throws URISyntaxException {
        log.debug("REST request to save ApplicantInternalComment : {}", applicantInternalComment);
        if (applicantInternalComment.getId() != null) {
            throw new BadRequestAlertException("A new applicantInternalComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantInternalComment result = applicantInternalCommentRepository.save(applicantInternalComment);
        applicantInternalCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/applicant-internal-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicant-internal-comments : Updates an existing applicantInternalComment.
     *
     * @param applicantInternalComment the applicantInternalComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicantInternalComment,
     * or with status 400 (Bad Request) if the applicantInternalComment is not valid,
     * or with status 500 (Internal Server Error) if the applicantInternalComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicant-internal-comments")
    @Timed
    public ResponseEntity<ApplicantInternalComment> updateApplicantInternalComment(@RequestBody ApplicantInternalComment applicantInternalComment) throws URISyntaxException {
        log.debug("REST request to update ApplicantInternalComment : {}", applicantInternalComment);
        if (applicantInternalComment.getId() == null) {
            return createApplicantInternalComment(applicantInternalComment);
        }
        ApplicantInternalComment result = applicantInternalCommentRepository.save(applicantInternalComment);
        applicantInternalCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicantInternalComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicant-internal-comments : get all the applicantInternalComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicantInternalComments in body
     */
    @GetMapping("/applicant-internal-comments")
    @Timed
    public List<ApplicantInternalComment> getAllApplicantInternalComments() {
        log.debug("REST request to get all ApplicantInternalComments");
        return applicantInternalCommentRepository.findAll();
        }

    /**
     * GET  /applicant-internal-comments/:id : get the "id" applicantInternalComment.
     *
     * @param id the id of the applicantInternalComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicantInternalComment, or with status 404 (Not Found)
     */
    @GetMapping("/applicant-internal-comments/{id}")
    @Timed
    public ResponseEntity<ApplicantInternalComment> getApplicantInternalComment(@PathVariable Long id) {
        log.debug("REST request to get ApplicantInternalComment : {}", id);
        ApplicantInternalComment applicantInternalComment = applicantInternalCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicantInternalComment));
    }

    /**
     * DELETE  /applicant-internal-comments/:id : delete the "id" applicantInternalComment.
     *
     * @param id the id of the applicantInternalComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicant-internal-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicantInternalComment(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantInternalComment : {}", id);
        applicantInternalCommentRepository.delete(id);
        applicantInternalCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/applicant-internal-comments?query=:query : search for the applicantInternalComment corresponding
     * to the query.
     *
     * @param query the query of the applicantInternalComment search
     * @return the result of the search
     */
    @GetMapping("/_search/applicant-internal-comments")
    @Timed
    public List<ApplicantInternalComment> searchApplicantInternalComments(@RequestParam String query) {
        log.debug("REST request to search ApplicantInternalComments for query {}", query);
        return StreamSupport
            .stream(applicantInternalCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
