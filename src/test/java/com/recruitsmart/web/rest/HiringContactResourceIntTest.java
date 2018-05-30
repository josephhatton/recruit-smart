package com.recruitsmart.web.rest;

import com.recruitsmart.RecruitsmartApp;

import com.recruitsmart.domain.HiringContact;
import com.recruitsmart.repository.HiringContactRepository;
import com.recruitsmart.repository.search.HiringContactSearchRepository;
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
 * Test class for the HiringContactResource REST controller.
 *
 * @see HiringContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitsmartApp.class)
public class HiringContactResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRAL_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERRAL_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private HiringContactRepository hiringContactRepository;

    @Autowired
    private HiringContactSearchRepository hiringContactSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHiringContactMockMvc;

    private HiringContact hiringContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HiringContactResource hiringContactResource = new HiringContactResource(hiringContactRepository, hiringContactSearchRepository);
        this.restHiringContactMockMvc = MockMvcBuilders.standaloneSetup(hiringContactResource)
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
    public static HiringContact createEntity(EntityManager em) {
        HiringContact hiringContact = new HiringContact();
        hiringContact.setFirstName(DEFAULT_FIRST_NAME);
        hiringContact.setLastName(DEFAULT_LAST_NAME);
        hiringContact.setPhone1(DEFAULT_PHONE_1);
        hiringContact.setPhone2(DEFAULT_PHONE_2);
        hiringContact.setEmail1(DEFAULT_EMAIL_1);
        hiringContact.setJobTitle(DEFAULT_JOB_TITLE);
        hiringContact.setReferralSource(DEFAULT_REFERRAL_SOURCE);
        hiringContact.setContactType(DEFAULT_CONTACT_TYPE);
        hiringContact.setEmail2(DEFAULT_EMAIL_2);
        hiringContact.setMiddleName(DEFAULT_MIDDLE_NAME);
        hiringContact.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return hiringContact;
    }

    @Before
    public void initTest() {
        hiringContactSearchRepository.deleteAll();
        hiringContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createHiringContact() throws Exception {
        int databaseSizeBeforeCreate = hiringContactRepository.findAll().size();

        // Create the HiringContact
        restHiringContactMockMvc.perform(post("/api/hiring-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContact)))
            .andExpect(status().isCreated());

        // Validate the HiringContact in the database
        List<HiringContact> hiringContactList = hiringContactRepository.findAll();
        assertThat(hiringContactList).hasSize(databaseSizeBeforeCreate + 1);
        HiringContact testHiringContact = hiringContactList.get(hiringContactList.size() - 1);
        assertThat(testHiringContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testHiringContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testHiringContact.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testHiringContact.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testHiringContact.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testHiringContact.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testHiringContact.getReferralSource()).isEqualTo(DEFAULT_REFERRAL_SOURCE);
        assertThat(testHiringContact.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testHiringContact.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testHiringContact.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testHiringContact.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the HiringContact in Elasticsearch
        HiringContact hiringContactEs = hiringContactSearchRepository.findOne(testHiringContact.getId());
        assertThat(hiringContactEs).isEqualToIgnoringGivenFields(testHiringContact);
    }

    @Test
    @Transactional
    public void createHiringContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hiringContactRepository.findAll().size();

        // Create the HiringContact with an existing ID
        hiringContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHiringContactMockMvc.perform(post("/api/hiring-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContact)))
            .andExpect(status().isBadRequest());

        // Validate the HiringContact in the database
        List<HiringContact> hiringContactList = hiringContactRepository.findAll();
        assertThat(hiringContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHiringContacts() throws Exception {
        // Initialize the database
        hiringContactRepository.saveAndFlush(hiringContact);

        // Get all the hiringContactList
        restHiringContactMockMvc.perform(get("/api/hiring-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].referralSource").value(hasItem(DEFAULT_REFERRAL_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void getHiringContact() throws Exception {
        // Initialize the database
        hiringContactRepository.saveAndFlush(hiringContact);

        // Get the hiringContact
        restHiringContactMockMvc.perform(get("/api/hiring-contacts/{id}", hiringContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hiringContact.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.referralSource").value(DEFAULT_REFERRAL_SOURCE.toString()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHiringContact() throws Exception {
        // Get the hiringContact
        restHiringContactMockMvc.perform(get("/api/hiring-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHiringContact() throws Exception {
        // Initialize the database
        hiringContactRepository.saveAndFlush(hiringContact);
        hiringContactSearchRepository.save(hiringContact);
        int databaseSizeBeforeUpdate = hiringContactRepository.findAll().size();

        // Update the hiringContact
        HiringContact updatedHiringContact = hiringContactRepository.findOne(hiringContact.getId());
        // Disconnect from session so that the updates on updatedHiringContact are not directly saved in db
        em.detach(updatedHiringContact);
        updatedHiringContact.setFirstName(UPDATED_FIRST_NAME);
        updatedHiringContact.setLastName(UPDATED_LAST_NAME);
        updatedHiringContact.setPhone1(UPDATED_PHONE_1);
        updatedHiringContact.setPhone2(UPDATED_PHONE_2);
        updatedHiringContact.setEmail1(UPDATED_EMAIL_1);
        updatedHiringContact.setJobTitle(UPDATED_JOB_TITLE);
        updatedHiringContact.setReferralSource(UPDATED_REFERRAL_SOURCE);
        updatedHiringContact.setContactType(UPDATED_CONTACT_TYPE);
        updatedHiringContact.setEmail2(UPDATED_EMAIL_2);
        updatedHiringContact.setMiddleName(UPDATED_MIDDLE_NAME);
        updatedHiringContact.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restHiringContactMockMvc.perform(put("/api/hiring-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHiringContact)))
            .andExpect(status().isOk());

        // Validate the HiringContact in the database
        List<HiringContact> hiringContactList = hiringContactRepository.findAll();
        assertThat(hiringContactList).hasSize(databaseSizeBeforeUpdate);
        HiringContact testHiringContact = hiringContactList.get(hiringContactList.size() - 1);
        assertThat(testHiringContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testHiringContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testHiringContact.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testHiringContact.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testHiringContact.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testHiringContact.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testHiringContact.getReferralSource()).isEqualTo(UPDATED_REFERRAL_SOURCE);
        assertThat(testHiringContact.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testHiringContact.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testHiringContact.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testHiringContact.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the HiringContact in Elasticsearch
        HiringContact hiringContactEs = hiringContactSearchRepository.findOne(testHiringContact.getId());
        assertThat(hiringContactEs).isEqualToIgnoringGivenFields(testHiringContact);
    }

    @Test
    @Transactional
    public void updateNonExistingHiringContact() throws Exception {
        int databaseSizeBeforeUpdate = hiringContactRepository.findAll().size();

        // Create the HiringContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHiringContactMockMvc.perform(put("/api/hiring-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hiringContact)))
            .andExpect(status().isCreated());

        // Validate the HiringContact in the database
        List<HiringContact> hiringContactList = hiringContactRepository.findAll();
        assertThat(hiringContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHiringContact() throws Exception {
        // Initialize the database
        hiringContactRepository.saveAndFlush(hiringContact);
        hiringContactSearchRepository.save(hiringContact);
        int databaseSizeBeforeDelete = hiringContactRepository.findAll().size();

        // Get the hiringContact
        restHiringContactMockMvc.perform(delete("/api/hiring-contacts/{id}", hiringContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hiringContactExistsInEs = hiringContactSearchRepository.exists(hiringContact.getId());
        assertThat(hiringContactExistsInEs).isFalse();

        // Validate the database is empty
        List<HiringContact> hiringContactList = hiringContactRepository.findAll();
        assertThat(hiringContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHiringContact() throws Exception {
        // Initialize the database
        hiringContactRepository.saveAndFlush(hiringContact);
        hiringContactSearchRepository.save(hiringContact);

        // Search the hiringContact
        restHiringContactMockMvc.perform(get("/api/_search/hiring-contacts?query=id:" + hiringContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].referralSource").value(hasItem(DEFAULT_REFERRAL_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HiringContact.class);
        HiringContact hiringContact1 = new HiringContact();
        hiringContact1.setId(1L);
        HiringContact hiringContact2 = new HiringContact();
        hiringContact2.setId(hiringContact1.getId());
        assertThat(hiringContact1).isEqualTo(hiringContact2);
        hiringContact2.setId(2L);
        assertThat(hiringContact1).isNotEqualTo(hiringContact2);
        hiringContact1.setId(null);
        assertThat(hiringContact1).isNotEqualTo(hiringContact2);
    }
}
