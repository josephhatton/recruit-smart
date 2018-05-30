package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.Applicant;

import com.recruitsmart.repository.ApplicantRepository;
import com.recruitsmart.repository.search.ApplicantSearchRepository;
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
 * REST controller for managing Applicant.
 */
@RestController
@RequestMapping("/api")
public class ApplicantResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantResource.class);

    private static final String ENTITY_NAME = "applicant";

    private final ApplicantRepository applicantRepository;

    private final ApplicantSearchRepository applicantSearchRepository;

    public ApplicantResource(ApplicantRepository applicantRepository, ApplicantSearchRepository applicantSearchRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantSearchRepository = applicantSearchRepository;
    }

    /**
     * POST  /applicants : Create a new applicant.
     *
     * @param applicant the applicant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicant, or with status 400 (Bad Request) if the applicant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applicants")
    @Timed
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) throws URISyntaxException {
        log.debug("REST request to save Applicant : {}", applicant);
        if (applicant.getId() != null) {
            throw new BadRequestAlertException("A new applicant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Applicant result = applicantRepository.save(applicant);
        applicantSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicants : Updates an existing applicant.
     *
     * @param applicant the applicant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicant,
     * or with status 400 (Bad Request) if the applicant is not valid,
     * or with status 500 (Internal Server Error) if the applicant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applicants")
    @Timed
    public ResponseEntity<Applicant> updateApplicant(@RequestBody Applicant applicant) throws URISyntaxException {
        log.debug("REST request to update Applicant : {}", applicant);
        if (applicant.getId() == null) {
            return createApplicant(applicant);
        }
        Applicant result = applicantRepository.save(applicant);
        applicantSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicants : get all the applicants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicants in body
     */
    @GetMapping("/applicants")
    @Timed
    public ResponseEntity<List<Applicant>> getAllApplicants(Pageable pageable) {
        log.debug("REST request to get a page of Applicants");
        Page<Applicant> page = applicantRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applicants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /applicants/:id : get the "id" applicant.
     *
     * @param id the id of the applicant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicant, or with status 404 (Not Found)
     */
    @GetMapping("/applicants/{id}")
    @Timed
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        log.debug("REST request to get Applicant : {}", id);
        Applicant applicant = applicantRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicant));
    }

    /**
     * DELETE  /applicants/:id : delete the "id" applicant.
     *
     * @param id the id of the applicant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/applicants/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicant(@PathVariable Long id) {
        log.debug("REST request to delete Applicant : {}", id);
        applicantRepository.delete(id);
        applicantSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/applicants?query=:query : search for the applicant corresponding
     * to the query.
     *
     * @param query the query of the applicant search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/applicants")
    @Timed
    public ResponseEntity<List<Applicant>> searchApplicants(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Applicants for query {}", query);
        Page<Applicant> page = applicantSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/applicants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
