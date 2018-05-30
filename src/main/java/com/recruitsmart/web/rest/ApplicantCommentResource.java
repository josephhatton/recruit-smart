package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.ApplicantComment;

import com.recruitsmart.repository.ApplicantCommentRepository;
import com.recruitsmart.repository.search.ApplicantCommentSearchRepository;
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
 * REST controller for managing ApplicantComment.
 */
@RestController
@RequestMapping("/api")
public class ApplicantCommentResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantCommentResource.class);

    private static final String ENTITY_NAME = "applicantComment";

    private final ApplicantCommentRepository applicantCommentRepository;

    private final ApplicantCommentSearchRepository applicantCommentSearchRepository;

    public ApplicantCommentResource(ApplicantCommentRepository applicantCommentRepository, ApplicantCommentSearchRepository applicantCommentSearchRepository) {
        this.applicantCommentRepository = applicantCommentRepository;
        this.applicantCommentSearchRepository = applicantCommentSearchRepository;
    }

    /**
     * POST  /applicant-comments : Create a new applicantComment.
     *
     * @param applicantComment the applicantComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicantComment, or with status 400 (Bad Request) if the applicantComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicant-comments")
    @Timed
    public ResponseEntity<ApplicantComment> createApplicantComment(@RequestBody ApplicantComment applicantComment) throws URISyntaxException {
        log.debug("REST request to save ApplicantComment : {}", applicantComment);
        if (applicantComment.getId() != null) {
            throw new BadRequestAlertException("A new applicantComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantComment result = applicantCommentRepository.save(applicantComment);
        applicantCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/applicant-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicant-comments : Updates an existing applicantComment.
     *
     * @param applicantComment the applicantComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicantComment,
     * or with status 400 (Bad Request) if the applicantComment is not valid,
     * or with status 500 (Internal Server Error) if the applicantComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicant-comments")
    @Timed
    public ResponseEntity<ApplicantComment> updateApplicantComment(@RequestBody ApplicantComment applicantComment) throws URISyntaxException {
        log.debug("REST request to update ApplicantComment : {}", applicantComment);
        if (applicantComment.getId() == null) {
            return createApplicantComment(applicantComment);
        }
        ApplicantComment result = applicantCommentRepository.save(applicantComment);
        applicantCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicantComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicant-comments : get all the applicantComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicantComments in body
     */
    @GetMapping("/applicant-comments")
    @Timed
    public List<ApplicantComment> getAllApplicantComments() {
        log.debug("REST request to get all ApplicantComments");
        return applicantCommentRepository.findAll();
        }

    /**
     * GET  /applicant-comments/:id : get the "id" applicantComment.
     *
     * @param id the id of the applicantComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicantComment, or with status 404 (Not Found)
     */
    @GetMapping("/applicant-comments/{id}")
    @Timed
    public ResponseEntity<ApplicantComment> getApplicantComment(@PathVariable Long id) {
        log.debug("REST request to get ApplicantComment : {}", id);
        ApplicantComment applicantComment = applicantCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicantComment));
    }

    /**
     * DELETE  /applicant-comments/:id : delete the "id" applicantComment.
     *
     * @param id the id of the applicantComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicant-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicantComment(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantComment : {}", id);
        applicantCommentRepository.delete(id);
        applicantCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/applicant-comments?query=:query : search for the applicantComment corresponding
     * to the query.
     *
     * @param query the query of the applicantComment search
     * @return the result of the search
     */
    @GetMapping("/_search/applicant-comments")
    @Timed
    public List<ApplicantComment> searchApplicantComments(@RequestParam String query) {
        log.debug("REST request to search ApplicantComments for query {}", query);
        return StreamSupport
            .stream(applicantCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
