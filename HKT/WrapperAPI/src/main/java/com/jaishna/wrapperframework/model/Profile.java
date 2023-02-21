package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Profile {
    private String id;
    private InheritedInfo inheritedInfo;
    private BillInfo billingInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InheritedInfo getInheritedInfo() {
        return inheritedInfo;
    }

    public void setInheritedInfo(InheritedInfo inheritedInfo) {
        this.inheritedInfo = inheritedInfo;
    }

    public BillInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillInfo billingInfo) {
        this.billingInfo = billingInfo;
    }
}
