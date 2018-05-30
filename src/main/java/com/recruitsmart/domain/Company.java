package com.recruitsmart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.recruitsmart.domain.enumeration.CompanyType;

import com.recruitsmart.domain.enumeration.CompanyStatus;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "industry")
    private String industry;

    @Column(name = "revenue")
    private String revenue;

    @Column(name = "employee")
    private String employee;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_status")
    private CompanyStatus companyStatus;

//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<CompanyComment> companyComments = new HashSet<>();
//
//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Address> addresses = new HashSet<>();
//
//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<JobOrder> jobOrders = new HashSet<>();

//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<HiringContact> hiringContacts = new HashSet<>();
//
//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<CompanyInternalComment> companyInternalComments = new HashSet<>();
//
//    @OneToMany(mappedBy = "company")
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Activity> activities = new HashSet<>();
//
//    @ManyToMany
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    @JoinTable(name = "company_skill",
//               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
//               inverseJoinColumns = @JoinColumn(name="skills_id", referencedColumnName="id"))
//    private Set<Skill> skills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Integer isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public CompanyStatus getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

//    public Set<CompanyComment> getCompanyComments() {
//        return companyComments;
//    }
//
//    public void setCompanyComments(Set<CompanyComment> companyComments) {
//        this.companyComments = companyComments;
//    }
//
//    public Set<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Set<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    public Set<JobOrder> getJobOrders() {
//        return jobOrders;
//    }
//
//    public void setJobOrders(Set<JobOrder> jobOrders) {
//        this.jobOrders = jobOrders;
//    }
//
//    public Set<HiringContact> getHiringContacts() {
//        return hiringContacts;
//    }
//
//    public void setHiringContacts(Set<HiringContact> hiringContacts) {
//        this.hiringContacts = hiringContacts;
//    }
//
//    public Set<CompanyInternalComment> getCompanyInternalComments() {
//        return companyInternalComments;
//    }
//
//    public void setCompanyInternalComments(Set<CompanyInternalComment> companyInternalComments) {
//        this.companyInternalComments = companyInternalComments;
//    }
//
//    public Set<Activity> getActivities() {
//        return activities;
//    }
//
//    public void setActivities(Set<Activity> activities) {
//        this.activities = activities;
//    }
//
//    public Set<Skill> getSkills() {
//        return skills;
//    }
//
//    public void setSkills(Set<Skill> skills) {
//        this.skills = skills;
//    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", revenue='" + getRevenue() + "'" +
            ", employee='" + getEmployee() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", companyType='" + getCompanyType() + "'" +
            ", companyStatus='" + getCompanyStatus() + "'" +
            "}";
    }
}
