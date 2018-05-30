package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.HotList;

import com.recruitsmart.repository.HotListRepository;
import com.recruitsmart.repository.search.HotListSearchRepository;
import com.recruitsmart.web.rest.errors.BadRequestAlertException;
import com.recruitsmart.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HotList.
 */
@RestController
@RequestMapping("/api")
public class HotListResource {

    private final Logger log = LoggerFactory.getLogger(HotListResource.class);

    private static final String ENTITY_NAME = "hotList";

    private final HotListRepository hotListRepository;

    private final HotListSearchRepository hotListSearchRepository;

    public HotListResource(HotListRepository hotListRepository, HotListSearchRepository hotListSearchRepository) {
        this.hotListRepository = hotListRepository;
        this.hotListSearchRepository = hotListSearchRepository;
    }

    /**
     * POST  /hot-lists : Create a new hotList.
     *
     * @param hotList the hotList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotList, or with status 400 (Bad Request) if the hotList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hot-lists")
    @Timed
    public ResponseEntity<HotList> createHotList(@Valid @RequestBody HotList hotList) throws URISyntaxException {
        log.debug("REST request to save HotList : {}", hotList);
        if (hotList.getId() != null) {
            throw new BadRequestAlertException("A new hotList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotList result = hotListRepository.save(hotList);
        hotListSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hot-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hot-lists : Updates an existing hotList.
     *
     * @param hotList the hotList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotList,
     * or with status 400 (Bad Request) if the hotList is not valid,
     * or with status 500 (Internal Server Error) if the hotList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hot-lists")
    @Timed
    public ResponseEntity<HotList> updateHotList(@Valid @RequestBody HotList hotList) throws URISyntaxException {
        log.debug("REST request to update HotList : {}", hotList);
        if (hotList.getId() == null) {
            return createHotList(hotList);
        }
        HotList result = hotListRepository.save(hotList);
        hotListSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hot-lists : get all the hotLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hotLists in body
     */
    @GetMapping("/hot-lists")
    @Timed
    public List<HotList> getAllHotLists() {
        log.debug("REST request to get all HotLists");
        return hotListRepository.findAll();
        }

    /**
     * GET  /hot-lists/:id : get the "id" hotList.
     *
     * @param id the id of the hotList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotList, or with status 404 (Not Found)
     */
    @GetMapping("/hot-lists/{id}")
    @Timed
    public ResponseEntity<HotList> getHotList(@PathVariable Long id) {
        log.debug("REST request to get HotList : {}", id);
        HotList hotList = hotListRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotList));
    }

    /**
     * DELETE  /hot-lists/:id : delete the "id" hotList.
     *
     * @param id the id of the hotList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hot-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotList(@PathVariable Long id) {
        log.debug("REST request to delete HotList : {}", id);
        hotListRepository.delete(id);
        hotListSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hot-lists?query=:query : search for the hotList corresponding
     * to the query.
     *
     * @param query the query of the hotList search
     * @return the result of the search
     */
    @GetMapping("/_search/hot-lists")
    @Timed
    public List<HotList> searchHotLists(@RequestParam String query) {
        log.debug("REST request to search HotLists for query {}", query);
        return StreamSupport
            .stream(hotListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
