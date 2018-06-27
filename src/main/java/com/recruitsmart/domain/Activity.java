package com.recruitsmart.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priority")
    private String priority;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private ActivityAction action;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Applicant applicant;

    @ManyToOne
    private HiringContact hiringContact;

    @ManyToOne
    private JobOrder jobOrder;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("hiringContactActivity")
    private HiringContact hiringContactActivity;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JsonBackReference("applicantActivity")
    private Applicant applicantActivity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Applicant getApplicant() {
      return applicant;
    }

    public void setApplicant(Applicant applicant) {
      this.applicant = applicant;
    }

    public HiringContact getHiringContact() {
      return hiringContact;
    }

    public void setHiringContact(HiringContact hiringContact) {
      this.hiringContact = hiringContact;
    }

    public JobOrder getJobOrder() {
      return jobOrder;
    }

    public void setJobOrder(JobOrder jobOrder) {
      this.jobOrder = jobOrder;
    }

    public ActivityAction getAction() {
      return action;
    }

    public void setAction(ActivityAction action) {
      this.action = action;
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", priority='" + getPriority() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
