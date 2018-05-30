package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.JobOrderInternalComment;
import com.recruitsmart.repository.JobOrderInternalCommentRepository;
import com.recruitsmart.repository.search.JobOrderInternalCommentSearchRepository;
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
 * Test class for the JobOrderInternalCommentResource REST controller.
 *
 * @see JobOrderInternalCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class JobOrderInternalCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JobOrderInternalCommentRepository jobOrderInternalCommentRepository;

    @Autowired
    private JobOrderInternalCommentSearchRepository jobOrderInternalCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobOrderInternalCommentMockMvc;

    private JobOrderInternalComment jobOrderInternalComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobOrderInternalCommentResource jobOrderInternalCommentResource = new JobOrderInternalCommentResource(jobOrderInternalCommentRepository, jobOrderInternalCommentSearchRepository);
        this.restJobOrderInternalCommentMockMvc = MockMvcBuilders.standaloneSetup(jobOrderInternalCommentResource)
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
    public static JobOrderInternalComment createEntity(EntityManager em) {
        JobOrderInternalComment jobOrderInternalComment = new JobOrderInternalComment();
        jobOrderInternalComment.setName(DEFAULT_NAME);
        jobOrderInternalComment.setDescription(DEFAULT_DESCRIPTION);
        return jobOrderInternalComment;
    }

    @Before
    public void initTest() {
        jobOrderInternalCommentSearchRepository.deleteAll();
        jobOrderInternalComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobOrderInternalComment() throws Exception {
        int databaseSizeBeforeCreate = jobOrderInternalCommentRepository.findAll().size();

        // Create the JobOrderInternalComment
        restJobOrderInternalCommentMockMvc.perform(post("/api/job-order-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderInternalComment)))
            .andExpect(status().isCreated());

        // Validate the JobOrderInternalComment in the database
        List<JobOrderInternalComment> jobOrderInternalCommentList = jobOrderInternalCommentRepository.findAll();
        assertThat(jobOrderInternalCommentList).hasSize(databaseSizeBeforeCreate + 1);
        JobOrderInternalComment testJobOrderInternalComment = jobOrderInternalCommentList.get(jobOrderInternalCommentList.size() - 1);
        assertThat(testJobOrderInternalComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobOrderInternalComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the JobOrderInternalComment in Elasticsearch
        JobOrderInternalComment jobOrderInternalCommentEs = jobOrderInternalCommentSearchRepository.findOne(testJobOrderInternalComment.getId());
        assertThat(jobOrderInternalCommentEs).isEqualToIgnoringGivenFields(testJobOrderInternalComment);
    }

    @Test
    @Transactional
    public void createJobOrderInternalCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOrderInternalCommentRepository.findAll().size();

        // Create the JobOrderInternalComment with an existing ID
        jobOrderInternalComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOrderInternalCommentMockMvc.perform(post("/api/job-order-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderInternalComment)))
            .andExpect(status().isBadRequest());

        // Validate the JobOrderInternalComment in the database
        List<JobOrderInternalComment> jobOrderInternalCommentList = jobOrderInternalCommentRepository.findAll();
        assertThat(jobOrderInternalCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobOrderInternalComments() throws Exception {
        // Initialize the database
        jobOrderInternalCommentRepository.saveAndFlush(jobOrderInternalComment);

        // Get all the jobOrderInternalCommentList
        restJobOrderInternalCommentMockMvc.perform(get("/api/job-order-internal-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrderInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getJobOrderInternalComment() throws Exception {
        // Initialize the database
        jobOrderInternalCommentRepository.saveAndFlush(jobOrderInternalComment);

        // Get the jobOrderInternalComment
        restJobOrderInternalCommentMockMvc.perform(get("/api/job-order-internal-comments/{id}", jobOrderInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobOrderInternalComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobOrderInternalComment() throws Exception {
        // Get the jobOrderInternalComment
        restJobOrderInternalCommentMockMvc.perform(get("/api/job-order-internal-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOrderInternalComment() throws Exception {
        // Initialize the database
        jobOrderInternalCommentRepository.saveAndFlush(jobOrderInternalComment);
        jobOrderInternalCommentSearchRepository.save(jobOrderInternalComment);
        int databaseSizeBeforeUpdate = jobOrderInternalCommentRepository.findAll().size();

        // Update the jobOrderInternalComment
        JobOrderInternalComment updatedJobOrderInternalComment = jobOrderInternalCommentRepository.findOne(jobOrderInternalComment.getId());
        // Disconnect from session so that the updates on updatedJobOrderInternalComment are not directly saved in db
        em.detach(updatedJobOrderInternalComment);
        updatedJobOrderInternalComment.setName(UPDATED_NAME);
        updatedJobOrderInternalComment.setDescription(UPDATED_DESCRIPTION);

        restJobOrderInternalCommentMockMvc.perform(put("/api/job-order-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobOrderInternalComment)))
            .andExpect(status().isOk());

        // Validate the JobOrderInternalComment in the database
        List<JobOrderInternalComment> jobOrderInternalCommentList = jobOrderInternalCommentRepository.findAll();
        assertThat(jobOrderInternalCommentList).hasSize(databaseSizeBeforeUpdate);
        JobOrderInternalComment testJobOrderInternalComment = jobOrderInternalCommentList.get(jobOrderInternalCommentList.size() - 1);
        assertThat(testJobOrderInternalComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobOrderInternalComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the JobOrderInternalComment in Elasticsearch
        JobOrderInternalComment jobOrderInternalCommentEs = jobOrderInternalCommentSearchRepository.findOne(testJobOrderInternalComment.getId());
        assertThat(jobOrderInternalCommentEs).isEqualToIgnoringGivenFields(testJobOrderInternalComment);
    }

    @Test
    @Transactional
    public void updateNonExistingJobOrderInternalComment() throws Exception {
        int databaseSizeBeforeUpdate = jobOrderInternalCommentRepository.findAll().size();

        // Create the JobOrderInternalComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobOrderInternalCommentMockMvc.perform(put("/api/job-order-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderInternalComment)))
            .andExpect(status().isCreated());

        // Validate the JobOrderInternalComment in the database
        List<JobOrderInternalComment> jobOrderInternalCommentList = jobOrderInternalCommentRepository.findAll();
        assertThat(jobOrderInternalCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobOrderInternalComment() throws Exception {
        // Initialize the database
        jobOrderInternalCommentRepository.saveAndFlush(jobOrderInternalComment);
        jobOrderInternalCommentSearchRepository.save(jobOrderInternalComment);
        int databaseSizeBeforeDelete = jobOrderInternalCommentRepository.findAll().size();

        // Get the jobOrderInternalComment
        restJobOrderInternalCommentMockMvc.perform(delete("/api/job-order-internal-comments/{id}", jobOrderInternalComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobOrderInternalCommentExistsInEs = jobOrderInternalCommentSearchRepository.exists(jobOrderInternalComment.getId());
        assertThat(jobOrderInternalCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<JobOrderInternalComment> jobOrderInternalCommentList = jobOrderInternalCommentRepository.findAll();
        assertThat(jobOrderInternalCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobOrderInternalComment() throws Exception {
        // Initialize the database
        jobOrderInternalCommentRepository.saveAndFlush(jobOrderInternalComment);
        jobOrderInternalCommentSearchRepository.save(jobOrderInternalComment);

        // Search the jobOrderInternalComment
        restJobOrderInternalCommentMockMvc.perform(get("/api/_search/job-order-internal-comments?query=id:" + jobOrderInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrderInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOrderInternalComment.class);
        JobOrderInternalComment jobOrderInternalComment1 = new JobOrderInternalComment();
        jobOrderInternalComment1.setId(1L);
        JobOrderInternalComment jobOrderInternalComment2 = new JobOrderInternalComment();
        jobOrderInternalComment2.setId(jobOrderInternalComment1.getId());
        assertThat(jobOrderInternalComment1).isEqualTo(jobOrderInternalComment2);
        jobOrderInternalComment2.setId(2L);
        assertThat(jobOrderInternalComment1).isNotEqualTo(jobOrderInternalComment2);
        jobOrderInternalComment1.setId(null);
        assertThat(jobOrderInternalComment1).isNotEqualTo(jobOrderInternalComment2);
    }
}
