package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.HotListBucket;
import com.recruitsmart.repository.HotListBucketRepository;
import com.recruitsmart.repository.search.HotListBucketSearchRepository;
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
 * Test class for the HotListBucketResource REST controller.
 *
 * @see HotListBucketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class HotListBucketResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private HotListBucketRepository hotListBucketRepository;

    @Autowired
    private HotListBucketSearchRepository hotListBucketSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotListBucketMockMvc;

    private HotListBucket hotListBucket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HotListBucketResource hotListBucketResource = new HotListBucketResource(hotListBucketRepository, hotListBucketSearchRepository);
        this.restHotListBucketMockMvc = MockMvcBuilders.standaloneSetup(hotListBucketResource)
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
    public static HotListBucket createEntity(EntityManager em) {
        HotListBucket hotListBucket = new HotListBucket()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID);
        return hotListBucket;
    }

    @Before
    public void initTest() {
        hotListBucketSearchRepository.deleteAll();
        hotListBucket = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotListBucket() throws Exception {
        int databaseSizeBeforeCreate = hotListBucketRepository.findAll().size();

        // Create the HotListBucket
        restHotListBucketMockMvc.perform(post("/api/hot-list-buckets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotListBucket)))
            .andExpect(status().isCreated());

        // Validate the HotListBucket in the database
        List<HotListBucket> hotListBucketList = hotListBucketRepository.findAll();
        assertThat(hotListBucketList).hasSize(databaseSizeBeforeCreate + 1);
        HotListBucket testHotListBucket = hotListBucketList.get(hotListBucketList.size() - 1);
        assertThat(testHotListBucket.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotListBucket.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the HotListBucket in Elasticsearch
        HotListBucket hotListBucketEs = hotListBucketSearchRepository.findOne(testHotListBucket.getId());
        assertThat(hotListBucketEs).isEqualToIgnoringGivenFields(testHotListBucket);
    }

    @Test
    @Transactional
    public void createHotListBucketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotListBucketRepository.findAll().size();

        // Create the HotListBucket with an existing ID
        hotListBucket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotListBucketMockMvc.perform(post("/api/hot-list-buckets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotListBucket)))
            .andExpect(status().isBadRequest());

        // Validate the HotListBucket in the database
        List<HotListBucket> hotListBucketList = hotListBucketRepository.findAll();
        assertThat(hotListBucketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHotListBuckets() throws Exception {
        // Initialize the database
        hotListBucketRepository.saveAndFlush(hotListBucket);

        // Get all the hotListBucketList
        restHotListBucketMockMvc.perform(get("/api/hot-list-buckets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotListBucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getHotListBucket() throws Exception {
        // Initialize the database
        hotListBucketRepository.saveAndFlush(hotListBucket);

        // Get the hotListBucket
        restHotListBucketMockMvc.perform(get("/api/hot-list-buckets/{id}", hotListBucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotListBucket.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHotListBucket() throws Exception {
        // Get the hotListBucket
        restHotListBucketMockMvc.perform(get("/api/hot-list-buckets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotListBucket() throws Exception {
        // Initialize the database
        hotListBucketRepository.saveAndFlush(hotListBucket);
        hotListBucketSearchRepository.save(hotListBucket);
        int databaseSizeBeforeUpdate = hotListBucketRepository.findAll().size();

        // Update the hotListBucket
        HotListBucket updatedHotListBucket = hotListBucketRepository.findOne(hotListBucket.getId());
        // Disconnect from session so that the updates on updatedHotListBucket are not directly saved in db
        em.detach(updatedHotListBucket);
        updatedHotListBucket
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID);

        restHotListBucketMockMvc.perform(put("/api/hot-list-buckets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotListBucket)))
            .andExpect(status().isOk());

        // Validate the HotListBucket in the database
        List<HotListBucket> hotListBucketList = hotListBucketRepository.findAll();
        assertThat(hotListBucketList).hasSize(databaseSizeBeforeUpdate);
        HotListBucket testHotListBucket = hotListBucketList.get(hotListBucketList.size() - 1);
        assertThat(testHotListBucket.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotListBucket.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the HotListBucket in Elasticsearch
        HotListBucket hotListBucketEs = hotListBucketSearchRepository.findOne(testHotListBucket.getId());
        assertThat(hotListBucketEs).isEqualToIgnoringGivenFields(testHotListBucket);
    }

    @Test
    @Transactional
    public void updateNonExistingHotListBucket() throws Exception {
        int databaseSizeBeforeUpdate = hotListBucketRepository.findAll().size();

        // Create the HotListBucket

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHotListBucketMockMvc.perform(put("/api/hot-list-buckets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotListBucket)))
            .andExpect(status().isCreated());

        // Validate the HotListBucket in the database
        List<HotListBucket> hotListBucketList = hotListBucketRepository.findAll();
        assertThat(hotListBucketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHotListBucket() throws Exception {
        // Initialize the database
        hotListBucketRepository.saveAndFlush(hotListBucket);
        hotListBucketSearchRepository.save(hotListBucket);
        int databaseSizeBeforeDelete = hotListBucketRepository.findAll().size();

        // Get the hotListBucket
        restHotListBucketMockMvc.perform(delete("/api/hot-list-buckets/{id}", hotListBucket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hotListBucketExistsInEs = hotListBucketSearchRepository.exists(hotListBucket.getId());
        assertThat(hotListBucketExistsInEs).isFalse();

        // Validate the database is empty
        List<HotListBucket> hotListBucketList = hotListBucketRepository.findAll();
        assertThat(hotListBucketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHotListBucket() throws Exception {
        // Initialize the database
        hotListBucketRepository.saveAndFlush(hotListBucket);
        hotListBucketSearchRepository.save(hotListBucket);

        // Search the hotListBucket
        restHotListBucketMockMvc.perform(get("/api/_search/hot-list-buckets?query=id:" + hotListBucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotListBucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotListBucket.class);
        HotListBucket hotListBucket1 = new HotListBucket();
        hotListBucket1.setId(1L);
        HotListBucket hotListBucket2 = new HotListBucket();
        hotListBucket2.setId(hotListBucket1.getId());
        assertThat(hotListBucket1).isEqualTo(hotListBucket2);
        hotListBucket2.setId(2L);
        assertThat(hotListBucket1).isNotEqualTo(hotListBucket2);
        hotListBucket1.setId(null);
        assertThat(hotListBucket1).isNotEqualTo(hotListBucket2);
    }
}
