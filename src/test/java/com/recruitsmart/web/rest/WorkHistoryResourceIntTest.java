package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.WorkHistory;
import com.recruitsmart.repository.WorkHistoryRepository;
import com.recruitsmart.repository.search.WorkHistorySearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.recruitsmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkHistoryResource REST controller.
 *
 * @see WorkHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class WorkHistoryResourceIntTest {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_STARTING_COMPENSATION = 1D;
    private static final Double UPDATED_STARTING_COMPENSATION = 2D;

    private static final Double DEFAULT_ENDING_COMPENSATION = 1D;
    private static final Double UPDATED_ENDING_COMPENSATION = 2D;

    private static final Integer DEFAULT_COMPENSATION_TYPE = 1;
    private static final Integer UPDATED_COMPENSATION_TYPE = 2;

    private static final String DEFAULT_SUPERVISOR = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERVISOR_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERVISOR_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DUTIES = "AAAAAAAAAA";
    private static final String UPDATED_DUTIES = "BBBBBBBBBB";

    private static final String DEFAULT_REASON_FOR_LEAVING = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_LEAVING = "BBBBBBBBBB";

    @Autowired
    private WorkHistoryRepository workHistoryRepository;

    @Autowired
    private WorkHistorySearchRepository workHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkHistoryMockMvc;

    private WorkHistory workHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkHistoryResource workHistoryResource = new WorkHistoryResource(workHistoryRepository, workHistorySearchRepository);
        this.restWorkHistoryMockMvc = MockMvcBuilders.standaloneSetup(workHistoryResource)
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
    public static WorkHistory createEntity(EntityManager em) {
        WorkHistory workHistory = new WorkHistory();
        workHistory.setCompany(DEFAULT_COMPANY);
        workHistory.setTitle(DEFAULT_TITLE);
        workHistory.setStartDate(DEFAULT_START_DATE);
        workHistory.setEndDate(DEFAULT_END_DATE);
        workHistory.setStartingCompensation(DEFAULT_STARTING_COMPENSATION);
        workHistory.setEndingCompensation(DEFAULT_ENDING_COMPENSATION);
        workHistory.setCompensationType(DEFAULT_COMPENSATION_TYPE);
        workHistory.setSupervisor(DEFAULT_SUPERVISOR);
        workHistory.setSupervisorTitle(DEFAULT_SUPERVISOR_TITLE);
        workHistory.setSupervisorPhone(DEFAULT_SUPERVISOR_PHONE);
        workHistory.setDuties(DEFAULT_DUTIES);
        workHistory.setReasonForLeaving(DEFAULT_REASON_FOR_LEAVING);
        return workHistory;
    }

    @Before
    public void initTest() {
        workHistorySearchRepository.deleteAll();
        workHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkHistory() throws Exception {
        int databaseSizeBeforeCreate = workHistoryRepository.findAll().size();

        // Create the WorkHistory
        restWorkHistoryMockMvc.perform(post("/api/work-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workHistory)))
            .andExpect(status().isCreated());

        // Validate the WorkHistory in the database
        List<WorkHistory> workHistoryList = workHistoryRepository.findAll();
        assertThat(workHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkHistory testWorkHistory = workHistoryList.get(workHistoryList.size() - 1);
        assertThat(testWorkHistory.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testWorkHistory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWorkHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkHistory.getStartingCompensation()).isEqualTo(DEFAULT_STARTING_COMPENSATION);
        assertThat(testWorkHistory.getEndingCompensation()).isEqualTo(DEFAULT_ENDING_COMPENSATION);
        assertThat(testWorkHistory.getCompensationType()).isEqualTo(DEFAULT_COMPENSATION_TYPE);
        assertThat(testWorkHistory.getSupervisor()).isEqualTo(DEFAULT_SUPERVISOR);
        assertThat(testWorkHistory.getSupervisorTitle()).isEqualTo(DEFAULT_SUPERVISOR_TITLE);
        assertThat(testWorkHistory.getSupervisorPhone()).isEqualTo(DEFAULT_SUPERVISOR_PHONE);
        assertThat(testWorkHistory.getDuties()).isEqualTo(DEFAULT_DUTIES);
        assertThat(testWorkHistory.getReasonForLeaving()).isEqualTo(DEFAULT_REASON_FOR_LEAVING);

        // Validate the WorkHistory in Elasticsearch
        WorkHistory workHistoryEs = workHistorySearchRepository.findOne(testWorkHistory.getId());
        assertThat(workHistoryEs).isEqualToIgnoringGivenFields(testWorkHistory);
    }

    @Test
    @Transactional
    public void createWorkHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workHistoryRepository.findAll().size();

        // Create the WorkHistory with an existing ID
        workHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkHistoryMockMvc.perform(post("/api/work-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workHistory)))
            .andExpect(status().isBadRequest());

        // Validate the WorkHistory in the database
        List<WorkHistory> workHistoryList = workHistoryRepository.findAll();
        assertThat(workHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkHistories() throws Exception {
        // Initialize the database
        workHistoryRepository.saveAndFlush(workHistory);

        // Get all the workHistoryList
        restWorkHistoryMockMvc.perform(get("/api/work-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startingCompensation").value(hasItem(DEFAULT_STARTING_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].endingCompensation").value(hasItem(DEFAULT_ENDING_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].compensationType").value(hasItem(DEFAULT_COMPENSATION_TYPE)))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR.toString())))
            .andExpect(jsonPath("$.[*].supervisorTitle").value(hasItem(DEFAULT_SUPERVISOR_TITLE.toString())))
            .andExpect(jsonPath("$.[*].supervisorPhone").value(hasItem(DEFAULT_SUPERVISOR_PHONE.toString())))
            .andExpect(jsonPath("$.[*].duties").value(hasItem(DEFAULT_DUTIES.toString())))
            .andExpect(jsonPath("$.[*].reasonForLeaving").value(hasItem(DEFAULT_REASON_FOR_LEAVING.toString())));
    }

    @Test
    @Transactional
    public void getWorkHistory() throws Exception {
        // Initialize the database
        workHistoryRepository.saveAndFlush(workHistory);

        // Get the workHistory
        restWorkHistoryMockMvc.perform(get("/api/work-histories/{id}", workHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workHistory.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.startingCompensation").value(DEFAULT_STARTING_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.endingCompensation").value(DEFAULT_ENDING_COMPENSATION.doubleValue()))
            .andExpect(jsonPath("$.compensationType").value(DEFAULT_COMPENSATION_TYPE))
            .andExpect(jsonPath("$.supervisor").value(DEFAULT_SUPERVISOR.toString()))
            .andExpect(jsonPath("$.supervisorTitle").value(DEFAULT_SUPERVISOR_TITLE.toString()))
            .andExpect(jsonPath("$.supervisorPhone").value(DEFAULT_SUPERVISOR_PHONE.toString()))
            .andExpect(jsonPath("$.duties").value(DEFAULT_DUTIES.toString()))
            .andExpect(jsonPath("$.reasonForLeaving").value(DEFAULT_REASON_FOR_LEAVING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkHistory() throws Exception {
        // Get the workHistory
        restWorkHistoryMockMvc.perform(get("/api/work-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkHistory() throws Exception {
        // Initialize the database
        workHistoryRepository.saveAndFlush(workHistory);
        workHistorySearchRepository.save(workHistory);
        int databaseSizeBeforeUpdate = workHistoryRepository.findAll().size();

        // Update the workHistory
        WorkHistory updatedWorkHistory = workHistoryRepository.findOne(workHistory.getId());
        // Disconnect from session so that the updates on updatedWorkHistory are not directly saved in db
        em.detach(updatedWorkHistory);
        updatedWorkHistory.setCompany(UPDATED_COMPANY);
        updatedWorkHistory.setTitle(UPDATED_TITLE);
        updatedWorkHistory.setStartDate(UPDATED_START_DATE);
        updatedWorkHistory.setEndDate(UPDATED_END_DATE);
        updatedWorkHistory.setStartingCompensation(UPDATED_STARTING_COMPENSATION);
        updatedWorkHistory.setEndingCompensation(UPDATED_ENDING_COMPENSATION);
        updatedWorkHistory.setCompensationType(UPDATED_COMPENSATION_TYPE);
        updatedWorkHistory.setSupervisor(UPDATED_SUPERVISOR);
        updatedWorkHistory.setSupervisorTitle(UPDATED_SUPERVISOR_TITLE);
        updatedWorkHistory.setSupervisorPhone(UPDATED_SUPERVISOR_PHONE);
        updatedWorkHistory.setDuties(UPDATED_DUTIES);
        updatedWorkHistory.setReasonForLeaving(UPDATED_REASON_FOR_LEAVING);

        restWorkHistoryMockMvc.perform(put("/api/work-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkHistory)))
            .andExpect(status().isOk());

        // Validate the WorkHistory in the database
        List<WorkHistory> workHistoryList = workHistoryRepository.findAll();
        assertThat(workHistoryList).hasSize(databaseSizeBeforeUpdate);
        WorkHistory testWorkHistory = workHistoryList.get(workHistoryList.size() - 1);
        assertThat(testWorkHistory.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testWorkHistory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkHistory.getStartingCompensation()).isEqualTo(UPDATED_STARTING_COMPENSATION);
        assertThat(testWorkHistory.getEndingCompensation()).isEqualTo(UPDATED_ENDING_COMPENSATION);
        assertThat(testWorkHistory.getCompensationType()).isEqualTo(UPDATED_COMPENSATION_TYPE);
        assertThat(testWorkHistory.getSupervisor()).isEqualTo(UPDATED_SUPERVISOR);
        assertThat(testWorkHistory.getSupervisorTitle()).isEqualTo(UPDATED_SUPERVISOR_TITLE);
        assertThat(testWorkHistory.getSupervisorPhone()).isEqualTo(UPDATED_SUPERVISOR_PHONE);
        assertThat(testWorkHistory.getDuties()).isEqualTo(UPDATED_DUTIES);
        assertThat(testWorkHistory.getReasonForLeaving()).isEqualTo(UPDATED_REASON_FOR_LEAVING);

        // Validate the WorkHistory in Elasticsearch
        WorkHistory workHistoryEs = workHistorySearchRepository.findOne(testWorkHistory.getId());
        assertThat(workHistoryEs).isEqualToIgnoringGivenFields(testWorkHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkHistory() throws Exception {
        int databaseSizeBeforeUpdate = workHistoryRepository.findAll().size();

        // Create the WorkHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkHistoryMockMvc.perform(put("/api/work-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workHistory)))
            .andExpect(status().isCreated());

        // Validate the WorkHistory in the database
        List<WorkHistory> workHistoryList = workHistoryRepository.findAll();
        assertThat(workHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkHistory() throws Exception {
        // Initialize the database
        workHistoryRepository.saveAndFlush(workHistory);
        workHistorySearchRepository.save(workHistory);
        int databaseSizeBeforeDelete = workHistoryRepository.findAll().size();

        // Get the workHistory
        restWorkHistoryMockMvc.perform(delete("/api/work-histories/{id}", workHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean workHistoryExistsInEs = workHistorySearchRepository.exists(workHistory.getId());
        assertThat(workHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<WorkHistory> workHistoryList = workHistoryRepository.findAll();
        assertThat(workHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWorkHistory() throws Exception {
        // Initialize the database
        workHistoryRepository.saveAndFlush(workHistory);
        workHistorySearchRepository.save(workHistory);

        // Search the workHistory
        restWorkHistoryMockMvc.perform(get("/api/_search/work-histories?query=id:" + workHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startingCompensation").value(hasItem(DEFAULT_STARTING_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].endingCompensation").value(hasItem(DEFAULT_ENDING_COMPENSATION.doubleValue())))
            .andExpect(jsonPath("$.[*].compensationType").value(hasItem(DEFAULT_COMPENSATION_TYPE)))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR.toString())))
            .andExpect(jsonPath("$.[*].supervisorTitle").value(hasItem(DEFAULT_SUPERVISOR_TITLE.toString())))
            .andExpect(jsonPath("$.[*].supervisorPhone").value(hasItem(DEFAULT_SUPERVISOR_PHONE.toString())))
            .andExpect(jsonPath("$.[*].duties").value(hasItem(DEFAULT_DUTIES.toString())))
            .andExpect(jsonPath("$.[*].reasonForLeaving").value(hasItem(DEFAULT_REASON_FOR_LEAVING.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkHistory.class);
        WorkHistory workHistory1 = new WorkHistory();
        workHistory1.setId(1L);
        WorkHistory workHistory2 = new WorkHistory();
        workHistory2.setId(workHistory1.getId());
        assertThat(workHistory1).isEqualTo(workHistory2);
        workHistory2.setId(2L);
        assertThat(workHistory1).isNotEqualTo(workHistory2);
        workHistory1.setId(null);
        assertThat(workHistory1).isNotEqualTo(workHistory2);
    }
}
