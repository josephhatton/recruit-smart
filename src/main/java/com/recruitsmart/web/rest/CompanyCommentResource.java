package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.CompanyComment;

import com.recruitsmart.repository.CompanyCommentRepository;
import com.recruitsmart.repository.search.CompanyCommentSearchRepository;
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
 * REST controller for managing CompanyComment.
 */
@RestController
@RequestMapping("/api")
public class CompanyCommentResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCommentResource.class);

    private static final String ENTITY_NAME = "companyComment";

    private final CompanyCommentRepository companyCommentRepository;

    private final CompanyCommentSearchRepository companyCommentSearchRepository;

    public CompanyCommentResource(CompanyCommentRepository companyCommentRepository, CompanyCommentSearchRepository companyCommentSearchRepository) {
        this.companyCommentRepository = companyCommentRepository;
        this.companyCommentSearchRepository = companyCommentSearchRepository;
    }

    /**
     * POST  /company-comments : Create a new companyComment.
     *
     * @param companyComment the companyComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyComment, or with status 400 (Bad Request) if the companyComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-comments")
    @Timed
    public ResponseEntity<CompanyComment> createCompanyComment(@RequestBody CompanyComment companyComment) throws URISyntaxException {
        log.debug("REST request to save CompanyComment : {}", companyComment);
        if (companyComment.getId() != null) {
            throw new BadRequestAlertException("A new companyComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyComment result = companyCommentRepository.save(companyComment);
        companyCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/company-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-comments : Updates an existing companyComment.
     *
     * @param companyComment the companyComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyComment,
     * or with status 400 (Bad Request) if the companyComment is not valid,
     * or with status 500 (Internal Server Error) if the companyComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-comments")
    @Timed
    public ResponseEntity<CompanyComment> updateCompanyComment(@RequestBody CompanyComment companyComment) throws URISyntaxException {
        log.debug("REST request to update CompanyComment : {}", companyComment);
        if (companyComment.getId() == null) {
            return createCompanyComment(companyComment);
        }
        CompanyComment result = companyCommentRepository.save(companyComment);
        companyCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-comments : get all the companyComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyComments in body
     */
    @GetMapping("/company-comments")
    @Timed
    public List<CompanyComment> getAllCompanyComments() {
        log.debug("REST request to get all CompanyComments");
        return companyCommentRepository.findAll();
        }

    /**
     * GET  /company-comments/:id : get the "id" companyComment.
     *
     * @param id the id of the companyComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyComment, or with status 404 (Not Found)
     */
    @GetMapping("/company-comments/{id}")
    @Timed
    public ResponseEntity<CompanyComment> getCompanyComment(@PathVariable Long id) {
        log.debug("REST request to get CompanyComment : {}", id);
        CompanyComment companyComment = companyCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyComment));
    }

    /**
     * DELETE  /company-comments/:id : delete the "id" companyComment.
     *
     * @param id the id of the companyComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyComment(@PathVariable Long id) {
        log.debug("REST request to delete CompanyComment : {}", id);
        companyCommentRepository.delete(id);
        companyCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-comments?query=:query : search for the companyComment corresponding
     * to the query.
     *
     * @param query the query of the companyComment search
     * @return the result of the search
     */
    @GetMapping("/_search/company-comments")
    @Timed
    public List<CompanyComment> searchCompanyComments(@RequestParam String query) {
        log.debug("REST request to search CompanyComments for query {}", query);
        return StreamSupport
            .stream(companyCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
