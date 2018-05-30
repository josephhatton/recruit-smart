package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.Applicant;
import com.recruitsmart.repository.ApplicantRepository;
import com.recruitsmart.repository.search.ApplicantSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.recruitsmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicantResource REST controller.
 *
 * @see ApplicantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class ApplicantResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRAL_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERRAL_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_STATUS_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_STATUS_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_DESIRED_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_DESIRED_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_MIN_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_MIN_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_TOTAL_COMP = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_TOTAL_COMP = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_HOURLY_RATE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_HOURLY_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIRED_HOURLY_RATE = "AAAAAAAAAA";
    private static final String UPDATED_DESIRED_HOURLY_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_MIN_HOURLY_RATE = "AAAAAAAAAA";
    private static final String UPDATED_MIN_HOURLY_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPENSATION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PTO = false;
    private static final Boolean UPDATED_PTO = true;

    private static final Boolean DEFAULT_HEALTH = false;
    private static final Boolean UPDATED_HEALTH = true;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantSearchRepository applicantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicantResource applicantResource = new ApplicantResource(applicantRepository, applicantSearchRepository);
        this.restApplicantMockMvc = MockMvcBuilders.standaloneSetup(applicantResource)
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
    public static Applicant createEntity(EntityManager em) {
        Applicant applicant = new Applicant();
        applicant.setFirstName(DEFAULT_FIRST_NAME);
        applicant.setMiddleName(DEFAULT_MIDDLE_NAME);
        applicant.setLastName(DEFAULT_LAST_NAME);
        applicant.setTitle(DEFAULT_TITLE);
        applicant.setEmail1(DEFAULT_EMAIL_1);
        applicant.setHomePhone(DEFAULT_HOME_PHONE);
        applicant.setCellPhone(DEFAULT_CELL_PHONE);
        applicant.setIsDeleted(DEFAULT_IS_DELETED);
        applicant.setWorkPhone(DEFAULT_WORK_PHONE);
        applicant.setEmail2(DEFAULT_EMAIL_2);
        applicant.setReferralSource(DEFAULT_REFERRAL_SOURCE);
        applicant.setWorkStatusNote(DEFAULT_WORK_STATUS_NOTE);
        applicant.setCurrentSalary(DEFAULT_CURRENT_SALARY);
        applicant.setDesiredSalary(DEFAULT_DESIRED_SALARY);
        applicant.setMinSalary(DEFAULT_MIN_SALARY);
        applicant.setCurrentTotalComp(DEFAULT_CURRENT_TOTAL_COMP);
        applicant.setCurrentHourlyRate(DEFAULT_CURRENT_HOURLY_RATE);
        applicant.setDesiredHourlyRate(DEFAULT_DESIRED_HOURLY_RATE);
        applicant.setMinHourlyRate(DEFAULT_MIN_HOURLY_RATE);
        applicant.setCompensationComment(DEFAULT_COMPENSATION_COMMENT);
        applicant.setResume(DEFAULT_RESUME);
        applicant.setPto(DEFAULT_PTO);
        applicant.setHealth(DEFAULT_HEALTH);
        return applicant;
    }

    @Before
    public void initTest() {
        applicantSearchRepository.deleteAll();
        applicant = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicant.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplicant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testApplicant.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testApplicant.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testApplicant.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testApplicant.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testApplicant.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testApplicant.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testApplicant.getReferralSource()).isEqualTo(DEFAULT_REFERRAL_SOURCE);
        assertThat(testApplicant.getWorkStatusNote()).isEqualTo(DEFAULT_WORK_STATUS_NOTE);
        assertThat(testApplicant.getCurrentSalary()).isEqualTo(DEFAULT_CURRENT_SALARY);
        assertThat(testApplicant.getDesiredSalary()).isEqualTo(DEFAULT_DESIRED_SALARY);
        assertThat(testApplicant.getMinSalary()).isEqualTo(DEFAULT_MIN_SALARY);
        assertThat(testApplicant.getCurrentTotalComp()).isEqualTo(DEFAULT_CURRENT_TOTAL_COMP);
        assertThat(testApplicant.getCurrentHourlyRate()).isEqualTo(DEFAULT_CURRENT_HOURLY_RATE);
        assertThat(testApplicant.getDesiredHourlyRate()).isEqualTo(DEFAULT_DESIRED_HOURLY_RATE);
        assertThat(testApplicant.getMinHourlyRate()).isEqualTo(DEFAULT_MIN_HOURLY_RATE);
        assertThat(testApplicant.getCompensationComment()).isEqualTo(DEFAULT_COMPENSATION_COMMENT);
        assertThat(testApplicant.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testApplicant.isPto()).isEqualTo(DEFAULT_PTO);
        assertThat(testApplicant.isHealth()).isEqualTo(DEFAULT_HEALTH);

        // Validate the Applicant in Elasticsearch
        Applicant applicantEs = applicantSearchRepository.findOne(testApplicant.getId());
        assertThat(applicantEs).isEqualToIgnoringGivenFields(testApplicant);
    }

    @Test
    @Transactional
    public void createApplicantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant with an existing ID
        applicant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].referralSource").value(hasItem(DEFAULT_REFERRAL_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].workStatusNote").value(hasItem(DEFAULT_WORK_STATUS_NOTE.toString())))
            .andExpect(jsonPath("$.[*].currentSalary").value(hasItem(DEFAULT_CURRENT_SALARY.toString())))
            .andExpect(jsonPath("$.[*].desiredSalary").value(hasItem(DEFAULT_DESIRED_SALARY.toString())))
            .andExpect(jsonPath("$.[*].minSalary").value(hasItem(DEFAULT_MIN_SALARY.toString())))
            .andExpect(jsonPath("$.[*].currentTotalComp").value(hasItem(DEFAULT_CURRENT_TOTAL_COMP.toString())))
            .andExpect(jsonPath("$.[*].currentHourlyRate").value(hasItem(DEFAULT_CURRENT_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].desiredHourlyRate").value(hasItem(DEFAULT_DESIRED_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].minHourlyRate").value(hasItem(DEFAULT_MIN_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].compensationComment").value(hasItem(DEFAULT_COMPENSATION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())))
            .andExpect(jsonPath("$.[*].pto").value(hasItem(DEFAULT_PTO.booleanValue())))
            .andExpect(jsonPath("$.[*].health").value(hasItem(DEFAULT_HEALTH.booleanValue())));
    }

    @Test
    @Transactional
    public void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicant.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.referralSource").value(DEFAULT_REFERRAL_SOURCE.toString()))
            .andExpect(jsonPath("$.workStatusNote").value(DEFAULT_WORK_STATUS_NOTE.toString()))
            .andExpect(jsonPath("$.currentSalary").value(DEFAULT_CURRENT_SALARY.toString()))
            .andExpect(jsonPath("$.desiredSalary").value(DEFAULT_DESIRED_SALARY.toString()))
            .andExpect(jsonPath("$.minSalary").value(DEFAULT_MIN_SALARY.toString()))
            .andExpect(jsonPath("$.currentTotalComp").value(DEFAULT_CURRENT_TOTAL_COMP.toString()))
            .andExpect(jsonPath("$.currentHourlyRate").value(DEFAULT_CURRENT_HOURLY_RATE.toString()))
            .andExpect(jsonPath("$.desiredHourlyRate").value(DEFAULT_DESIRED_HOURLY_RATE.toString()))
            .andExpect(jsonPath("$.minHourlyRate").value(DEFAULT_MIN_HOURLY_RATE.toString()))
            .andExpect(jsonPath("$.compensationComment").value(DEFAULT_COMPENSATION_COMMENT.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()))
            .andExpect(jsonPath("$.pto").value(DEFAULT_PTO.booleanValue()))
            .andExpect(jsonPath("$.health").value(DEFAULT_HEALTH.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        applicantSearchRepository.save(applicant);
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        Applicant updatedApplicant = applicantRepository.findOne(applicant.getId());
        // Disconnect from session so that the updates on updatedApplicant are not directly saved in db
        em.detach(updatedApplicant);
        updatedApplicant.setFirstName(UPDATED_FIRST_NAME);
        updatedApplicant.setMiddleName(UPDATED_MIDDLE_NAME);
        updatedApplicant.setLastName(UPDATED_LAST_NAME);
        updatedApplicant.setTitle(UPDATED_TITLE);
        updatedApplicant.setEmail1(UPDATED_EMAIL_1);
        updatedApplicant.setHomePhone(UPDATED_HOME_PHONE);
        updatedApplicant.setCellPhone(UPDATED_CELL_PHONE);
        updatedApplicant.setIsDeleted(UPDATED_IS_DELETED);
        updatedApplicant.setWorkPhone(UPDATED_WORK_PHONE);
        updatedApplicant.setEmail2(UPDATED_EMAIL_2);
        updatedApplicant.setReferralSource(UPDATED_REFERRAL_SOURCE);
        updatedApplicant.setWorkStatusNote(UPDATED_WORK_STATUS_NOTE);
        updatedApplicant.setCurrentSalary(UPDATED_CURRENT_SALARY);
        updatedApplicant.setDesiredSalary(UPDATED_DESIRED_SALARY);
        updatedApplicant.setMinSalary(UPDATED_MIN_SALARY);
        updatedApplicant.setCurrentTotalComp(UPDATED_CURRENT_TOTAL_COMP);
        updatedApplicant.setCurrentHourlyRate(UPDATED_CURRENT_HOURLY_RATE);
        updatedApplicant.setDesiredHourlyRate(UPDATED_DESIRED_HOURLY_RATE);
        updatedApplicant.setMinHourlyRate(UPDATED_MIN_HOURLY_RATE);
        updatedApplicant.setCompensationComment(UPDATED_COMPENSATION_COMMENT);
        updatedApplicant.setResume(UPDATED_RESUME);
        updatedApplicant.setPto(UPDATED_PTO);
        updatedApplicant.setHealth(UPDATED_HEALTH);

        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicant)))
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicant.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testApplicant.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testApplicant.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testApplicant.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testApplicant.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testApplicant.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testApplicant.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testApplicant.getReferralSource()).isEqualTo(UPDATED_REFERRAL_SOURCE);
        assertThat(testApplicant.getWorkStatusNote()).isEqualTo(UPDATED_WORK_STATUS_NOTE);
        assertThat(testApplicant.getCurrentSalary()).isEqualTo(UPDATED_CURRENT_SALARY);
        assertThat(testApplicant.getDesiredSalary()).isEqualTo(UPDATED_DESIRED_SALARY);
        assertThat(testApplicant.getMinSalary()).isEqualTo(UPDATED_MIN_SALARY);
        assertThat(testApplicant.getCurrentTotalComp()).isEqualTo(UPDATED_CURRENT_TOTAL_COMP);
        assertThat(testApplicant.getCurrentHourlyRate()).isEqualTo(UPDATED_CURRENT_HOURLY_RATE);
        assertThat(testApplicant.getDesiredHourlyRate()).isEqualTo(UPDATED_DESIRED_HOURLY_RATE);
        assertThat(testApplicant.getMinHourlyRate()).isEqualTo(UPDATED_MIN_HOURLY_RATE);
        assertThat(testApplicant.getCompensationComment()).isEqualTo(UPDATED_COMPENSATION_COMMENT);
        assertThat(testApplicant.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testApplicant.isPto()).isEqualTo(UPDATED_PTO);
        assertThat(testApplicant.isHealth()).isEqualTo(UPDATED_HEALTH);

        // Validate the Applicant in Elasticsearch
        Applicant applicantEs = applicantSearchRepository.findOne(testApplicant.getId());
        assertThat(applicantEs).isEqualToIgnoringGivenFields(testApplicant);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Create the Applicant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        applicantSearchRepository.save(applicant);
        int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Get the applicant
        restApplicantMockMvc.perform(delete("/api/applicants/{id}", applicant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean applicantExistsInEs = applicantSearchRepository.exists(applicant.getId());
        assertThat(applicantExistsInEs).isFalse();

        // Validate the database is empty
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        applicantSearchRepository.save(applicant);

        // Search the applicant
        restApplicantMockMvc.perform(get("/api/_search/applicants?query=id:" + applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].referralSource").value(hasItem(DEFAULT_REFERRAL_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].workStatusNote").value(hasItem(DEFAULT_WORK_STATUS_NOTE.toString())))
            .andExpect(jsonPath("$.[*].currentSalary").value(hasItem(DEFAULT_CURRENT_SALARY.toString())))
            .andExpect(jsonPath("$.[*].desiredSalary").value(hasItem(DEFAULT_DESIRED_SALARY.toString())))
            .andExpect(jsonPath("$.[*].minSalary").value(hasItem(DEFAULT_MIN_SALARY.toString())))
            .andExpect(jsonPath("$.[*].currentTotalComp").value(hasItem(DEFAULT_CURRENT_TOTAL_COMP.toString())))
            .andExpect(jsonPath("$.[*].currentHourlyRate").value(hasItem(DEFAULT_CURRENT_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].desiredHourlyRate").value(hasItem(DEFAULT_DESIRED_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].minHourlyRate").value(hasItem(DEFAULT_MIN_HOURLY_RATE.toString())))
            .andExpect(jsonPath("$.[*].compensationComment").value(hasItem(DEFAULT_COMPENSATION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())))
            .andExpect(jsonPath("$.[*].pto").value(hasItem(DEFAULT_PTO.booleanValue())))
            .andExpect(jsonPath("$.[*].health").value(hasItem(DEFAULT_HEALTH.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicant.class);
        Applicant applicant1 = new Applicant();
        applicant1.setId(1L);
        Applicant applicant2 = new Applicant();
        applicant2.setId(applicant1.getId());
        assertThat(applicant1).isEqualTo(applicant2);
        applicant2.setId(2L);
        assertThat(applicant1).isNotEqualTo(applicant2);
        applicant1.setId(null);
        assertThat(applicant1).isNotEqualTo(applicant2);
    }
}
