package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.CompanyInternalComment;
import com.recruitsmart.repository.CompanyInternalCommentRepository;
import com.recruitsmart.repository.search.CompanyInternalCommentSearchRepository;
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
 * Test class for the CompanyInternalCommentResource REST controller.
 *
 * @see CompanyInternalCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class CompanyInternalCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CompanyInternalCommentRepository companyInternalCommentRepository;

    @Autowired
    private CompanyInternalCommentSearchRepository companyInternalCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyInternalCommentMockMvc;

    private CompanyInternalComment companyInternalComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyInternalCommentResource companyInternalCommentResource = new CompanyInternalCommentResource(companyInternalCommentRepository, companyInternalCommentSearchRepository);
        this.restCompanyInternalCommentMockMvc = MockMvcBuilders.standaloneSetup(companyInternalCommentResource)
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
    public static CompanyInternalComment createEntity(EntityManager em) {
        CompanyInternalComment companyInternalComment = new CompanyInternalComment();
        companyInternalComment.setName(DEFAULT_NAME);
        companyInternalComment.setDescription(DEFAULT_DESCRIPTION);
        return companyInternalComment;
    }

    @Before
    public void initTest() {
        companyInternalCommentSearchRepository.deleteAll();
        companyInternalComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyInternalComment() throws Exception {
        int databaseSizeBeforeCreate = companyInternalCommentRepository.findAll().size();

        // Create the CompanyInternalComment
        restCompanyInternalCommentMockMvc.perform(post("/api/company-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyInternalComment)))
            .andExpect(status().isCreated());

        // Validate the CompanyInternalComment in the database
        List<CompanyInternalComment> companyInternalCommentList = companyInternalCommentRepository.findAll();
        assertThat(companyInternalCommentList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyInternalComment testCompanyInternalComment = companyInternalCommentList.get(companyInternalCommentList.size() - 1);
        assertThat(testCompanyInternalComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyInternalComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CompanyInternalComment in Elasticsearch
        CompanyInternalComment companyInternalCommentEs = companyInternalCommentSearchRepository.findOne(testCompanyInternalComment.getId());
        assertThat(companyInternalCommentEs).isEqualToIgnoringGivenFields(testCompanyInternalComment);
    }

    @Test
    @Transactional
    public void createCompanyInternalCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyInternalCommentRepository.findAll().size();

        // Create the CompanyInternalComment with an existing ID
        companyInternalComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyInternalCommentMockMvc.perform(post("/api/company-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyInternalComment)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyInternalComment in the database
        List<CompanyInternalComment> companyInternalCommentList = companyInternalCommentRepository.findAll();
        assertThat(companyInternalCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyInternalComments() throws Exception {
        // Initialize the database
        companyInternalCommentRepository.saveAndFlush(companyInternalComment);

        // Get all the companyInternalCommentList
        restCompanyInternalCommentMockMvc.perform(get("/api/company-internal-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCompanyInternalComment() throws Exception {
        // Initialize the database
        companyInternalCommentRepository.saveAndFlush(companyInternalComment);

        // Get the companyInternalComment
        restCompanyInternalCommentMockMvc.perform(get("/api/company-internal-comments/{id}", companyInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyInternalComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyInternalComment() throws Exception {
        // Get the companyInternalComment
        restCompanyInternalCommentMockMvc.perform(get("/api/company-internal-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyInternalComment() throws Exception {
        // Initialize the database
        companyInternalCommentRepository.saveAndFlush(companyInternalComment);
        companyInternalCommentSearchRepository.save(companyInternalComment);
        int databaseSizeBeforeUpdate = companyInternalCommentRepository.findAll().size();

        // Update the companyInternalComment
        CompanyInternalComment updatedCompanyInternalComment = companyInternalCommentRepository.findOne(companyInternalComment.getId());
        // Disconnect from session so that the updates on updatedCompanyInternalComment are not directly saved in db
        em.detach(updatedCompanyInternalComment);
        updatedCompanyInternalComment.setName(UPDATED_NAME);
        updatedCompanyInternalComment.setDescription(UPDATED_DESCRIPTION);

        restCompanyInternalCommentMockMvc.perform(put("/api/company-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyInternalComment)))
            .andExpect(status().isOk());

        // Validate the CompanyInternalComment in the database
        List<CompanyInternalComment> companyInternalCommentList = companyInternalCommentRepository.findAll();
        assertThat(companyInternalCommentList).hasSize(databaseSizeBeforeUpdate);
        CompanyInternalComment testCompanyInternalComment = companyInternalCommentList.get(companyInternalCommentList.size() - 1);
        assertThat(testCompanyInternalComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyInternalComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CompanyInternalComment in Elasticsearch
        CompanyInternalComment companyInternalCommentEs = companyInternalCommentSearchRepository.findOne(testCompanyInternalComment.getId());
        assertThat(companyInternalCommentEs).isEqualToIgnoringGivenFields(testCompanyInternalComment);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyInternalComment() throws Exception {
        int databaseSizeBeforeUpdate = companyInternalCommentRepository.findAll().size();

        // Create the CompanyInternalComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyInternalCommentMockMvc.perform(put("/api/company-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyInternalComment)))
            .andExpect(status().isCreated());

        // Validate the CompanyInternalComment in the database
        List<CompanyInternalComment> companyInternalCommentList = companyInternalCommentRepository.findAll();
        assertThat(companyInternalCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyInternalComment() throws Exception {
        // Initialize the database
        companyInternalCommentRepository.saveAndFlush(companyInternalComment);
        companyInternalCommentSearchRepository.save(companyInternalComment);
        int databaseSizeBeforeDelete = companyInternalCommentRepository.findAll().size();

        // Get the companyInternalComment
        restCompanyInternalCommentMockMvc.perform(delete("/api/company-internal-comments/{id}", companyInternalComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyInternalCommentExistsInEs = companyInternalCommentSearchRepository.exists(companyInternalComment.getId());
        assertThat(companyInternalCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyInternalComment> companyInternalCommentList = companyInternalCommentRepository.findAll();
        assertThat(companyInternalCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyInternalComment() throws Exception {
        // Initialize the database
        companyInternalCommentRepository.saveAndFlush(companyInternalComment);
        companyInternalCommentSearchRepository.save(companyInternalComment);

        // Search the companyInternalComment
        restCompanyInternalCommentMockMvc.perform(get("/api/_search/company-internal-comments?query=id:" + companyInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyInternalComment.class);
        CompanyInternalComment companyInternalComment1 = new CompanyInternalComment();
        companyInternalComment1.setId(1L);
        CompanyInternalComment companyInternalComment2 = new CompanyInternalComment();
        companyInternalComment2.setId(companyInternalComment1.getId());
        assertThat(companyInternalComment1).isEqualTo(companyInternalComment2);
        companyInternalComment2.setId(2L);
        assertThat(companyInternalComment1).isNotEqualTo(companyInternalComment2);
        companyInternalComment1.setId(null);
        assertThat(companyInternalComment1).isNotEqualTo(companyInternalComment2);
    }
}
