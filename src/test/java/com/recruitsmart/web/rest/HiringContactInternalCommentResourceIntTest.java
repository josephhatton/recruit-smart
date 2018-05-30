package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.HiringContactInternalComment;
import com.recruitsmart.repository.HiringContactInternalCommentRepository;
import com.recruitsmart.repository.search.HiringContactInternalCommentSearchRepository;
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
 * Test class for the HiringContactInternalCommentResource REST controller.
 *
 * @see HiringContactInternalCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class HiringContactInternalCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HiringContactInternalCommentRepository hiringContactInternalCommentRepository;

    @Autowired
    private HiringContactInternalCommentSearchRepository hiringContactInternalCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHiringContactInternalCommentMockMvc;

    private HiringContactInternalComment hiringContactInternalComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HiringContactInternalCommentResource hiringContactInternalCommentResource = new HiringContactInternalCommentResource(hiringContactInternalCommentRepository, hiringContactInternalCommentSearchRepository);
        this.restHiringContactInternalCommentMockMvc = MockMvcBuilders.standaloneSetup(hiringContactInternalCommentResource)
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
    public static HiringContactInternalComment createEntity(EntityManager em) {
        HiringContactInternalComment hiringContactInternalComment = new HiringContactInternalComment();
        hiringContactInternalComment.setName(DEFAULT_NAME);
        hiringContactInternalComment.setDescription(DEFAULT_DESCRIPTION);
        return hiringContactInternalComment;
    }

    @Before
    public void initTest() {
        hiringContactInternalCommentSearchRepository.deleteAll();
        hiringContactInternalComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createHiringContactInternalComment() throws Exception {
        int databaseSizeBeforeCreate = hiringContactInternalCommentRepository.findAll().size();

        // Create the HiringContactInternalComment
        restHiringContactInternalCommentMockMvc.perform(post("/api/hiring-contact-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactInternalComment)))
            .andExpect(status().isCreated());

        // Validate the HiringContactInternalComment in the database
        List<HiringContactInternalComment> hiringContactInternalCommentList = hiringContactInternalCommentRepository.findAll();
        assertThat(hiringContactInternalCommentList).hasSize(databaseSizeBeforeCreate + 1);
        HiringContactInternalComment testHiringContactInternalComment = hiringContactInternalCommentList.get(hiringContactInternalCommentList.size() - 1);
        assertThat(testHiringContactInternalComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHiringContactInternalComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the HiringContactInternalComment in Elasticsearch
        HiringContactInternalComment hiringContactInternalCommentEs = hiringContactInternalCommentSearchRepository.findOne(testHiringContactInternalComment.getId());
        assertThat(hiringContactInternalCommentEs).isEqualToIgnoringGivenFields(testHiringContactInternalComment);
    }

    @Test
    @Transactional
    public void createHiringContactInternalCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hiringContactInternalCommentRepository.findAll().size();

        // Create the HiringContactInternalComment with an existing ID
        hiringContactInternalComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHiringContactInternalCommentMockMvc.perform(post("/api/hiring-contact-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactInternalComment)))
            .andExpect(status().isBadRequest());

        // Validate the HiringContactInternalComment in the database
        List<HiringContactInternalComment> hiringContactInternalCommentList = hiringContactInternalCommentRepository.findAll();
        assertThat(hiringContactInternalCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHiringContactInternalComments() throws Exception {
        // Initialize the database
        hiringContactInternalCommentRepository.saveAndFlush(hiringContactInternalComment);

        // Get all the hiringContactInternalCommentList
        restHiringContactInternalCommentMockMvc.perform(get("/api/hiring-contact-internal-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContactInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getHiringContactInternalComment() throws Exception {
        // Initialize the database
        hiringContactInternalCommentRepository.saveAndFlush(hiringContactInternalComment);

        // Get the hiringContactInternalComment
        restHiringContactInternalCommentMockMvc.perform(get("/api/hiring-contact-internal-comments/{id}", hiringContactInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hiringContactInternalComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHiringContactInternalComment() throws Exception {
        // Get the hiringContactInternalComment
        restHiringContactInternalCommentMockMvc.perform(get("/api/hiring-contact-internal-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHiringContactInternalComment() throws Exception {
        // Initialize the database
        hiringContactInternalCommentRepository.saveAndFlush(hiringContactInternalComment);
        hiringContactInternalCommentSearchRepository.save(hiringContactInternalComment);
        int databaseSizeBeforeUpdate = hiringContactInternalCommentRepository.findAll().size();

        // Update the hiringContactInternalComment
        HiringContactInternalComment updatedHiringContactInternalComment = hiringContactInternalCommentRepository.findOne(hiringContactInternalComment.getId());
        // Disconnect from session so that the updates on updatedHiringContactInternalComment are not directly saved in db
        em.detach(updatedHiringContactInternalComment);
        updatedHiringContactInternalComment.setName(UPDATED_NAME);
        updatedHiringContactInternalComment.setDescription(UPDATED_DESCRIPTION);

        restHiringContactInternalCommentMockMvc.perform(put("/api/hiring-contact-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHiringContactInternalComment)))
            .andExpect(status().isOk());

        // Validate the HiringContactInternalComment in the database
        List<HiringContactInternalComment> hiringContactInternalCommentList = hiringContactInternalCommentRepository.findAll();
        assertThat(hiringContactInternalCommentList).hasSize(databaseSizeBeforeUpdate);
        HiringContactInternalComment testHiringContactInternalComment = hiringContactInternalCommentList.get(hiringContactInternalCommentList.size() - 1);
        assertThat(testHiringContactInternalComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHiringContactInternalComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the HiringContactInternalComment in Elasticsearch
        HiringContactInternalComment hiringContactInternalCommentEs = hiringContactInternalCommentSearchRepository.findOne(testHiringContactInternalComment.getId());
        assertThat(hiringContactInternalCommentEs).isEqualToIgnoringGivenFields(testHiringContactInternalComment);
    }

    @Test
    @Transactional
    public void updateNonExistingHiringContactInternalComment() throws Exception {
        int databaseSizeBeforeUpdate = hiringContactInternalCommentRepository.findAll().size();

        // Create the HiringContactInternalComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHiringContactInternalCommentMockMvc.perform(put("/api/hiring-contact-internal-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactInternalComment)))
            .andExpect(status().isCreated());

        // Validate the HiringContactInternalComment in the database
        List<HiringContactInternalComment> hiringContactInternalCommentList = hiringContactInternalCommentRepository.findAll();
        assertThat(hiringContactInternalCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHiringContactInternalComment() throws Exception {
        // Initialize the database
        hiringContactInternalCommentRepository.saveAndFlush(hiringContactInternalComment);
        hiringContactInternalCommentSearchRepository.save(hiringContactInternalComment);
        int databaseSizeBeforeDelete = hiringContactInternalCommentRepository.findAll().size();

        // Get the hiringContactInternalComment
        restHiringContactInternalCommentMockMvc.perform(delete("/api/hiring-contact-internal-comments/{id}", hiringContactInternalComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hiringContactInternalCommentExistsInEs = hiringContactInternalCommentSearchRepository.exists(hiringContactInternalComment.getId());
        assertThat(hiringContactInternalCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<HiringContactInternalComment> hiringContactInternalCommentList = hiringContactInternalCommentRepository.findAll();
        assertThat(hiringContactInternalCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHiringContactInternalComment() throws Exception {
        // Initialize the database
        hiringContactInternalCommentRepository.saveAndFlush(hiringContactInternalComment);
        hiringContactInternalCommentSearchRepository.save(hiringContactInternalComment);

        // Search the hiringContactInternalComment
        restHiringContactInternalCommentMockMvc.perform(get("/api/_search/hiring-contact-internal-comments?query=id:" + hiringContactInternalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContactInternalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HiringContactInternalComment.class);
        HiringContactInternalComment hiringContactInternalComment1 = new HiringContactInternalComment();
        hiringContactInternalComment1.setId(1L);
        HiringContactInternalComment hiringContactInternalComment2 = new HiringContactInternalComment();
        hiringContactInternalComment2.setId(hiringContactInternalComment1.getId());
        assertThat(hiringContactInternalComment1).isEqualTo(hiringContactInternalComment2);
        hiringContactInternalComment2.setId(2L);
        assertThat(hiringContactInternalComment1).isNotEqualTo(hiringContactInternalComment2);
        hiringContactInternalComment1.setId(null);
        assertThat(hiringContactInternalComment1).isNotEqualTo(hiringContactInternalComment2);
    }
}
