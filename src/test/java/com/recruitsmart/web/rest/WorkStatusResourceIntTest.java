package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.WorkStatus;
import com.recruitsmart.repository.WorkStatusRepository;
import com.recruitsmart.repository.search.WorkStatusSearchRepository;
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
 * Test class for the WorkStatusResource REST controller.
 *
 * @see WorkStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class WorkStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkStatusRepository workStatusRepository;

    @Autowired
    private WorkStatusSearchRepository workStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkStatusMockMvc;

    private WorkStatus workStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkStatusResource workStatusResource = new WorkStatusResource(workStatusRepository, workStatusSearchRepository);
        this.restWorkStatusMockMvc = MockMvcBuilders.standaloneSetup(workStatusResource)
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
    public static WorkStatus createEntity(EntityManager em) {
        WorkStatus workStatus = new WorkStatus();
        workStatus.setName(DEFAULT_NAME);
        workStatus.setDescription(DEFAULT_DESCRIPTION);
        return workStatus;
    }

    @Before
    public void initTest() {
        workStatusSearchRepository.deleteAll();
        workStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkStatus() throws Exception {
        int databaseSizeBeforeCreate = workStatusRepository.findAll().size();

        // Create the WorkStatus
        restWorkStatusMockMvc.perform(post("/api/work-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workStatus)))
            .andExpect(status().isCreated());

        // Validate the WorkStatus in the database
        List<WorkStatus> workStatusList = workStatusRepository.findAll();
        assertThat(workStatusList).hasSize(databaseSizeBeforeCreate + 1);
        WorkStatus testWorkStatus = workStatusList.get(workStatusList.size() - 1);
        assertThat(testWorkStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the WorkStatus in Elasticsearch
        WorkStatus workStatusEs = workStatusSearchRepository.findOne(testWorkStatus.getId());
        assertThat(workStatusEs).isEqualToIgnoringGivenFields(testWorkStatus);
    }

    @Test
    @Transactional
    public void createWorkStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workStatusRepository.findAll().size();

        // Create the WorkStatus with an existing ID
        workStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkStatusMockMvc.perform(post("/api/work-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workStatus)))
            .andExpect(status().isBadRequest());

        // Validate the WorkStatus in the database
        List<WorkStatus> workStatusList = workStatusRepository.findAll();
        assertThat(workStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkStatuses() throws Exception {
        // Initialize the database
        workStatusRepository.saveAndFlush(workStatus);

        // Get all the workStatusList
        restWorkStatusMockMvc.perform(get("/api/work-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getWorkStatus() throws Exception {
        // Initialize the database
        workStatusRepository.saveAndFlush(workStatus);

        // Get the workStatus
        restWorkStatusMockMvc.perform(get("/api/work-statuses/{id}", workStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkStatus() throws Exception {
        // Get the workStatus
        restWorkStatusMockMvc.perform(get("/api/work-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkStatus() throws Exception {
        // Initialize the database
        workStatusRepository.saveAndFlush(workStatus);
        workStatusSearchRepository.save(workStatus);
        int databaseSizeBeforeUpdate = workStatusRepository.findAll().size();

        // Update the workStatus
        WorkStatus updatedWorkStatus = workStatusRepository.findOne(workStatus.getId());
        // Disconnect from session so that the updates on updatedWorkStatus are not directly saved in db
        em.detach(updatedWorkStatus);
        updatedWorkStatus.setName(UPDATED_NAME);
        updatedWorkStatus.setDescription(UPDATED_DESCRIPTION);

        restWorkStatusMockMvc.perform(put("/api/work-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkStatus)))
            .andExpect(status().isOk());

        // Validate the WorkStatus in the database
        List<WorkStatus> workStatusList = workStatusRepository.findAll();
        assertThat(workStatusList).hasSize(databaseSizeBeforeUpdate);
        WorkStatus testWorkStatus = workStatusList.get(workStatusList.size() - 1);
        assertThat(testWorkStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the WorkStatus in Elasticsearch
        WorkStatus workStatusEs = workStatusSearchRepository.findOne(testWorkStatus.getId());
        assertThat(workStatusEs).isEqualToIgnoringGivenFields(testWorkStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkStatus() throws Exception {
        int databaseSizeBeforeUpdate = workStatusRepository.findAll().size();

        // Create the WorkStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkStatusMockMvc.perform(put("/api/work-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workStatus)))
            .andExpect(status().isCreated());

        // Validate the WorkStatus in the database
        List<WorkStatus> workStatusList = workStatusRepository.findAll();
        assertThat(workStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkStatus() throws Exception {
        // Initialize the database
        workStatusRepository.saveAndFlush(workStatus);
        workStatusSearchRepository.save(workStatus);
        int databaseSizeBeforeDelete = workStatusRepository.findAll().size();

        // Get the workStatus
        restWorkStatusMockMvc.perform(delete("/api/work-statuses/{id}", workStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean workStatusExistsInEs = workStatusSearchRepository.exists(workStatus.getId());
        assertThat(workStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<WorkStatus> workStatusList = workStatusRepository.findAll();
        assertThat(workStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorkStatus() throws Exception {
        // Initialize the database
        workStatusRepository.saveAndFlush(workStatus);
        workStatusSearchRepository.save(workStatus);

        // Search the workStatus
        restWorkStatusMockMvc.perform(get("/api/_search/work-statuses?query=id:" + workStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkStatus.class);
        WorkStatus workStatus1 = new WorkStatus();
        workStatus1.setId(1L);
        WorkStatus workStatus2 = new WorkStatus();
        workStatus2.setId(workStatus1.getId());
        assertThat(workStatus1).isEqualTo(workStatus2);
        workStatus2.setId(2L);
        assertThat(workStatus1).isNotEqualTo(workStatus2);
        workStatus1.setId(null);
        assertThat(workStatus1).isNotEqualTo(workStatus2);
    }
}
