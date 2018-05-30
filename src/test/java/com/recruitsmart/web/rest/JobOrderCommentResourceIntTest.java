package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.JobOrderComment;
import com.recruitsmart.repository.JobOrderCommentRepository;
import com.recruitsmart.repository.search.JobOrderCommentSearchRepository;
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
 * Test class for the JobOrderCommentResource REST controller.
 *
 * @see JobOrderCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class JobOrderCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JobOrderCommentRepository jobOrderCommentRepository;

    @Autowired
    private JobOrderCommentSearchRepository jobOrderCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobOrderCommentMockMvc;

    private JobOrderComment jobOrderComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobOrderCommentResource jobOrderCommentResource = new JobOrderCommentResource(jobOrderCommentRepository, jobOrderCommentSearchRepository);
        this.restJobOrderCommentMockMvc = MockMvcBuilders.standaloneSetup(jobOrderCommentResource)
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
    public static JobOrderComment createEntity(EntityManager em) {
        JobOrderComment jobOrderComment = new JobOrderComment();
        jobOrderComment.setName(DEFAULT_NAME);
        jobOrderComment.setDescription(DEFAULT_DESCRIPTION);
        return jobOrderComment;
    }

    @Before
    public void initTest() {
        jobOrderCommentSearchRepository.deleteAll();
        jobOrderComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobOrderComment() throws Exception {
        int databaseSizeBeforeCreate = jobOrderCommentRepository.findAll().size();

        // Create the JobOrderComment
        restJobOrderCommentMockMvc.perform(post("/api/job-order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderComment)))
            .andExpect(status().isCreated());

        // Validate the JobOrderComment in the database
        List<JobOrderComment> jobOrderCommentList = jobOrderCommentRepository.findAll();
        assertThat(jobOrderCommentList).hasSize(databaseSizeBeforeCreate + 1);
        JobOrderComment testJobOrderComment = jobOrderCommentList.get(jobOrderCommentList.size() - 1);
        assertThat(testJobOrderComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobOrderComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the JobOrderComment in Elasticsearch
        JobOrderComment jobOrderCommentEs = jobOrderCommentSearchRepository.findOne(testJobOrderComment.getId());
        assertThat(jobOrderCommentEs).isEqualToIgnoringGivenFields(testJobOrderComment);
    }

    @Test
    @Transactional
    public void createJobOrderCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOrderCommentRepository.findAll().size();

        // Create the JobOrderComment with an existing ID
        jobOrderComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOrderCommentMockMvc.perform(post("/api/job-order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderComment)))
            .andExpect(status().isBadRequest());

        // Validate the JobOrderComment in the database
        List<JobOrderComment> jobOrderCommentList = jobOrderCommentRepository.findAll();
        assertThat(jobOrderCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobOrderComments() throws Exception {
        // Initialize the database
        jobOrderCommentRepository.saveAndFlush(jobOrderComment);

        // Get all the jobOrderCommentList
        restJobOrderCommentMockMvc.perform(get("/api/job-order-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrderComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getJobOrderComment() throws Exception {
        // Initialize the database
        jobOrderCommentRepository.saveAndFlush(jobOrderComment);

        // Get the jobOrderComment
        restJobOrderCommentMockMvc.perform(get("/api/job-order-comments/{id}", jobOrderComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobOrderComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobOrderComment() throws Exception {
        // Get the jobOrderComment
        restJobOrderCommentMockMvc.perform(get("/api/job-order-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOrderComment() throws Exception {
        // Initialize the database
        jobOrderCommentRepository.saveAndFlush(jobOrderComment);
        jobOrderCommentSearchRepository.save(jobOrderComment);
        int databaseSizeBeforeUpdate = jobOrderCommentRepository.findAll().size();

        // Update the jobOrderComment
        JobOrderComment updatedJobOrderComment = jobOrderCommentRepository.findOne(jobOrderComment.getId());
        // Disconnect from session so that the updates on updatedJobOrderComment are not directly saved in db
        em.detach(updatedJobOrderComment);
        updatedJobOrderComment.setName(UPDATED_NAME);
        updatedJobOrderComment.setDescription(UPDATED_DESCRIPTION);

        restJobOrderCommentMockMvc.perform(put("/api/job-order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobOrderComment)))
            .andExpect(status().isOk());

        // Validate the JobOrderComment in the database
        List<JobOrderComment> jobOrderCommentList = jobOrderCommentRepository.findAll();
        assertThat(jobOrderCommentList).hasSize(databaseSizeBeforeUpdate);
        JobOrderComment testJobOrderComment = jobOrderCommentList.get(jobOrderCommentList.size() - 1);
        assertThat(testJobOrderComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobOrderComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the JobOrderComment in Elasticsearch
        JobOrderComment jobOrderCommentEs = jobOrderCommentSearchRepository.findOne(testJobOrderComment.getId());
        assertThat(jobOrderCommentEs).isEqualToIgnoringGivenFields(testJobOrderComment);
    }

    @Test
    @Transactional
    public void updateNonExistingJobOrderComment() throws Exception {
        int databaseSizeBeforeUpdate = jobOrderCommentRepository.findAll().size();

        // Create the JobOrderComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobOrderCommentMockMvc.perform(put("/api/job-order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOrderComment)))
            .andExpect(status().isCreated());

        // Validate the JobOrderComment in the database
        List<JobOrderComment> jobOrderCommentList = jobOrderCommentRepository.findAll();
        assertThat(jobOrderCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobOrderComment() throws Exception {
        // Initialize the database
        jobOrderCommentRepository.saveAndFlush(jobOrderComment);
        jobOrderCommentSearchRepository.save(jobOrderComment);
        int databaseSizeBeforeDelete = jobOrderCommentRepository.findAll().size();

        // Get the jobOrderComment
        restJobOrderCommentMockMvc.perform(delete("/api/job-order-comments/{id}", jobOrderComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobOrderCommentExistsInEs = jobOrderCommentSearchRepository.exists(jobOrderComment.getId());
        assertThat(jobOrderCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<JobOrderComment> jobOrderCommentList = jobOrderCommentRepository.findAll();
        assertThat(jobOrderCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobOrderComment() throws Exception {
        // Initialize the database
        jobOrderCommentRepository.saveAndFlush(jobOrderComment);
        jobOrderCommentSearchRepository.save(jobOrderComment);

        // Search the jobOrderComment
        restJobOrderCommentMockMvc.perform(get("/api/_search/job-order-comments?query=id:" + jobOrderComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOrderComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOrderComment.class);
        JobOrderComment jobOrderComment1 = new JobOrderComment();
        jobOrderComment1.setId(1L);
        JobOrderComment jobOrderComment2 = new JobOrderComment();
        jobOrderComment2.setId(jobOrderComment1.getId());
        assertThat(jobOrderComment1).isEqualTo(jobOrderComment2);
        jobOrderComment2.setId(2L);
        assertThat(jobOrderComment1).isNotEqualTo(jobOrderComment2);
        jobOrderComment1.setId(null);
        assertThat(jobOrderComment1).isNotEqualTo(jobOrderComment2);
    }
}
