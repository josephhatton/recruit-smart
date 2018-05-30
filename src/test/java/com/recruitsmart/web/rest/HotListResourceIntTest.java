package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.HotList;
import com.recruitsmart.repository.HotListRepository;
import com.recruitsmart.repository.search.HotListSearchRepository;
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

import com.recruitsmart.domain.enumeration.MainEntity;
/**
 * Test class for the HotListResource REST controller.
 *
 * @see HotListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class HotListResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final MainEntity DEFAULT_TYPE = MainEntity.APPLICANT;
    private static final MainEntity UPDATED_TYPE = MainEntity.HIRING_CONTACT;

    @Autowired
    private HotListRepository hotListRepository;

    @Autowired
    private HotListSearchRepository hotListSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotListMockMvc;

    private HotList hotList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HotListResource hotListResource = new HotListResource(hotListRepository, hotListSearchRepository);
        this.restHotListMockMvc = MockMvcBuilders.standaloneSetup(hotListResource)
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
    public static HotList createEntity(EntityManager em) {
        HotList hotList = new HotList();
        hotList.setName(DEFAULT_NAME);
        hotList.setType(DEFAULT_TYPE);
        return hotList;
    }

    @Before
    public void initTest() {
        hotListSearchRepository.deleteAll();
        hotList = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotList() throws Exception {
        int databaseSizeBeforeCreate = hotListRepository.findAll().size();

        // Create the HotList
        restHotListMockMvc.perform(post("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotList)))
            .andExpect(status().isCreated());

        // Validate the HotList in the database
        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeCreate + 1);
        HotList testHotList = hotListList.get(hotListList.size() - 1);
        assertThat(testHotList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotList.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the HotList in Elasticsearch
        HotList hotListEs = hotListSearchRepository.findOne(testHotList.getId());
        assertThat(hotListEs).isEqualToIgnoringGivenFields(testHotList);
    }

    @Test
    @Transactional
    public void createHotListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotListRepository.findAll().size();

        // Create the HotList with an existing ID
        hotList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotListMockMvc.perform(post("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotList)))
            .andExpect(status().isBadRequest());

        // Validate the HotList in the database
        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotListRepository.findAll().size();
        // set the field null
        hotList.setName(null);

        // Create the HotList, which fails.

        restHotListMockMvc.perform(post("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotList)))
            .andExpect(status().isBadRequest());

        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotListRepository.findAll().size();
        // set the field null
        hotList.setType(null);

        // Create the HotList, which fails.

        restHotListMockMvc.perform(post("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotList)))
            .andExpect(status().isBadRequest());

        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHotLists() throws Exception {
        // Initialize the database
        hotListRepository.saveAndFlush(hotList);

        // Get all the hotListList
        restHotListMockMvc.perform(get("/api/hot-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getHotList() throws Exception {
        // Initialize the database
        hotListRepository.saveAndFlush(hotList);

        // Get the hotList
        restHotListMockMvc.perform(get("/api/hot-lists/{id}", hotList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotList() throws Exception {
        // Get the hotList
        restHotListMockMvc.perform(get("/api/hot-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotList() throws Exception {
        // Initialize the database
        hotListRepository.saveAndFlush(hotList);
        hotListSearchRepository.save(hotList);
        int databaseSizeBeforeUpdate = hotListRepository.findAll().size();

        // Update the hotList
        HotList updatedHotList = hotListRepository.findOne(hotList.getId());
        // Disconnect from session so that the updates on updatedHotList are not directly saved in db
        em.detach(updatedHotList);
        updatedHotList.setName(UPDATED_NAME);
        updatedHotList.setType(UPDATED_TYPE);

        restHotListMockMvc.perform(put("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotList)))
            .andExpect(status().isOk());

        // Validate the HotList in the database
        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeUpdate);
        HotList testHotList = hotListList.get(hotListList.size() - 1);
        assertThat(testHotList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotList.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the HotList in Elasticsearch
        HotList hotListEs = hotListSearchRepository.findOne(testHotList.getId());
        assertThat(hotListEs).isEqualToIgnoringGivenFields(testHotList);
    }

    @Test
    @Transactional
    public void updateNonExistingHotList() throws Exception {
        int databaseSizeBeforeUpdate = hotListRepository.findAll().size();

        // Create the HotList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHotListMockMvc.perform(put("/api/hot-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotList)))
            .andExpect(status().isCreated());

        // Validate the HotList in the database
        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHotList() throws Exception {
        // Initialize the database
        hotListRepository.saveAndFlush(hotList);
        hotListSearchRepository.save(hotList);
        int databaseSizeBeforeDelete = hotListRepository.findAll().size();

        // Get the hotList
        restHotListMockMvc.perform(delete("/api/hot-lists/{id}", hotList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hotListExistsInEs = hotListSearchRepository.exists(hotList.getId());
        assertThat(hotListExistsInEs).isFalse();

        // Validate the database is empty
        List<HotList> hotListList = hotListRepository.findAll();
        assertThat(hotListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHotList() throws Exception {
        // Initialize the database
        hotListRepository.saveAndFlush(hotList);
        hotListSearchRepository.save(hotList);

        // Search the hotList
        restHotListMockMvc.perform(get("/api/_search/hot-lists?query=id:" + hotList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotList.class);
        HotList hotList1 = new HotList();
        hotList1.setId(1L);
        HotList hotList2 = new HotList();
        hotList2.setId(hotList1.getId());
        assertThat(hotList1).isEqualTo(hotList2);
        hotList2.setId(2L);
        assertThat(hotList1).isNotEqualTo(hotList2);
        hotList1.setId(null);
        assertThat(hotList1).isNotEqualTo(hotList2);
    }
}
