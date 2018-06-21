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

import com.recruitsmart.domain.enumeration.JobType;

import com.recruitsmart.domain.enumeration.JobStatus;

/**
 * A JobOrder.
 */
@Entity
@Table(name = "job_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "joborder")
public class JobOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private String salary;

    @Column(name = "hourly")
    private String hourly;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "jobOrderComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("jobOrderComment")
    private Set<JobOrderComment> jobOrderComments = new HashSet<>();

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "jobOrderInternalComment", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("jobOrderInternalComment")
    private Set<JobOrderInternalComment> jobOrderInternalComments = new HashSet<>();

    @ManyToOne
    private Company company;

    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, mappedBy = "jobOrderSkill", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("jobOrderSkill")
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("hiringContactJobOrder")
    private HiringContact hiringContactJobOrder;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("applicantJobOrder")
    private Applicant applicantJobOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getHourly() {
        return hourly;
    }

    public void setHourly(String hourly) {
        this.hourly = hourly;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Set<JobOrderComment> getJobOrderComments() {
        return jobOrderComments;
    }

    public void setJobOrderComments(Set<JobOrderComment> jobOrderComments) {
        this.jobOrderComments = jobOrderComments;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<JobOrderInternalComment> getJobOrderInternalComments() {
        return jobOrderInternalComments;
    }

    public void setJobOrderInternalComments(Set<JobOrderInternalComment> jobOrderInternalComments) {
        this.jobOrderInternalComments = jobOrderInternalComments;
    }

    public HiringContact getHiringContactJobOrder() {
        return hiringContactJobOrder;
    }

    public void setHiringContactJobOrder(HiringContact hiringContactJobOrder) {
        this.hiringContactJobOrder = hiringContactJobOrder;
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
        JobOrder jobOrder = (JobOrder) o;
        if (jobOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobOrder{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", duration='" + getDuration() + "'" +
            ", description='" + getDescription() + "'" +
            ", salary='" + getSalary() + "'" +
            ", hourly='" + getHourly() + "'" +
            ", jobType='" + getJobType() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            "}";
    }
}
