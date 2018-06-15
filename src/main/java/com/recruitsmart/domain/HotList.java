package com.recruitsmart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HotList.
 */
@Entity
@Table(name = "hot_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hotlist")
public class HotList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hot_list_type")
    private String hotListType;

    @ManyToOne
    private HotListBucket hotListBucket;

    @ManyToOne
    private JobOrder jobOrder;

    @ManyToOne
    private Applicant applicant;

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

    public HotListBucket getHotListBucket() {
        return hotListBucket;
    }

    public void setHotListBucket(HotListBucket hotListBucket) {
        this.hotListBucket = hotListBucket;
    }

    public JobOrder getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(JobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public String getHotListType() {
        return hotListType;
    }

    public void setHotListType(String hotListType) {
        this.hotListType = hotListType;
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
        HotList hotList = (HotList) o;
        if (hotList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hotList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HotList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
