package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.ApplicantInternalComment;
import com.recruitsmart.repository.ApplicantInternalCommentRepository;
import com.recruitsmart.repository.search.ApplicantInternalCommentSearchRepository;
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
 * Test class for the ApplicantInternalCommentResource REST controller.
 *
 * @see ApplicantInternalCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class ApplicantInternalCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApplicantInternalCommentRepository applicantInternalCommentRepository;

    @Autowired
    private ApplicantInternalCommentSearchRepository applicantInternalCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicantInternalCommentMockMvc;

    private ApplicantInternalComment applicantInternalComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicantInternalCommentResource applicantInternalCommentResource = new ApplicantInternalCommentResource(applicantInternalCommentRepository, applicantInternalCommentSearchRepository);
        this.restApplicantInternalCommentMockMvc = MockMvcBuilders.standaloneSetup(applicantInternalCommentResource)
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
    public static ApplicantInternalComment createEntity(EntityManager em) {
        ApplicantInternalComment applicantInternalComment = new ApplicantInternalComment();
        applicantInternalComment.setName(DEFAULT_NAME);
        applicantInternalComment.setDescription(DEFAULT_DESCRIPTION);
        return applicantInternalComment;
    }

    @Before
    public void initTest() {
        applicantInternalCommentSearchRepository.deleteAll();
        applicantInternalComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantInternalComment() throws Exception {
        int databaseSizeBeforeCreate = applicantInternalCommentRepository.findAll().size();

        // Create the ApplicantInternalComment
        restApplicantInternalCommentMockMvc.perform(post("/api/applicant-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantInternalComment)))
            .andExpect(status().isCreated());

        // Validate the ApplicantInternalComment in the database
        List<ApplicantInternalComment> applicantInternalCommentList = applicantInternalCommentRepository.findAll();
        assertThat(applicantInternalCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantInternalComment testApplicantInternalComment = applicantInternalCommentList.get(applicantInternalCommentList.size() - 1);
        assertThat(testApplicantInternalComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicantInternalComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ApplicantInternalComment in Elasticsearch
        ApplicantInternalComment applicantInternalCommentEs = applicantInternalCommentSearchRepository.findOne(testApplicantInternalComment.getId());
        assertThat(applicantInternalCommentEs).isEqualToIgnoringGivenFields(testApplicantInternalComment);
    }

    @Test
    @Transactional
    public void createApplicantInternalCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantInternalCommentRepository.findAll().size();

        // Create the ApplicantInternalComment with an existing ID
        applicantInternalComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantInternalCommentMockMvc.perform(post("/api/applicant-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantInternalComment)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantInternalComment in the database
        List<ApplicantInternalComment> applicantInternalCommentList = applicantInternalCommentRepository.findAll();
        assertThat(applicantInternalCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicantInternalComments() throws Exception {
        // Initialize the database
        applicantInternalCommentRepository.saveAndFlush(applicantInternalComment);

        // Get all the applicantInternalCommentList
        restApplicantInternalCommentMockMvc.perform(get("/api/applicant-internal-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getApplicantInternalComment() throws Exception {
        // Initialize the database
        applicantInternalCommentRepository.saveAndFlush(applicantInternalComment);

        // Get the applicantInternalComment
        restApplicantInternalCommentMockMvc.perform(get("/api/applicant-internal-comments/{id}", applicantInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicantInternalComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantInternalComment() throws Exception {
        // Get the applicantInternalComment
        restApplicantInternalCommentMockMvc.perform(get("/api/applicant-internal-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantInternalComment() throws Exception {
        // Initialize the database
        applicantInternalCommentRepository.saveAndFlush(applicantInternalComment);
        applicantInternalCommentSearchRepository.save(applicantInternalComment);
        int databaseSizeBeforeUpdate = applicantInternalCommentRepository.findAll().size();

        // Update the applicantInternalComment
        ApplicantInternalComment updatedApplicantInternalComment = applicantInternalCommentRepository.findOne(applicantInternalComment.getId());
        // Disconnect from session so that the updates on updatedApplicantInternalComment are not directly saved in db
        em.detach(updatedApplicantInternalComment);
        updatedApplicantInternalComment.setName(UPDATED_NAME);
        updatedApplicantInternalComment.setDescription(UPDATED_DESCRIPTION);

        restApplicantInternalCommentMockMvc.perform(put("/api/applicant-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantInternalComment)))
            .andExpect(status().isOk());

        // Validate the ApplicantInternalComment in the database
        List<ApplicantInternalComment> applicantInternalCommentList = applicantInternalCommentRepository.findAll();
        assertThat(applicantInternalCommentList).hasSize(databaseSizeBeforeUpdate);
        ApplicantInternalComment testApplicantInternalComment = applicantInternalCommentList.get(applicantInternalCommentList.size() - 1);
        assertThat(testApplicantInternalComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicantInternalComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ApplicantInternalComment in Elasticsearch
        ApplicantInternalComment applicantInternalCommentEs = applicantInternalCommentSearchRepository.findOne(testApplicantInternalComment.getId());
        assertThat(applicantInternalCommentEs).isEqualToIgnoringGivenFields(testApplicantInternalComment);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantInternalComment() throws Exception {
        int databaseSizeBeforeUpdate = applicantInternalCommentRepository.findAll().size();

        // Create the ApplicantInternalComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicantInternalCommentMockMvc.perform(put("/api/applicant-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicantInternalComment)))
            .andExpect(status().isCreated());

        // Validate the ApplicantInternalComment in the database
        List<ApplicantInternalComment> applicantInternalCommentList = applicantInternalCommentRepository.findAll();
        assertThat(applicantInternalCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicantInternalComment() throws Exception {
        // Initialize the database
        applicantInternalCommentRepository.saveAndFlush(applicantInternalComment);
        applicantInternalCommentSearchRepository.save(applicantInternalComment);
        int databaseSizeBeforeDelete = applicantInternalCommentRepository.findAll().size();

        // Get the applicantInternalComment
        restApplicantInternalCommentMockMvc.perform(delete("/api/applicant-internal-comments/{id}", applicantInternalComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean applicantInternalCommentExistsInEs = applicantInternalCommentSearchRepository.exists(applicantInternalComment.getId());
        assertThat(applicantInternalCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<ApplicantInternalComment> applicantInternalCommentList = applicantInternalCommentRepository.findAll();
        assertThat(applicantInternalCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApplicantInternalComment() throws Exception {
        // Initialize the database
        applicantInternalCommentRepository.saveAndFlush(applicantInternalComment);
        applicantInternalCommentSearchRepository.save(applicantInternalComment);

        // Search the applicantInternalComment
        restApplicantInternalCommentMockMvc.perform(get("/api/_search/applicant-internal-comments?query=id:" + applicantInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantInternalComment.class);
        ApplicantInternalComment applicantInternalComment1 = new ApplicantInternalComment();
        applicantInternalComment1.setId(1L);
        ApplicantInternalComment applicantInternalComment2 = new ApplicantInternalComment();
        applicantInternalComment2.setId(applicantInternalComment1.getId());
        assertThat(applicantInternalComment1).isEqualTo(applicantInternalComment2);
        applicantInternalComment2.setId(2L);
        assertThat(applicantInternalComment1).isNotEqualTo(applicantInternalComment2);
        applicantInternalComment1.setId(null);
        assertThat(applicantInternalComment1).isNotEqualTo(applicantInternalComment2);
    }
}
