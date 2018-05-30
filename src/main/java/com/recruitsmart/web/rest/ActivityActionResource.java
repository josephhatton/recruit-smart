package com.recruitsmart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.recruitsmart.domain.ActivityAction;

import com.recruitsmart.repository.ActivityActionRepository;
import com.recruitsmart.repository.search.ActivityActionSearchRepository;
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
 * REST controller for managing ActivityAction.
 */
@RestController
@RequestMapping("/api")
public class ActivityActionResource {

    private final Logger log = LoggerFactory.getLogger(ActivityActionResource.class);

    private static final String ENTITY_NAME = "activityAction";

    private final ActivityActionRepository activityActionRepository;

    private final ActivityActionSearchRepository activityActionSearchRepository;

    public ActivityActionResource(ActivityActionRepository activityActionRepository, ActivityActionSearchRepository activityActionSearchRepository) {
        this.activityActionRepository = activityActionRepository;
        this.activityActionSearchRepository = activityActionSearchRepository;
    }

    /**
     * POST  /activity-actions : Create a new activityAction.
     *
     * @param activityAction the activityAction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityAction, or with status 400 (Bad Request) if the activityAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activity-actions")
    @Timed
    public ResponseEntity<ActivityAction> createActivityAction(@Valid @RequestBody ActivityAction activityAction) throws URISyntaxException {
        log.debug("REST request to save ActivityAction : {}", activityAction);
        if (activityAction.getId() != null) {
            throw new BadRequestAlertException("A new activityAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityAction result = activityActionRepository.save(activityAction);
        activityActionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/activity-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activity-actions : Updates an existing activityAction.
     *
     * @param activityAction the activityAction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityAction,
     * or with status 400 (Bad Request) if the activityAction is not valid,
     * or with status 500 (Internal Server Error) if the activityAction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activity-actions")
    @Timed
    public ResponseEntity<ActivityAction> updateActivityAction(@Valid @RequestBody ActivityAction activityAction) throws URISyntaxException {
        log.debug("REST request to update ActivityAction : {}", activityAction);
        if (activityAction.getId() == null) {
            return createActivityAction(activityAction);
        }
        ActivityAction result = activityActionRepository.save(activityAction);
        activityActionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityAction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activity-actions : get all the activityActions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activityActions in body
     */
    @GetMapping("/activity-actions")
    @Timed
    public List<ActivityAction> getAllActivityActions() {
        log.debug("REST request to get all ActivityActions");
        return activityActionRepository.findAll();
        }

    /**
     * GET  /activity-actions/:id : get the "id" activityAction.
     *
     * @param id the id of the activityAction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityAction, or with status 404 (Not Found)
     */
    @GetMapping("/activity-actions/{id}")
    @Timed
    public ResponseEntity<ActivityAction> getActivityAction(@PathVariable Long id) {
        log.debug("REST request to get ActivityAction : {}", id);
        ActivityAction activityAction = activityActionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activityAction));
    }

    /**
     * DELETE  /activity-actions/:id : delete the "id" activityAction.
     *
     * @param id the id of the activityAction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activity-actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivityAction(@PathVariable Long id) {
        log.debug("REST request to delete ActivityAction : {}", id);
        activityActionRepository.delete(id);
        activityActionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/activity-actions?query=:query : search for the activityAction corresponding
     * to the query.
     *
     * @param query the query of the activityAction search
     * @return the result of the search
     */
    @GetMapping("/_search/activity-actions")
    @Timed
    public List<ActivityAction> searchActivityActions(@RequestParam String query) {
        log.debug("REST request to search ActivityActions for query {}", query);
        return StreamSupport
            .stream(activityActionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
