package com.recruitsmart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Applicant.
 */
@Entity
@Table(name = "applicant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "applicant")
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "referral_source")
    private String referralSource;

    @Column(name = "work_status_note")
    private String workStatusNote;

    @Column(name = "current_salary")
    private String currentSalary;

    @Column(name = "desired_salary")
    private String desiredSalary;

    @Column(name = "min_salary")
    private String minSalary;

    @Column(name = "current_total_comp")
    private String currentTotalComp;

    @Column(name = "current_hourly_rate")
    private String currentHourlyRate;

    @Column(name = "desired_hourly_rate")
    private String desiredHourlyRate;

    @Column(name = "min_hourly_rate")
    private String minHourlyRate;

    @Column(name = "compensation_comment")
    private String compensationComment;

    @Lob
    @Column(name = "resume")
    private String resume;

    @Column(name = "pto")
    private Boolean pto;

    @Column(name = "health")
    private Boolean health;

    @ManyToOne
    private ApplicantStatus applicantStatus;

    @ManyToOne
    private WorkStatus workStatus;

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantJobOrder", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantJobOrder")
    private Set<JobOrder> jobOrders = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantComment")
    private Set<ApplicantComment> applicantComments = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantInternalComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantInternalComment")
    private Set<ApplicantInternalComment> applicantInternalComments = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantAddress", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantAddress")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantSkill", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantSkill")
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantHistory", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantHistory")
    private Set<WorkHistory> workHistories = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantActivity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantActivity")
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "applicantHiringContact", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("applicantHiringContact")
    private Set<HiringContact> hiringContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public String getWorkStatusNote() {
        return workStatusNote;
    }

    public void setWorkStatusNote(String workStatusNote) {
        this.workStatusNote = workStatusNote;
    }

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    public String getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(String desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getCurrentTotalComp() {
        return currentTotalComp;
    }

    public void setCurrentTotalComp(String currentTotalComp) {
        this.currentTotalComp = currentTotalComp;
    }

    public String getCurrentHourlyRate() {
        return currentHourlyRate;
    }

    public void setCurrentHourlyRate(String currentHourlyRate) {
        this.currentHourlyRate = currentHourlyRate;
    }

    public String getDesiredHourlyRate() {
        return desiredHourlyRate;
    }

    public void setDesiredHourlyRate(String desiredHourlyRate) {
        this.desiredHourlyRate = desiredHourlyRate;
    }

    public String getMinHourlyRate() {
        return minHourlyRate;
    }

    public void setMinHourlyRate(String minHourlyRate) {
        this.minHourlyRate = minHourlyRate;
    }

    public String getCompensationComment() {
        return compensationComment;
    }

    public void setCompensationComment(String compensationComment) {
        this.compensationComment = compensationComment;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Boolean isPto() {
        return pto;
    }

    public void setPto(Boolean pto) {
        this.pto = pto;
    }

    public Boolean isHealth() {
        return health;
    }

    public void setHealth(Boolean health) {
        this.health = health;
    }


    public ApplicantStatus getApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(ApplicantStatus applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public Set<ApplicantComment> getApplicantComments() {
        return applicantComments;
    }

    public void setApplicantComments(Set<ApplicantComment> applicantComments) {
        this.applicantComments = applicantComments;
    }

    public Set<ApplicantInternalComment> getApplicantInternalComments() {
        return applicantInternalComments;
    }

    public void setApplicantInternalComments(Set<ApplicantInternalComment> applicantInternalComments) {
        this.applicantInternalComments = applicantInternalComments;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<WorkHistory> getWorkHistories() {
        return workHistories;
    }

    public void setWorkHistories(Set<WorkHistory> workHistories) {
        this.workHistories = workHistories;
    }

    public Set<Activity> getActivities() {
      return activities;
    }

    public void setActivities(Set<Activity> activities) {
      this.activities = activities;
    }

    public Set<JobOrder> getJobOrders() {
      return jobOrders;
    }

    public void setJobOrders(Set<JobOrder> jobOrders) {
      this.jobOrders = jobOrders;
    }

    public Set<HiringContact> getHiringContacts() {
      return hiringContacts;
    }

    public void setHiringContacts(Set<HiringContact> hiringContacts) {
      this.hiringContacts = hiringContacts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Applicant applicant = (Applicant) o;
        if (applicant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Applicant{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", title='" + getTitle() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", homePhone='" + getHomePhone() + "'" +
            ", cellPhone='" + getCellPhone() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", referralSource='" + getReferralSource() + "'" +
            ", workStatusNote='" + getWorkStatusNote() + "'" +
            ", currentSalary='" + getCurrentSalary() + "'" +
            ", desiredSalary='" + getDesiredSalary() + "'" +
            ", minSalary='" + getMinSalary() + "'" +
            ", currentTotalComp='" + getCurrentTotalComp() + "'" +
            ", currentHourlyRate='" + getCurrentHourlyRate() + "'" +
            ", desiredHourlyRate='" + getDesiredHourlyRate() + "'" +
            ", minHourlyRate='" + getMinHourlyRate() + "'" +
            ", compensationComment='" + getCompensationComment() + "'" +
            ", resume='" + getResume() + "'" +
            ", pto='" + isPto() + "'" +
            ", health='" + isHealth() + "'" +
            "}";
    }
}
