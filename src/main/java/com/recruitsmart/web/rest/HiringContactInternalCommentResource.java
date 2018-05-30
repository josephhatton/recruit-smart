package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.HiringContactInternalComment;

import com.recruitsmart.repository.HiringContactInternalCommentRepository;
import com.recruitsmart.repository.search.HiringContactInternalCommentSearchRepository;
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
 * REST controller for managing HiringContactInternalComment.
 */
@RestController
@RequestMapping("/api")
public class HiringContactInternalCommentResource {

    private final Logger log = LoggerFactory.getLogger(HiringContactInternalCommentResource.class);

    private static final String ENTITY_NAME = "hiringContactInternalComment";

    private final HiringContactInternalCommentRepository hiringContactInternalCommentRepository;

    private final HiringContactInternalCommentSearchRepository hiringContactInternalCommentSearchRepository;

    public HiringContactInternalCommentResource(HiringContactInternalCommentRepository hiringContactInternalCommentRepository, HiringContactInternalCommentSearchRepository hiringContactInternalCommentSearchRepository) {
        this.hiringContactInternalCommentRepository = hiringContactInternalCommentRepository;
        this.hiringContactInternalCommentSearchRepository = hiringContactInternalCommentSearchRepository;
    }

    /**
     * POST  /hiring-contact-internal-comments : Create a new hiringContactInternalComment.
     *
     * @param hiringContactInternalComment the hiringContactInternalComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hiringContactInternalComment, or with status 400 (Bad Request) if the hiringContactInternalComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hiring-contact-internal-comments")
    @Timed
    public ResponseEntity<HiringContactInternalComment> createHiringContactInternalComment(@RequestBody HiringContactInternalComment hiringContactInternalComment) throws URISyntaxException {
        log.debug("REST request to save HiringContactInternalComment : {}", hiringContactInternalComment);
        if (hiringContactInternalComment.getId() != null) {
            throw new BadRequestAlertException("A new hiringContactInternalComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HiringContactInternalComment result = hiringContactInternalCommentRepository.save(hiringContactInternalComment);
        hiringContactInternalCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hiring-contact-internal-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hiring-contact-internal-comments : Updates an existing hiringContactInternalComment.
     *
     * @param hiringContactInternalComment the hiringContactInternalComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hiringContactInternalComment,
     * or with status 400 (Bad Request) if the hiringContactInternalComment is not valid,
     * or with status 500 (Internal Server Error) if the hiringContactInternalComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hiring-contact-internal-comments")
    @Timed
    public ResponseEntity<HiringContactInternalComment> updateHiringContactInternalComment(@RequestBody HiringContactInternalComment hiringContactInternalComment) throws URISyntaxException {
        log.debug("REST request to update HiringContactInternalComment : {}", hiringContactInternalComment);
        if (hiringContactInternalComment.getId() == null) {
            return createHiringContactInternalComment(hiringContactInternalComment);
        }
        HiringContactInternalComment result = hiringContactInternalCommentRepository.save(hiringContactInternalComment);
        hiringContactInternalCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hiringContactInternalComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hiring-contact-internal-comments : get all the hiringContactInternalComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hiringContactInternalComments in body
     */
    @GetMapping("/hiring-contact-internal-comments")
    @Timed
    public List<HiringContactInternalComment> getAllHiringContactInternalComments() {
        log.debug("REST request to get all HiringContactInternalComments");
        return hiringContactInternalCommentRepository.findAll();
        }

    /**
     * GET  /hiring-contact-internal-comments/:id : get the "id" hiringContactInternalComment.
     *
     * @param id the id of the hiringContactInternalComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hiringContactInternalComment, or with status 404 (Not Found)
     */
    @GetMapping("/hiring-contact-internal-comments/{id}")
    @Timed
    public ResponseEntity<HiringContactInternalComment> getHiringContactInternalComment(@PathVariable Long id) {
        log.debug("REST request to get HiringContactInternalComment : {}", id);
        HiringContactInternalComment hiringContactInternalComment = hiringContactInternalCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hiringContactInternalComment));
    }

    /**
     * DELETE  /hiring-contact-internal-comments/:id : delete the "id" hiringContactInternalComment.
     *
     * @param id the id of the hiringContactInternalComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hiring-contact-internal-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteHiringContactInternalComment(@PathVariable Long id) {
        log.debug("REST request to delete HiringContactInternalComment : {}", id);
        hiringContactInternalCommentRepository.delete(id);
        hiringContactInternalCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hiring-contact-internal-comments?query=:query : search for the hiringContactInternalComment corresponding
     * to the query.
     *
     * @param query the query of the hiringContactInternalComment search
     * @return the result of the search
     */
    @GetMapping("/_search/hiring-contact-internal-comments")
    @Timed
    public List<HiringContactInternalComment> searchHiringContactInternalComments(@RequestParam String query) {
        log.debug("REST request to search HiringContactInternalComments for query {}", query);
        return StreamSupport
            .stream(hiringContactInternalCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
