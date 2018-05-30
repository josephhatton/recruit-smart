package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.HiringContactComment;
import com.recruitsmart.repository.HiringContactCommentRepository;
import com.recruitsmart.repository.search.HiringContactCommentSearchRepository;
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
 * Test class for the HiringContactCommentResource REST controller.
 *
 * @see HiringContactCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class HiringContactCommentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HiringContactCommentRepository hiringContactCommentRepository;

    @Autowired
    private HiringContactCommentSearchRepository hiringContactCommentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHiringContactCommentMockMvc;

    private HiringContactComment hiringContactComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HiringContactCommentResource hiringContactCommentResource = new HiringContactCommentResource(hiringContactCommentRepository, hiringContactCommentSearchRepository);
        this.restHiringContactCommentMockMvc = MockMvcBuilders.standaloneSetup(hiringContactCommentResource)
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
    public static HiringContactComment createEntity(EntityManager em) {
        HiringContactComment hiringContactComment = new HiringContactComment();
        hiringContactComment.setName(DEFAULT_NAME);
        hiringContactComment.setDescription(DEFAULT_DESCRIPTION);
        return hiringContactComment;
    }

    @Before
    public void initTest() {
        hiringContactCommentSearchRepository.deleteAll();
        hiringContactComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createHiringContactComment() throws Exception {
        int databaseSizeBeforeCreate = hiringContactCommentRepository.findAll().size();

        // Create the HiringContactComment
        restHiringContactCommentMockMvc.perform(post("/api/hiring-contact-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactComment)))
            .andExpect(status().isCreated());

        // Validate the HiringContactComment in the database
        List<HiringContactComment> hiringContactCommentList = hiringContactCommentRepository.findAll();
        assertThat(hiringContactCommentList).hasSize(databaseSizeBeforeCreate + 1);
        HiringContactComment testHiringContactComment = hiringContactCommentList.get(hiringContactCommentList.size() - 1);
        assertThat(testHiringContactComment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHiringContactComment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the HiringContactComment in Elasticsearch
        HiringContactComment hiringContactCommentEs = hiringContactCommentSearchRepository.findOne(testHiringContactComment.getId());
        assertThat(hiringContactCommentEs).isEqualToIgnoringGivenFields(testHiringContactComment);
    }

    @Test
    @Transactional
    public void createHiringContactCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hiringContactCommentRepository.findAll().size();

        // Create the HiringContactComment with an existing ID
        hiringContactComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHiringContactCommentMockMvc.perform(post("/api/hiring-contact-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactComment)))
            .andExpect(status().isBadRequest());

        // Validate the HiringContactComment in the database
        List<HiringContactComment> hiringContactCommentList = hiringContactCommentRepository.findAll();
        assertThat(hiringContactCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHiringContactComments() throws Exception {
        // Initialize the database
        hiringContactCommentRepository.saveAndFlush(hiringContactComment);

        // Get all the hiringContactCommentList
        restHiringContactCommentMockMvc.perform(get("/api/hiring-contact-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContactComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getHiringContactComment() throws Exception {
        // Initialize the database
        hiringContactCommentRepository.saveAndFlush(hiringContactComment);

        // Get the hiringContactComment
        restHiringContactCommentMockMvc.perform(get("/api/hiring-contact-comments/{id}", hiringContactComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hiringContactComment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHiringContactComment() throws Exception {
        // Get the hiringContactComment
        restHiringContactCommentMockMvc.perform(get("/api/hiring-contact-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHiringContactComment() throws Exception {
        // Initialize the database
        hiringContactCommentRepository.saveAndFlush(hiringContactComment);
        hiringContactCommentSearchRepository.save(hiringContactComment);
        int databaseSizeBeforeUpdate = hiringContactCommentRepository.findAll().size();

        // Update the hiringContactComment
        HiringContactComment updatedHiringContactComment = hiringContactCommentRepository.findOne(hiringContactComment.getId());
        // Disconnect from session so that the updates on updatedHiringContactComment are not directly saved in db
        em.detach(updatedHiringContactComment);
        updatedHiringContactComment.setName(UPDATED_NAME);
        updatedHiringContactComment.setDescription(UPDATED_DESCRIPTION);

        restHiringContactCommentMockMvc.perform(put("/api/hiring-contact-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHiringContactComment)))
            .andExpect(status().isOk());

        // Validate the HiringContactComment in the database
        List<HiringContactComment> hiringContactCommentList = hiringContactCommentRepository.findAll();
        assertThat(hiringContactCommentList).hasSize(databaseSizeBeforeUpdate);
        HiringContactComment testHiringContactComment = hiringContactCommentList.get(hiringContactCommentList.size() - 1);
        assertThat(testHiringContactComment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHiringContactComment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the HiringContactComment in Elasticsearch
        HiringContactComment hiringContactCommentEs = hiringContactCommentSearchRepository.findOne(testHiringContactComment.getId());
        assertThat(hiringContactCommentEs).isEqualToIgnoringGivenFields(testHiringContactComment);
    }

    @Test
    @Transactional
    public void updateNonExistingHiringContactComment() throws Exception {
        int databaseSizeBeforeUpdate = hiringContactCommentRepository.findAll().size();

        // Create the HiringContactComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHiringContactCommentMockMvc.perform(put("/api/hiring-contact-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContactComment)))
            .andExpect(status().isCreated());

        // Validate the HiringContactComment in the database
        List<HiringContactComment> hiringContactCommentList = hiringContactCommentRepository.findAll();
        assertThat(hiringContactCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHiringContactComment() throws Exception {
        // Initialize the database
        hiringContactCommentRepository.saveAndFlush(hiringContactComment);
        hiringContactCommentSearchRepository.save(hiringContactComment);
        int databaseSizeBeforeDelete = hiringContactCommentRepository.findAll().size();

        // Get the hiringContactComment
        restHiringContactCommentMockMvc.perform(delete("/api/hiring-contact-comments/{id}", hiringContactComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hiringContactCommentExistsInEs = hiringContactCommentSearchRepository.exists(hiringContactComment.getId());
        assertThat(hiringContactCommentExistsInEs).isFalse();

        // Validate the database is empty
        List<HiringContactComment> hiringContactCommentList = hiringContactCommentRepository.findAll();
        assertThat(hiringContactCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHiringContactComment() throws Exception {
        // Initialize the database
        hiringContactCommentRepository.saveAndFlush(hiringContactComment);
        hiringContactCommentSearchRepository.save(hiringContactComment);

        // Search the hiringContactComment
        restHiringContactCommentMockMvc.perform(get("/api/_search/hiring-contact-comments?query=id:" + hiringContactComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContactComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HiringContactComment.class);
        HiringContactComment hiringContactComment1 = new HiringContactComment();
        hiringContactComment1.setId(1L);
        HiringContactComment hiringContactComment2 = new HiringContactComment();
        hiringContactComment2.setId(hiringContactComment1.getId());
        assertThat(hiringContactComment1).isEqualTo(hiringContactComment2);
        hiringContactComment2.setId(2L);
        assertThat(hiringContactComment1).isNotEqualTo(hiringContactComment2);
        hiringContactComment1.setId(null);
        assertThat(hiringContactComment1).isNotEqualTo(hiringContactComment2);
    }
}
