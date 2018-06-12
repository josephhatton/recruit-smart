package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.HotListBucket;

import com.recruitsmart.repository.HotListBucketRepository;
import com.recruitsmart.repository.search.HotListBucketSearchRepository;
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
 * REST controller for managing HotListBucket.
 */
@RestController
@RequestMapping("/api")
public class HotListBucketResource {

    private final Logger log = LoggerFactory.getLogger(HotListBucketResource.class);

    private static final String ENTITY_NAME = "hotListBucket";

    private final HotListBucketRepository hotListBucketRepository;

    private final HotListBucketSearchRepository hotListBucketSearchRepository;

    public HotListBucketResource(HotListBucketRepository hotListBucketRepository, HotListBucketSearchRepository hotListBucketSearchRepository) {
        this.hotListBucketRepository = hotListBucketRepository;
        this.hotListBucketSearchRepository = hotListBucketSearchRepository;
    }

    /**
     * POST  /hot-list-buckets : Create a new hotListBucket.
     *
     * @param hotListBucket the hotListBucket to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotListBucket, or with status 400 (Bad Request) if the hotListBucket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hot-list-buckets")
    @Timed
    public ResponseEntity<HotListBucket> createHotListBucket(@RequestBody HotListBucket hotListBucket) throws URISyntaxException {
        log.debug("REST request to save HotListBucket : {}", hotListBucket);
        if (hotListBucket.getId() != null) {
            throw new BadRequestAlertException("A new hotListBucket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotListBucket result = hotListBucketRepository.save(hotListBucket);
        hotListBucketSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hot-list-buckets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hot-list-buckets : Updates an existing hotListBucket.
     *
     * @param hotListBucket the hotListBucket to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotListBucket,
     * or with status 400 (Bad Request) if the hotListBucket is not valid,
     * or with status 500 (Internal Server Error) if the hotListBucket couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hot-list-buckets")
    @Timed
    public ResponseEntity<HotListBucket> updateHotListBucket(@RequestBody HotListBucket hotListBucket) throws URISyntaxException {
        log.debug("REST request to update HotListBucket : {}", hotListBucket);
        if (hotListBucket.getId() == null) {
            return createHotListBucket(hotListBucket);
        }
        HotListBucket result = hotListBucketRepository.save(hotListBucket);
        hotListBucketSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotListBucket.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hot-list-buckets : get all the hotListBuckets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hotListBuckets in body
     */
    @GetMapping("/hot-list-buckets")
    @Timed
    public List<HotListBucket> getAllHotListBuckets() {
        log.debug("REST request to get all HotListBuckets");
        return hotListBucketRepository.findAll();
        }

    /**
     * GET  /hot-list-buckets/:id : get the "id" hotListBucket.
     *
     * @param id the id of the hotListBucket to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotListBucket, or with status 404 (Not Found)
     */
    @GetMapping("/hot-list-buckets/{id}")
    @Timed
    public ResponseEntity<HotListBucket> getHotListBucket(@PathVariable Long id) {
        log.debug("REST request to get HotListBucket : {}", id);
        HotListBucket hotListBucket = hotListBucketRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotListBucket));
    }

    /**
     * DELETE  /hot-list-buckets/:id : delete the "id" hotListBucket.
     *
     * @param id the id of the hotListBucket to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hot-list-buckets/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotListBucket(@PathVariable Long id) {
        log.debug("REST request to delete HotListBucket : {}", id);
        hotListBucketRepository.delete(id);
        hotListBucketSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hot-list-buckets?query=:query : search for the hotListBucket corresponding
     * to the query.
     *
     * @param query the query of the hotListBucket search
     * @return the result of the search
     */
    @GetMapping("/_search/hot-list-buckets")
    @Timed
    public List<HotListBucket> searchHotListBuckets(@RequestParam String query) {
        log.debug("REST request to search HotListBuckets for query {}", query);
        return StreamSupport
            .stream(hotListBucketSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
