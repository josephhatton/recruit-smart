package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.ActivityAction;
import com.recruitsmart.repository.ActivityActionRepository;
import com.recruitsmart.repository.search.ActivityActionSearchRepository;
import com.recruitsmart.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.recruitsmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivityActionResource REST controller.
 *
 * @see ActivityActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class ActivityActionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ActivityActionRepository activityActionRepository;

    @Autowired
    private ActivityActionSearchRepository activityActionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityActionMockMvc;

    private ActivityAction activityAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityActionResource activityActionResource = new ActivityActionResource(activityActionRepository, activityActionSearchRepository);
        this.restActivityActionMockMvc = MockMvcBuilders.standaloneSetup(activityActionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityAction createEntity(EntityManager em) {
        ActivityAction activityAction = new ActivityAction();
        activityAction.setName(DEFAULT_NAME);
        activityAction.setDescription(DEFAULT_DESCRIPTION);
        return activityAction;
    }

    @Before
    public void initTest() {
        activityActionSearchRepository.deleteAll();
        activityAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityAction() throws Exception {
        int databaseSizeBeforeCreate = activityActionRepository.findAll().size();

        // Create the ActivityAction
        restActivityActionMockMvc.perform(post("/api/activity-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAction)))
            .andExpect(status().isCreated());

        // Validate the ActivityAction in the database
        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityAction testActivityAction = activityActionList.get(activityActionList.size() - 1);
        assertThat(testActivityAction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivityAction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ActivityAction in Elasticsearch
        ActivityAction activityActionEs = activityActionSearchRepository.findOne(testActivityAction.getId());
        assertThat(activityActionEs).isEqualToIgnoringGivenFields(testActivityAction);
    }

    @Test
    @Transactional
    public void createActivityActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityActionRepository.findAll().size();

        // Create the ActivityAction with an existing ID
        activityAction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityActionMockMvc.perform(post("/api/activity-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAction)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityAction in the database
        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityActionRepository.findAll().size();
        // set the field null
        activityAction.setName(null);

        // Create the ActivityAction, which fails.

        restActivityActionMockMvc.perform(post("/api/activity-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAction)))
            .andExpect(status().isBadRequest());

        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActivityActions() throws Exception {
        // Initialize the database
        activityActionRepository.saveAndFlush(activityAction);

        // Get all the activityActionList
        restActivityActionMockMvc.perform(get("/api/activity-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getActivityAction() throws Exception {
        // Initialize the database
        activityActionRepository.saveAndFlush(activityAction);

        // Get the activityAction
        restActivityActionMockMvc.perform(get("/api/activity-actions/{id}", activityAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityAction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityAction() throws Exception {
        // Get the activityAction
        restActivityActionMockMvc.perform(get("/api/activity-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityAction() throws Exception {
        // Initialize the database
        activityActionRepository.saveAndFlush(activityAction);
        activityActionSearchRepository.save(activityAction);
        int databaseSizeBeforeUpdate = activityActionRepository.findAll().size();

        // Update the activityAction
        ActivityAction updatedActivityAction = activityActionRepository.findOne(activityAction.getId());
        // Disconnect from session so that the updates on updatedActivityAction are not directly saved in db
        em.detach(updatedActivityAction);
        updatedActivityAction.setName(UPDATED_NAME);
        updatedActivityAction.setDescription(UPDATED_DESCRIPTION);

        restActivityActionMockMvc.perform(put("/api/activity-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivityAction)))
            .andExpect(status().isOk());

        // Validate the ActivityAction in the database
        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeUpdate);
        ActivityAction testActivityAction = activityActionList.get(activityActionList.size() - 1);
        assertThat(testActivityAction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivityAction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ActivityAction in Elasticsearch
        ActivityAction activityActionEs = activityActionSearchRepository.findOne(testActivityAction.getId());
        assertThat(activityActionEs).isEqualToIgnoringGivenFields(testActivityAction);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityAction() throws Exception {
        int databaseSizeBeforeUpdate = activityActionRepository.findAll().size();

        // Create the ActivityAction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityActionMockMvc.perform(put("/api/activity-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAction)))
            .andExpect(status().isCreated());

        // Validate the ActivityAction in the database
        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivityAction() throws Exception {
        // Initialize the database
        activityActionRepository.saveAndFlush(activityAction);
        activityActionSearchRepository.save(activityAction);
        int databaseSizeBeforeDelete = activityActionRepository.findAll().size();

        // Get the activityAction
        restActivityActionMockMvc.perform(delete("/api/activity-actions/{id}", activityAction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean activityActionExistsInEs = activityActionSearchRepository.exists(activityAction.getId());
        assertThat(activityActionExistsInEs).isFalse();

        // Validate the database is empty
        List<ActivityAction> activityActionList = activityActionRepository.findAll();
        assertThat(activityActionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchActivityAction() throws Exception {
        // Initialize the database
        activityActionRepository.saveAndFlush(activityAction);
        activityActionSearchRepository.save(activityAction);

        // Search the activityAction
        restActivityActionMockMvc.perform(get("/api/_search/activity-actions?query=id:" + activityAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityAction.class);
        ActivityAction activityAction1 = new ActivityAction();
        activityAction1.setId(1L);
        ActivityAction activityAction2 = new ActivityAction();
        activityAction2.setId(activityAction1.getId());
        assertThat(activityAction1).isEqualTo(activityAction2);
        activityAction2.setId(2L);
        assertThat(activityAction1).isNotEqualTo(activityAction2);
        activityAction1.setId(null);
        assertThat(activityAction1).isNotEqualTo(activityAction2);
    }
}
