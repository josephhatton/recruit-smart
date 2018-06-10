package com.recruitsmart.domain.enumeration;

/**
 * The JobStatus enumeration.
 */
public enum JobStatus {
    OPEN("Open"),
    FILLED_BY_US("Filled by Us"),
    ON_HOLD("On Hold"),
    FILLED_BY_COMPETITION("Filled By Competition"),
    CANCELLED("Cancelled");

    private String jobStatus;
    private JobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    @Override
    public String toString(){
        return jobStatus;
    }

}
