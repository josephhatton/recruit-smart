package com.recruitsmart.domain.enumeration;

/**
 * The CompanyType enumeration.
 */
public enum CompanyType {
    Client("Client"),
    Prospect("Prospect"),
    Vendor("Vendor");

    private String companyType;
    private CompanyType(String brand) {
        this.companyType = brand;
    }

    @Override
    public String toString(){
        return companyType;
    }
}
