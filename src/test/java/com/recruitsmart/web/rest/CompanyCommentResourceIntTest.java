package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.CompanyComment;
import com.recruitsmart.repository.CompanyCommentRepository;
import com.recruitsmart.repository.search.CompanyCommentSearchRepository;
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
 * Test class for the CompanyCommentResource REST controller.
 *
 * @see CompanyCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class CompanyCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CompanyCommentRepository companyCommentRepository;

    @Autowired
    private CompanyCommentSearchRepository companyCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyCommentMockMvc;

    private CompanyComment companyComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyCommentResource companyCommentResource = new CompanyCommentResource(companyCommentRepository, companyCommentSearchRepository);
        this.restCompanyCommentMockMvc = MockMvcBuilders.standaloneSetup(companyCommentResource)
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
    public static CompanyComment createEntity(EntityManager em) {
        CompanyComment companyComment = new CompanyComment();
        companyComment.setName(DEFAULT_NAME);
        companyComment.setDescription(DEFAULT_DESCRIPTION);
        return companyComment;
    }

    @Before
    public void initTest() {
        companyCommentSearchRepository.deleteAll();
        companyComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyComment() throws Exception {
        int databaseSizeBeforeCreate = companyCommentRepository.findAll().size();

        // Create the CompanyComment
        restCompanyCommentMockMvc.perform(post("/api/company-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyComment)))
            .andExpect(status().isCreated());

        // Validate the CompanyComment in the database
        List<CompanyComment> companyCommentList = companyCommentRepository.findAll();
        assertThat(companyCommentList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyComment testCompanyComment = companyCommentList.get(companyCommentList.size() - 1);
        assertThat(testCompanyComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CompanyComment in Elasticsearch
        CompanyComment companyCommentEs = companyCommentSearchRepository.findOne(testCompanyComment.getId());
        assertThat(companyCommentEs).isEqualToIgnoringGivenFields(testCompanyComment);
    }

    @Test
    @Transactional
    public void createCompanyCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyCommentRepository.findAll().size();

        // Create the CompanyComment with an existing ID
        companyComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCommentMockMvc.perform(post("/api/company-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyComment)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyComment in the database
        List<CompanyComment> companyCommentList = companyCommentRepository.findAll();
        assertThat(companyCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyComments() throws Exception {
        // Initialize the database
        companyCommentRepository.saveAndFlush(companyComment);

        // Get all the companyCommentList
        restCompanyCommentMockMvc.perform(get("/api/company-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCompanyComment() throws Exception {
        // Initialize the database
        companyCommentRepository.saveAndFlush(companyComment);

        // Get the companyComment
        restCompanyCommentMockMvc.perform(get("/api/company-comments/{id}", companyComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyComment() throws Exception {
        // Get the companyComment
        restCompanyCommentMockMvc.perform(get("/api/company-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyComment() throws Exception {
        // Initialize the database
        companyCommentRepository.saveAndFlush(companyComment);
        companyCommentSearchRepository.save(companyComment);
        int databaseSizeBeforeUpdate = companyCommentRepository.findAll().size();

        // Update the companyComment
        CompanyComment updatedCompanyComment = companyCommentRepository.findOne(companyComment.getId());
        // Disconnect from session so that the updates on updatedCompanyComment are not directly saved in db
        em.detach(updatedCompanyComment);
        updatedCompanyComment.setName(UPDATED_NAME);
        updatedCompanyComment.setDescription(UPDATED_DESCRIPTION);

        restCompanyCommentMockMvc.perform(put("/api/company-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyComment)))
            .andExpect(status().isOk());

        // Validate the CompanyComment in the database
        List<CompanyComment> companyCommentList = companyCommentRepository.findAll();
        assertThat(companyCommentList).hasSize(databaseSizeBeforeUpdate);
        CompanyComment testCompanyComment = companyCommentList.get(companyCommentList.size() - 1);
        assertThat(testCompanyComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CompanyComment in Elasticsearch
        CompanyComment companyCommentEs = companyCommentSearchRepository.findOne(testCompanyComment.getId());
        assertThat(companyCommentEs).isEqualToIgnoringGivenFields(testCompanyComment);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyComment() throws Exception {
        int databaseSizeBeforeUpdate = companyCommentRepository.findAll().size();

        // Create the CompanyComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyCommentMockMvc.perform(put("/api/company-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyComment)))
            .andExpect(status().isCreated());

        // Validate the CompanyComment in the database
        List<CompanyComment> companyCommentList = companyCommentRepository.findAll();
        assertThat(companyCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyComment() throws Exception {
        // Initialize the database
        companyCommentRepository.saveAndFlush(companyComment);
        companyCommentSearchRepository.save(companyComment);
        int databaseSizeBeforeDelete = companyCommentRepository.findAll().size();

        // Get the companyComment
        restCompanyCommentMockMvc.perform(delete("/api/company-comments/{id}", companyComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyCommentExistsInEs = companyCommentSearchRepository.exists(companyComment.getId());
        assertThat(companyCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyComment> companyCommentList = companyCommentRepository.findAll();
        assertThat(companyCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyComment() throws Exception {
        // Initialize the database
        companyCommentRepository.saveAndFlush(companyComment);
        companyCommentSearchRepository.save(companyComment);

        // Search the companyComment
        restCompanyCommentMockMvc.perform(get("/api/_search/company-comments?query=id:" + companyComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyComment.class);
        CompanyComment companyComment1 = new CompanyComment();
        companyComment1.setId(1L);
        CompanyComment companyComment2 = new CompanyComment();
        companyComment2.setId(companyComment1.getId());
        assertThat(companyComment1).isEqualTo(companyComment2);
        companyComment2.setId(2L);
        assertThat(companyComment1).isNotEqualTo(companyComment2);
        companyComment1.setId(null);
        assertThat(companyComment1).isNotEqualTo(companyComment2);
    }
}
