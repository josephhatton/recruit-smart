package com.recruitsmart.domain.enumeration;

/**
 * The JobType enumeration.
 */
public enum JobType {
    DIRECT_HIRE ("Direct Hire"),
    CONTRACT_TO_HIRE("Contract to Hire"),
    CONTRACT ("Contract");

    private String jobType;
    private JobType(String brand) {
        this.jobType = brand;
    }

    @Override
    public String toString(){
        return jobType;
    }

}
