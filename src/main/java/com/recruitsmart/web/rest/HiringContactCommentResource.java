package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.HiringContactComment;

import com.recruitsmart.repository.HiringContactCommentRepository;
import com.recruitsmart.repository.search.HiringContactCommentSearchRepository;
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
 * REST controller for managing HiringContactComment.
 */
@RestController
@RequestMapping("/api")
public class HiringContactCommentResource {

    private final Logger log = LoggerFactory.getLogger(HiringContactCommentResource.class);

    private static final String ENTITY_NAME = "hiringContactComment";

    private final HiringContactCommentRepository hiringContactCommentRepository;

    private final HiringContactCommentSearchRepository hiringContactCommentSearchRepository;

    public HiringContactCommentResource(HiringContactCommentRepository hiringContactCommentRepository, HiringContactCommentSearchRepository hiringContactCommentSearchRepository) {
        this.hiringContactCommentRepository = hiringContactCommentRepository;
        this.hiringContactCommentSearchRepository = hiringContactCommentSearchRepository;
    }

    /**
     * POST  /hiring-contact-comments : Create a new hiringContactComment.
     *
     * @param hiringContactComment the hiringContactComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hiringContactComment, or with status 400 (Bad Request) if the hiringContactComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hiring-contact-comments")
    @Timed
    public ResponseEntity<HiringContactComment> createHiringContactComment(@RequestBody HiringContactComment hiringContactComment) throws URISyntaxException {
        log.debug("REST request to save HiringContactComment : {}", hiringContactComment);
        if (hiringContactComment.getId() != null) {
            throw new BadRequestAlertException("A new hiringContactComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HiringContactComment result = hiringContactCommentRepository.save(hiringContactComment);
        hiringContactCommentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hiring-contact-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hiring-contact-comments : Updates an existing hiringContactComment.
     *
     * @param hiringContactComment the hiringContactComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hiringContactComment,
     * or with status 400 (Bad Request) if the hiringContactComment is not valid,
     * or with status 500 (Internal Server Error) if the hiringContactComment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hiring-contact-comments")
    @Timed
    public ResponseEntity<HiringContactComment> updateHiringContactComment(@RequestBody HiringContactComment hiringContactComment) throws URISyntaxException {
        log.debug("REST request to update HiringContactComment : {}", hiringContactComment);
        if (hiringContactComment.getId() == null) {
            return createHiringContactComment(hiringContactComment);
        }
        HiringContactComment result = hiringContactCommentRepository.save(hiringContactComment);
        hiringContactCommentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hiringContactComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hiring-contact-comments : get all the hiringContactComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hiringContactComments in body
     */
    @GetMapping("/hiring-contact-comments")
    @Timed
    public List<HiringContactComment> getAllHiringContactComments() {
        log.debug("REST request to get all HiringContactComments");
        return hiringContactCommentRepository.findAll();
        }

    /**
     * GET  /hiring-contact-comments/:id : get the "id" hiringContactComment.
     *
     * @param id the id of the hiringContactComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hiringContactComment, or with status 404 (Not Found)
     */
    @GetMapping("/hiring-contact-comments/{id}")
    @Timed
    public ResponseEntity<HiringContactComment> getHiringContactComment(@PathVariable Long id) {
        log.debug("REST request to get HiringContactComment : {}", id);
        HiringContactComment hiringContactComment = hiringContactCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hiringContactComment));
    }

    /**
     * DELETE  /hiring-contact-comments/:id : delete the "id" hiringContactComment.
     *
     * @param id the id of the hiringContactComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hiring-contact-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteHiringContactComment(@PathVariable Long id) {
        log.debug("REST request to delete HiringContactComment : {}", id);
        hiringContactCommentRepository.delete(id);
        hiringContactCommentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hiring-contact-comments?query=:query : search for the hiringContactComment corresponding
     * to the query.
     *
     * @param query the query of the hiringContactComment search
     * @return the result of the search
     */
    @GetMapping("/_search/hiring-contact-comments")
    @Timed
    public List<HiringContactComment> searchHiringContactComments(@RequestParam String query) {
        log.debug("REST request to search HiringContactComments for query {}", query);
        return StreamSupport
            .stream(hiringContactCommentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
