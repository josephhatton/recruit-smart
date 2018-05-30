package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.ApplicantStatus;

import com.recruitsmart.repository.ApplicantStatusRepository;
import com.recruitsmart.repository.search.ApplicantStatusSearchRepository;
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
 * REST controller for managing ApplicantStatus.
 */
@RestController
@RequestMapping("/api")
public class ApplicantStatusResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantStatusResource.class);

    private static final String ENTITY_NAME = "applicantStatus";

    private final ApplicantStatusRepository applicantStatusRepository;

    private final ApplicantStatusSearchRepository applicantStatusSearchRepository;

    public ApplicantStatusResource(ApplicantStatusRepository applicantStatusRepository, ApplicantStatusSearchRepository applicantStatusSearchRepository) {
        this.applicantStatusRepository = applicantStatusRepository;
        this.applicantStatusSearchRepository = applicantStatusSearchRepository;
    }

    /**
     * POST  /applicant-statuses : Create a new applicantStatus.
     *
     * @param applicantStatus the applicantStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicantStatus, or with status 400 (Bad Request) if the applicantStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicant-statuses")
    @Timed
    public ResponseEntity<ApplicantStatus> createApplicantStatus(@RequestBody ApplicantStatus applicantStatus) throws URISyntaxException {
        log.debug("REST request to save ApplicantStatus : {}", applicantStatus);
        if (applicantStatus.getId() != null) {
            throw new BadRequestAlertException("A new applicantStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantStatus result = applicantStatusRepository.save(applicantStatus);
        applicantStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/applicant-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicant-statuses : Updates an existing applicantStatus.
     *
     * @param applicantStatus the applicantStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicantStatus,
     * or with status 400 (Bad Request) if the applicantStatus is not valid,
     * or with status 500 (Internal Server Error) if the applicantStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicant-statuses")
    @Timed
    public ResponseEntity<ApplicantStatus> updateApplicantStatus(@RequestBody ApplicantStatus applicantStatus) throws URISyntaxException {
        log.debug("REST request to update ApplicantStatus : {}", applicantStatus);
        if (applicantStatus.getId() == null) {
            return createApplicantStatus(applicantStatus);
        }
        ApplicantStatus result = applicantStatusRepository.save(applicantStatus);
        applicantStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicantStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicant-statuses : get all the applicantStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicantStatuses in body
     */
    @GetMapping("/applicant-statuses")
    @Timed
    public List<ApplicantStatus> getAllApplicantStatuses() {
        log.debug("REST request to get all ApplicantStatuses");
        return applicantStatusRepository.findAll();
        }

    /**
     * GET  /applicant-statuses/:id : get the "id" applicantStatus.
     *
     * @param id the id of the applicantStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicantStatus, or with status 404 (Not Found)
     */
    @GetMapping("/applicant-statuses/{id}")
    @Timed
    public ResponseEntity<ApplicantStatus> getApplicantStatus(@PathVariable Long id) {
        log.debug("REST request to get ApplicantStatus : {}", id);
        ApplicantStatus applicantStatus = applicantStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicantStatus));
    }

    /**
     * DELETE  /applicant-statuses/:id : delete the "id" applicantStatus.
     *
     * @param id the id of the applicantStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicant-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicantStatus(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantStatus : {}", id);
        applicantStatusRepository.delete(id);
        applicantStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/applicant-statuses?query=:query : search for the applicantStatus corresponding
     * to the query.
     *
     * @param query the query of the applicantStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/applicant-statuses")
    @Timed
    public List<ApplicantStatus> searchApplicantStatuses(@RequestParam String query) {
        log.debug("REST request to search ApplicantStatuses for query {}", query);
        return StreamSupport
            .stream(applicantStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
