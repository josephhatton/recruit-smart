package com.recruitsmart.domain.enumeration;

/**
 * The CompanyStatus enumeration.
 */
public enum CompanyStatus {
    NO_CONTRACT("No Contract"),
    MSA("MSA"),
    SA("SA"),
    SOW("SOW");

    private String companyStatus;
    private CompanyStatus(String status) {
        this.companyStatus = status;
    }

    @Override
    public String toString(){
        return companyStatus;
    }
}
