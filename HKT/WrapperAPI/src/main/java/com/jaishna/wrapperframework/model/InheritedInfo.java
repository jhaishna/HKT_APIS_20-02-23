package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class InheritedInfo {
    private List<InvoiceInfo> invInfo;
    private Account account;
    private CustomerCareInfo customerCareInfo;
    private BillInfo billingInfo;
    private List<Attribute> attributes;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<InvoiceInfo> getInvInfo() {
        return invInfo;
    }

    public BillInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public void setInvInfo(List<InvoiceInfo> invInfo) {
        this.invInfo = invInfo;
    }

    public CustomerCareInfo getCustomerCareInfo() {
        return customerCareInfo;
    }

    public void setCustomerCareInfo(CustomerCareInfo customerCareInfo) {
        this.customerCareInfo = customerCareInfo;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
