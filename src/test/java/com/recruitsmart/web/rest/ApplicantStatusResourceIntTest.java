package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.ApplicantStatus;
import com.recruitsmart.repository.ApplicantStatusRepository;
import com.recruitsmart.repository.search.ApplicantStatusSearchRepository;
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
 * Test class for the ApplicantStatusResource REST controller.
 *
 * @see ApplicantStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class ApplicantStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApplicantStatusRepository applicantStatusRepository;

    @Autowired
    private ApplicantStatusSearchRepository applicantStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicantStatusMockMvc;

    private ApplicantStatus applicantStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicantStatusResource applicantStatusResource = new ApplicantStatusResource(applicantStatusRepository, applicantStatusSearchRepository);
        this.restApplicantStatusMockMvc = MockMvcBuilders.standaloneSetup(applicantStatusResource)
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
    public static ApplicantStatus createEntity(EntityManager em) {
        ApplicantStatus applicantStatus = new ApplicantStatus();
        applicantStatus.setName(DEFAULT_NAME);
        applicantStatus.setDescription(DEFAULT_DESCRIPTION);
        return applicantStatus;
    }

    @Before
    public void initTest() {
        applicantStatusSearchRepository.deleteAll();
        applicantStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantStatus() throws Exception {
        int databaseSizeBeforeCreate = applicantStatusRepository.findAll().size();

        // Create the ApplicantStatus
        restApplicantStatusMockMvc.perform(post("/api/applicant-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantStatus)))
            .andExpect(status().isCreated());

        // Validate the ApplicantStatus in the database
        List<ApplicantStatus> applicantStatusList = applicantStatusRepository.findAll();
        assertThat(applicantStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantStatus testApplicantStatus = applicantStatusList.get(applicantStatusList.size() - 1);
        assertThat(testApplicantStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicantStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ApplicantStatus in Elasticsearch
        ApplicantStatus applicantStatusEs = applicantStatusSearchRepository.findOne(testApplicantStatus.getId());
        assertThat(applicantStatusEs).isEqualToIgnoringGivenFields(testApplicantStatus);
    }

    @Test
    @Transactional
    public void createApplicantStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantStatusRepository.findAll().size();

        // Create the ApplicantStatus with an existing ID
        applicantStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantStatusMockMvc.perform(post("/api/applicant-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantStatus in the database
        List<ApplicantStatus> applicantStatusList = applicantStatusRepository.findAll();
        assertThat(applicantStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicantStatuses() throws Exception {
        // Initialize the database
        applicantStatusRepository.saveAndFlush(applicantStatus);

        // Get all the applicantStatusList
        restApplicantStatusMockMvc.perform(get("/api/applicant-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getApplicantStatus() throws Exception {
        // Initialize the database
        applicantStatusRepository.saveAndFlush(applicantStatus);

        // Get the applicantStatus
        restApplicantStatusMockMvc.perform(get("/api/applicant-statuses/{id}", applicantStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicantStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantStatus() throws Exception {
        // Get the applicantStatus
        restApplicantStatusMockMvc.perform(get("/api/applicant-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantStatus() throws Exception {
        // Initialize the database
        applicantStatusRepository.saveAndFlush(applicantStatus);
        applicantStatusSearchRepository.save(applicantStatus);
        int databaseSizeBeforeUpdate = applicantStatusRepository.findAll().size();

        // Update the applicantStatus
        ApplicantStatus updatedApplicantStatus = applicantStatusRepository.findOne(applicantStatus.getId());
        // Disconnect from session so that the updates on updatedApplicantStatus are not directly saved in db
        em.detach(updatedApplicantStatus);
        updatedApplicantStatus.setName(UPDATED_NAME);
        updatedApplicantStatus.setDescription(UPDATED_DESCRIPTION);

        restApplicantStatusMockMvc.perform(put("/api/applicant-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantStatus)))
            .andExpect(status().isOk());

        // Validate the ApplicantStatus in the database
        List<ApplicantStatus> applicantStatusList = applicantStatusRepository.findAll();
        assertThat(applicantStatusList).hasSize(databaseSizeBeforeUpdate);
        ApplicantStatus testApplicantStatus = applicantStatusList.get(applicantStatusList.size() - 1);
        assertThat(testApplicantStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicantStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ApplicantStatus in Elasticsearch
        ApplicantStatus applicantStatusEs = applicantStatusSearchRepository.findOne(testApplicantStatus.getId());
        assertThat(applicantStatusEs).isEqualToIgnoringGivenFields(testApplicantStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantStatus() throws Exception {
        int databaseSizeBeforeUpdate = applicantStatusRepository.findAll().size();

        // Create the ApplicantStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicantStatusMockMvc.perform(put("/api/applicant-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantStatus)))
            .andExpect(status().isCreated());

        // Validate the ApplicantStatus in the database
        List<ApplicantStatus> applicantStatusList = applicantStatusRepository.findAll();
        assertThat(applicantStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicantStatus() throws Exception {
        // Initialize the database
        applicantStatusRepository.saveAndFlush(applicantStatus);
        applicantStatusSearchRepository.save(applicantStatus);
        int databaseSizeBeforeDelete = applicantStatusRepository.findAll().size();

        // Get the applicantStatus
        restApplicantStatusMockMvc.perform(delete("/api/applicant-statuses/{id}", applicantStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean applicantStatusExistsInEs = applicantStatusSearchRepository.exists(applicantStatus.getId());
        assertThat(applicantStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<ApplicantStatus> applicantStatusList = applicantStatusRepository.findAll();
        assertThat(applicantStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApplicantStatus() throws Exception {
        // Initialize the database
        applicantStatusRepository.saveAndFlush(applicantStatus);
        applicantStatusSearchRepository.save(applicantStatus);

        // Search the applicantStatus
        restApplicantStatusMockMvc.perform(get("/api/_search/applicant-statuses?query=id:" + applicantStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantStatus.class);
        ApplicantStatus applicantStatus1 = new ApplicantStatus();
        applicantStatus1.setId(1L);
        ApplicantStatus applicantStatus2 = new ApplicantStatus();
        applicantStatus2.setId(applicantStatus1.getId());
        assertThat(applicantStatus1).isEqualTo(applicantStatus2);
        applicantStatus2.setId(2L);
        assertThat(applicantStatus1).isNotEqualTo(applicantStatus2);
        applicantStatus1.setId(null);
        assertThat(applicantStatus1).isNotEqualTo(applicantStatus2);
    }
}
