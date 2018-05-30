package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.HiringContact;

import com.recruitsmart.repository.HiringContactRepository;
import com.recruitsmart.repository.search.HiringContactSearchRepository;
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
 * REST controller for managing HiringContact.
 */
@RestController
@RequestMapping("/api")
public class HiringContactResource {

    private final Logger log = LoggerFactory.getLogger(HiringContactResource.class);

    private static final String ENTITY_NAME = "hiringContact";

    private final HiringContactRepository hiringContactRepository;

    private final HiringContactSearchRepository hiringContactSearchRepository;

    public HiringContactResource(HiringContactRepository hiringContactRepository, HiringContactSearchRepository hiringContactSearchRepository) {
        this.hiringContactRepository = hiringContactRepository;
        this.hiringContactSearchRepository = hiringContactSearchRepository;
    }

    /**
     * POST  /hiring-contacts : Create a new hiringContact.
     *
     * @param hiringContact the hiringContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hiringContact, or with status 400 (Bad Request) if the hiringContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hiring-contacts")
    @Timed
    public ResponseEntity<HiringContact> createHiringContact(@RequestBody HiringContact hiringContact) throws URISyntaxException {
        log.debug("REST request to save HiringContact : {}", hiringContact);
        if (hiringContact.getId() != null) {
            throw new BadRequestAlertException("A new hiringContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HiringContact result = hiringContactRepository.save(hiringContact);
        hiringContactSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hiring-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hiring-contacts : Updates an existing hiringContact.
     *
     * @param hiringContact the hiringContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hiringContact,
     * or with status 400 (Bad Request) if the hiringContact is not valid,
     * or with status 500 (Internal Server Error) if the hiringContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hiring-contacts")
    @Timed
    public ResponseEntity<HiringContact> updateHiringContact(@RequestBody HiringContact hiringContact) throws URISyntaxException {
        log.debug("REST request to update HiringContact : {}", hiringContact);
        if (hiringContact.getId() == null) {
            return createHiringContact(hiringContact);
        }
        HiringContact result = hiringContactRepository.save(hiringContact);
        hiringContactSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hiringContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hiring-contacts : get all the hiringContacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hiringContacts in body
     */
    @GetMapping("/hiring-contacts")
    @Timed
    public ResponseEntity<List<HiringContact>> getAllHiringContacts(Pageable pageable) {
        log.debug("REST request to get a page of HiringContacts");
        Page<HiringContact> page = hiringContactRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hiring-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hiring-contacts/:id : get the "id" hiringContact.
     *
     * @param id the id of the hiringContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hiringContact, or with status 404 (Not Found)
     */
    @GetMapping("/hiring-contacts/{id}")
    @Timed
    public ResponseEntity<HiringContact> getHiringContact(@PathVariable Long id) {
        log.debug("REST request to get HiringContact : {}", id);
        HiringContact hiringContact = hiringContactRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hiringContact));
    }

    /**
     * DELETE  /hiring-contacts/:id : delete the "id" hiringContact.
     *
     * @param id the id of the hiringContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hiring-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteHiringContact(@PathVariable Long id) {
        log.debug("REST request to delete HiringContact : {}", id);
        hiringContactRepository.delete(id);
        hiringContactSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hiring-contacts?query=:query : search for the hiringContact corresponding
     * to the query.
     *
     * @param query the query of the hiringContact search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hiring-contacts")
    @Timed
    public ResponseEntity<List<HiringContact>> searchHiringContacts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HiringContacts for query {}", query);
        Page<HiringContact> page = hiringContactSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hiring-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
