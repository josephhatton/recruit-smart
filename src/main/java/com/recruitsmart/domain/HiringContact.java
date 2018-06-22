package com.recruitsmart.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * A HiringContact.
 */
@Entity
@Table(name = "hiring_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hiringcontact")
public class HiringContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "referral_source")
    private String referralSource;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    private Company company;

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "hiringContactComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("hiringContactComment")
    private Set<HiringContactComment> hiringContactComments = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "hiringContactInternalComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("hiringContactInternalComment")
    private Set<HiringContactInternalComment> hiringContactInternalComments = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "hiringContactJobOrder", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("hiringContactJobOrder")
    private Set<JobOrder> jobOrders = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "hiringContactActivity", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("hiringContactActivity")
    private Set<Activity> activities = new HashSet<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("applicantHiringContact")
    private Applicant applicantHiringContact;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<HiringContactComment> getHiringContactComments() {
        return hiringContactComments;
    }

    public void setHiringContactComments(Set<HiringContactComment> hiringContactComments) {
        this.hiringContactComments = hiringContactComments;
    }

    public Set<HiringContactInternalComment> getHiringContactInternalComments() {
        return hiringContactInternalComments;
    }

    public void setHiringContactInternalComments(Set<HiringContactInternalComment> hiringContactInternalComments) {
        this.hiringContactInternalComments = hiringContactInternalComments;
    }

    public Set<JobOrder> getJobOrders() {
        return jobOrders;
    }

    public void setJobOrders(Set<JobOrder> jobOrders) {
        this.jobOrders = jobOrders;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
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
        HiringContact hiringContact = (HiringContact) o;
        if (hiringContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hiringContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HiringContact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", referralSource='" + getReferralSource() + "'" +
            ", contactType='" + getContactType() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
