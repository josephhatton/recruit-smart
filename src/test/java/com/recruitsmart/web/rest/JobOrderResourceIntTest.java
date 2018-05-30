package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.JobOrder;
import com.recruitsmart.repository.JobOrderRepository;
import com.recruitsmart.repository.search.JobOrderSearchRepository;
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

import com.recruitsmart.domain.enumeration.JobType;
import com.recruitsmart.domain.enumeration.JobStatus;
/**
 * Test class for the JobOrderResource REST controller.
 *
 * @see JobOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class JobOrderResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_HOURLY = "AAAAAAAAAA";
    private static final String UPDATED_HOURLY = "BBBBBBBBBB";

    private static final JobType DEFAULT_JOB_TYPE = JobType.DIRECT_HIRE;
    private static final JobType UPDATED_JOB_TYPE = JobType.CONTRACT_TO_HIRE;

    private static final JobStatus DEFAULT_JOB_STATUS = JobStatus.OPEN;
    private static final JobStatus UPDATED_JOB_STATUS = JobStatus.FILLED_BY_US;

    @Autowired
    private JobOrderRepository jobOrderRepository;

    @Autowired
    private JobOrderSearchRepository jobOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobOrderMockMvc;

    private JobOrder jobOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobOrderResource jobOrderResource = new JobOrderResource(jobOrderRepository, jobOrderSearchRepository);
        this.restJobOrderMockMvc = MockMvcBuilders.standaloneSetup(jobOrderResource)
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
    public static JobOrder createEntity(EntityManager em) {
        JobOrder jobOrder = new JobOrder();
        jobOrder.setTitle(DEFAULT_TITLE);
        jobOrder.setDuration(DEFAULT_DURATION);
        jobOrder.setDescription(DEFAULT_DESCRIPTION);
        jobOrder.setSalary(DEFAULT_SALARY);
        jobOrder.setHourly(DEFAULT_HOURLY);
        jobOrder.setJobType(DEFAULT_JOB_TYPE);
        jobOrder.setJobStatus(DEFAULT_JOB_STATUS);
        return jobOrder;
    }

    @Before
    public void initTest() {
        jobOrderSearchRepository.deleteAll();
        jobOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobOrder() throws Exception {
        int databaseSizeBeforeCreate = jobOrderRepository.findAll().size();

        // Create the JobOrder
        restJobOrderMockMvc.perform(post("/api/job-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrder)))
            .andExpect(status().isCreated());

        // Validate the JobOrder in the database
        List<JobOrder> jobOrderList = jobOrderRepository.findAll();
        assertThat(jobOrderList).hasSize(databaseSizeBeforeCreate + 1);
        JobOrder testJobOrder = jobOrderList.get(jobOrderList.size() - 1);
        assertThat(testJobOrder.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobOrder.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJobOrder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobOrder.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testJobOrder.getHourly()).isEqualTo(DEFAULT_HOURLY);
        assertThat(testJobOrder.getJobType()).isEqualTo(DEFAULT_JOB_TYPE);
        assertThat(testJobOrder.getJobStatus()).isEqualTo(DEFAULT_JOB_STATUS);

        // Validate the JobOrder in Elasticsearch
        JobOrder jobOrderEs = jobOrderSearchRepository.findOne(testJobOrder.getId());
        assertThat(jobOrderEs).isEqualToIgnoringGivenFields(testJobOrder);
    }

    @Test
    @Transactional
    public void createJobOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOrderRepository.findAll().size();

        // Create the JobOrder with an existing ID
        jobOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOrderMockMvc.perform(post("/api/job-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrder)))
            .andExpect(status().isBadRequest());

        // Validate the JobOrder in the database
        List<JobOrder> jobOrderList = jobOrderRepository.findAll();
        assertThat(jobOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobOrders() throws Exception {
        // Initialize the database
        jobOrderRepository.saveAndFlush(jobOrder);

        // Get all the jobOrderList
        restJobOrderMockMvc.perform(get("/api/job-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.toString())))
            .andExpect(jsonPath("$.[*].hourly").value(hasItem(DEFAULT_HOURLY.toString())))
            .andExpect(jsonPath("$.[*].jobType").value(hasItem(DEFAULT_JOB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getJobOrder() throws Exception {
        // Initialize the database
        jobOrderRepository.saveAndFlush(jobOrder);

        // Get the jobOrder
        restJobOrderMockMvc.perform(get("/api/job-orders/{id}", jobOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobOrder.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.toString()))
            .andExpect(jsonPath("$.hourly").value(DEFAULT_HOURLY.toString()))
            .andExpect(jsonPath("$.jobType").value(DEFAULT_JOB_TYPE.toString()))
            .andExpect(jsonPath("$.jobStatus").value(DEFAULT_JOB_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobOrder() throws Exception {
        // Get the jobOrder
        restJobOrderMockMvc.perform(get("/api/job-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOrder() throws Exception {
        // Initialize the database
        jobOrderRepository.saveAndFlush(jobOrder);
        jobOrderSearchRepository.save(jobOrder);
        int databaseSizeBeforeUpdate = jobOrderRepository.findAll().size();

        // Update the jobOrder
        JobOrder updatedJobOrder = jobOrderRepository.findOne(jobOrder.getId());
        // Disconnect from session so that the updates on updatedJobOrder are not directly saved in db
        em.detach(updatedJobOrder);
        updatedJobOrder.setTitle(UPDATED_TITLE);
        updatedJobOrder.setDuration(UPDATED_DURATION);
        updatedJobOrder.setDescription(UPDATED_DESCRIPTION);
        updatedJobOrder.setSalary(UPDATED_SALARY);
        updatedJobOrder.setHourly(UPDATED_HOURLY);
        updatedJobOrder.setJobType(UPDATED_JOB_TYPE);
        updatedJobOrder.setJobStatus(UPDATED_JOB_STATUS);

        restJobOrderMockMvc.perform(put("/api/job-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobOrder)))
            .andExpect(status().isOk());

        // Validate the JobOrder in the database
        List<JobOrder> jobOrderList = jobOrderRepository.findAll();
        assertThat(jobOrderList).hasSize(databaseSizeBeforeUpdate);
        JobOrder testJobOrder = jobOrderList.get(jobOrderList.size() - 1);
        assertThat(testJobOrder.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobOrder.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJobOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobOrder.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testJobOrder.getHourly()).isEqualTo(UPDATED_HOURLY);
        assertThat(testJobOrder.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testJobOrder.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);

        // Validate the JobOrder in Elasticsearch
        JobOrder jobOrderEs = jobOrderSearchRepository.findOne(testJobOrder.getId());
        assertThat(jobOrderEs).isEqualToIgnoringGivenFields(testJobOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingJobOrder() throws Exception {
        int databaseSizeBeforeUpdate = jobOrderRepository.findAll().size();

        // Create the JobOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobOrderMockMvc.perform(put("/api/job-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrder)))
            .andExpect(status().isCreated());

        // Validate the JobOrder in the database
        List<JobOrder> jobOrderList = jobOrderRepository.findAll();
        assertThat(jobOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobOrder() throws Exception {
        // Initialize the database
        jobOrderRepository.saveAndFlush(jobOrder);
        jobOrderSearchRepository.save(jobOrder);
        int databaseSizeBeforeDelete = jobOrderRepository.findAll().size();

        // Get the jobOrder
        restJobOrderMockMvc.perform(delete("/api/job-orders/{id}", jobOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobOrderExistsInEs = jobOrderSearchRepository.exists(jobOrder.getId());
        assertThat(jobOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<JobOrder> jobOrderList = jobOrderRepository.findAll();
        assertThat(jobOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobOrder() throws Exception {
        // Initialize the database
        jobOrderRepository.saveAndFlush(jobOrder);
        jobOrderSearchRepository.save(jobOrder);

        // Search the jobOrder
        restJobOrderMockMvc.perform(get("/api/_search/job-orders?query=id:" + jobOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.toString())))
            .andExpect(jsonPath("$.[*].hourly").value(hasItem(DEFAULT_HOURLY.toString())))
            .andExpect(jsonPath("$.[*].jobType").value(hasItem(DEFAULT_JOB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOrder.class);
        JobOrder jobOrder1 = new JobOrder();
        jobOrder1.setId(1L);
        JobOrder jobOrder2 = new JobOrder();
        jobOrder2.setId(jobOrder1.getId());
        assertThat(jobOrder1).isEqualTo(jobOrder2);
        jobOrder2.setId(2L);
        assertThat(jobOrder1).isNotEqualTo(jobOrder2);
        jobOrder1.setId(null);
        assertThat(jobOrder1).isNotEqualTo(jobOrder2);
    }
}
