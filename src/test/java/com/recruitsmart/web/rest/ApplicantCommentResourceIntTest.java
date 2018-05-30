package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.ApplicantComment;
import com.recruitsmart.repository.ApplicantCommentRepository;
import com.recruitsmart.repository.search.ApplicantCommentSearchRepository;
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
 * Test class for the ApplicantCommentResource REST controller.
 *
 * @see ApplicantCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class ApplicantCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApplicantCommentRepository applicantCommentRepository;

    @Autowired
    private ApplicantCommentSearchRepository applicantCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicantCommentMockMvc;

    private ApplicantComment applicantComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicantCommentResource applicantCommentResource = new ApplicantCommentResource(applicantCommentRepository, applicantCommentSearchRepository);
        this.restApplicantCommentMockMvc = MockMvcBuilders.standaloneSetup(applicantCommentResource)
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
    public static ApplicantComment createEntity(EntityManager em) {
        ApplicantComment applicantComment = new ApplicantComment();
        applicantComment.setName(DEFAULT_NAME);
        applicantComment.setDescription(DEFAULT_DESCRIPTION);
        return applicantComment;
    }

    @Before
    public void initTest() {
        applicantCommentSearchRepository.deleteAll();
        applicantComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantComment() throws Exception {
        int databaseSizeBeforeCreate = applicantCommentRepository.findAll().size();

        // Create the ApplicantComment
        restApplicantCommentMockMvc.perform(post("/api/applicant-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantComment)))
            .andExpect(status().isCreated());

        // Validate the ApplicantComment in the database
        List<ApplicantComment> applicantCommentList = applicantCommentRepository.findAll();
        assertThat(applicantCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantComment testApplicantComment = applicantCommentList.get(applicantCommentList.size() - 1);
        assertThat(testApplicantComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicantComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ApplicantComment in Elasticsearch
        ApplicantComment applicantCommentEs = applicantCommentSearchRepository.findOne(testApplicantComment.getId());
        assertThat(applicantCommentEs).isEqualToIgnoringGivenFields(testApplicantComment);
    }

    @Test
    @Transactional
    public void createApplicantCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantCommentRepository.findAll().size();

        // Create the ApplicantComment with an existing ID
        applicantComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantCommentMockMvc.perform(post("/api/applicant-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantComment)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantComment in the database
        List<ApplicantComment> applicantCommentList = applicantCommentRepository.findAll();
        assertThat(applicantCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicantComments() throws Exception {
        // Initialize the database
        applicantCommentRepository.saveAndFlush(applicantComment);

        // Get all the applicantCommentList
        restApplicantCommentMockMvc.perform(get("/api/applicant-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getApplicantComment() throws Exception {
        // Initialize the database
        applicantCommentRepository.saveAndFlush(applicantComment);

        // Get the applicantComment
        restApplicantCommentMockMvc.perform(get("/api/applicant-comments/{id}", applicantComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicantComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantComment() throws Exception {
        // Get the applicantComment
        restApplicantCommentMockMvc.perform(get("/api/applicant-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantComment() throws Exception {
        // Initialize the database
        applicantCommentRepository.saveAndFlush(applicantComment);
        applicantCommentSearchRepository.save(applicantComment);
        int databaseSizeBeforeUpdate = applicantCommentRepository.findAll().size();

        // Update the applicantComment
        ApplicantComment updatedApplicantComment = applicantCommentRepository.findOne(applicantComment.getId());
        // Disconnect from session so that the updates on updatedApplicantComment are not directly saved in db
        em.detach(updatedApplicantComment);
        updatedApplicantComment.setName(UPDATED_NAME);
        updatedApplicantComment.setDescription(UPDATED_DESCRIPTION);

        restApplicantCommentMockMvc.perform(put("/api/applicant-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantComment)))
            .andExpect(status().isOk());

        // Validate the ApplicantComment in the database
        List<ApplicantComment> applicantCommentList = applicantCommentRepository.findAll();
        assertThat(applicantCommentList).hasSize(databaseSizeBeforeUpdate);
        ApplicantComment testApplicantComment = applicantCommentList.get(applicantCommentList.size() - 1);
        assertThat(testApplicantComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicantComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ApplicantComment in Elasticsearch
        ApplicantComment applicantCommentEs = applicantCommentSearchRepository.findOne(testApplicantComment.getId());
        assertThat(applicantCommentEs).isEqualToIgnoringGivenFields(testApplicantComment);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantComment() throws Exception {
        int databaseSizeBeforeUpdate = applicantCommentRepository.findAll().size();

        // Create the ApplicantComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicantCommentMockMvc.perform(put("/api/applicant-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantComment)))
            .andExpect(status().isCreated());

        // Validate the ApplicantComment in the database
        List<ApplicantComment> applicantCommentList = applicantCommentRepository.findAll();
        assertThat(applicantCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicantComment() throws Exception {
        // Initialize the database
        applicantCommentRepository.saveAndFlush(applicantComment);
        applicantCommentSearchRepository.save(applicantComment);
        int databaseSizeBeforeDelete = applicantCommentRepository.findAll().size();

        // Get the applicantComment
        restApplicantCommentMockMvc.perform(delete("/api/applicant-comments/{id}", applicantComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean applicantCommentExistsInEs = applicantCommentSearchRepository.exists(applicantComment.getId());
        assertThat(applicantCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<ApplicantComment> applicantCommentList = applicantCommentRepository.findAll();
        assertThat(applicantCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApplicantComment() throws Exception {
        // Initialize the database
        applicantCommentRepository.saveAndFlush(applicantComment);
        applicantCommentSearchRepository.save(applicantComment);

        // Search the applicantComment
        restApplicantCommentMockMvc.perform(get("/api/_search/applicant-comments?query=id:" + applicantComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantComment.class);
        ApplicantComment applicantComment1 = new ApplicantComment();
        applicantComment1.setId(1L);
        ApplicantComment applicantComment2 = new ApplicantComment();
        applicantComment2.setId(applicantComment1.getId());
        assertThat(applicantComment1).isEqualTo(applicantComment2);
        applicantComment2.setId(2L);
        assertThat(applicantComment1).isNotEqualTo(applicantComment2);
        applicantComment1.setId(null);
        assertThat(applicantComment1).isNotEqualTo(applicantComment2);
    }
}
