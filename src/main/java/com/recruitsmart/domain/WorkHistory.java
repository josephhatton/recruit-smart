package com.recruitsmart.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WorkHistory.
 */
@Entity
@Table(name = "work_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workhistory")
public class WorkHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "starting_compensation")
    private Double startingCompensation;

    @Column(name = "ending_compensation")
    private Double endingCompensation;

    @Column(name = "compensation_type")
    private Integer compensationType;

    @Column(name = "supervisor")
    private String supervisor;

    @Column(name = "supervisor_title")
    private String supervisorTitle;

    @Column(name = "supervisor_phone")
    private String supervisorPhone;

    @Column(name = "duties")
    private String duties;

    @Column(name = "reason_for_leaving")
    private String reasonForLeaving;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("applicantHistory")
    private Applicant applicantHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getStartingCompensation() {
        return startingCompensation;
    }

    public void setStartingCompensation(Double startingCompensation) {
        this.startingCompensation = startingCompensation;
    }

    public Double getEndingCompensation() {
        return endingCompensation;
    }

    public void setEndingCompensation(Double endingCompensation) {
        this.endingCompensation = endingCompensation;
    }

    public Integer getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(Integer compensationType) {
        this.compensationType = compensationType;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisorTitle() {
        return supervisorTitle;
    }

    public void setSupervisorTitle(String supervisorTitle) {
        this.supervisorTitle = supervisorTitle;
    }

    public String getSupervisorPhone() {
        return supervisorPhone;
    }

    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
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
        WorkHistory workHistory = (WorkHistory) o;
        if (workHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkHistory{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", startingCompensation=" + getStartingCompensation() +
            ", endingCompensation=" + getEndingCompensation() +
            ", compensationType=" + getCompensationType() +
            ", supervisor='" + getSupervisor() + "'" +
            ", supervisorTitle='" + getSupervisorTitle() + "'" +
            ", supervisorPhone='" + getSupervisorPhone() + "'" +
            ", duties='" + getDuties() + "'" +
            ", reasonForLeaving='" + getReasonForLeaving() + "'" +
            "}";
    }
}
