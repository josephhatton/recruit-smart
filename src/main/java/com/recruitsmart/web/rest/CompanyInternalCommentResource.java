package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.CompanyInternalComment;

import com.recruitsmart.repository.CompanyInternalCommentRepository;
import com.recruitsmart.repository.search.CompanyInternalCommentSearchRepository;
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
 * REST controller for managing CompanyInternalComment.
 */
@RestController
@RequestMapping("/api")
public class CompanyInternalCommentResource {

    private final Logger log = LoggerFactory.getLogger(CompanyInternalCommentResource.class);

    private static final String ENTITY_NAME = "companyInternalComment";

    private final CompanyInternalCommentRepository companyInternalCommentRepository;

    private final CompanyInternalCommentSearchRepository companyInternalCommentSearchRepository;

    public CompanyInternalCommentResource(CompanyInternalCommentRepository companyInternalCommentRepository, CompanyInternalCommentSearchRepository companyInternalCommentSearchRepository) {
        this.companyInternalCommentRepository = companyInternalCommentRepository;
        this.companyInternalCommentSearchRepository = companyInternalCommentSearchRepository;
    }

    /**
     * POST  /company-internal-comments : Create a new companyInternalComment.
     *
     * @param companyInternalComment the companyInternalComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyInternalComment, or with status 400 (Bad Request) if the companyInternalComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-internal-comments")
    @Timed
    public ResponseEntity<CompanyInternalComment> createCompanyInternalComment(@RequestBody CompanyInternalComment companyInternalComment) throws URISyntaxException {
        log.debug("REST request to save CompanyInternalComment : {}", companyInternalComment);
        if (companyInternalComment.getId() != null) {
            throw new BadRequestAlertException("A new companyInternalComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyInternalComment result = companyInternalCommentRepository.save(companyInternalComment);
        companyInternalCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/company-internal-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-internal-comments : Updates an existing companyInternalComment.
     *
     * @param companyInternalComment the companyInternalComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyInternalComment,
     * or with status 400 (Bad Request) if the companyInternalComment is not valid,
     * or with status 500 (Internal Server Error) if the companyInternalComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-internal-comments")
    @Timed
    public ResponseEntity<CompanyInternalComment> updateCompanyInternalComment(@RequestBody CompanyInternalComment companyInternalComment) throws URISyntaxException {
        log.debug("REST request to update CompanyInternalComment : {}", companyInternalComment);
        if (companyInternalComment.getId() == null) {
            return createCompanyInternalComment(companyInternalComment);
        }
        CompanyInternalComment result = companyInternalCommentRepository.save(companyInternalComment);
        companyInternalCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyInternalComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-internal-comments : get all the companyInternalComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyInternalComments in body
     */
    @GetMapping("/company-internal-comments")
    @Timed
    public List<CompanyInternalComment> getAllCompanyInternalComments() {
        log.debug("REST request to get all CompanyInternalComments");
        return companyInternalCommentRepository.findAll();
        }

    /**
     * GET  /company-internal-comments/:id : get the "id" companyInternalComment.
     *
     * @param id the id of the companyInternalComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyInternalComment, or with status 404 (Not Found)
     */
    @GetMapping("/company-internal-comments/{id}")
    @Timed
    public ResponseEntity<CompanyInternalComment> getCompanyInternalComment(@PathVariable Long id) {
        log.debug("REST request to get CompanyInternalComment : {}", id);
        CompanyInternalComment companyInternalComment = companyInternalCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyInternalComment));
    }

    /**
     * DELETE  /company-internal-comments/:id : delete the "id" companyInternalComment.
     *
     * @param id the id of the companyInternalComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-internal-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyInternalComment(@PathVariable Long id) {
        log.debug("REST request to delete CompanyInternalComment : {}", id);
        companyInternalCommentRepository.delete(id);
        companyInternalCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-internal-comments?query=:query : search for the companyInternalComment corresponding
     * to the query.
     *
     * @param query the query of the companyInternalComment search
     * @return the result of the search
     */
    @GetMapping("/_search/company-internal-comments")
    @Timed
    public List<CompanyInternalComment> searchCompanyInternalComments(@RequestParam String query) {
        log.debug("REST request to search CompanyInternalComments for query {}", query);
        return StreamSupport
            .stream(companyInternalCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
