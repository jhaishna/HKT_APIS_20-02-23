package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Account {
    private String customerSegment;
    private String payMethodKey;
    private String creditStatus;

    public String getCustomerSegment() {
        return customerSegment;
    }

    public String getPayMethodKey() {
        return payMethodKey;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    public void setPayMethodKey(String payMethodKey) {
        this.payMethodKey = payMethodKey;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }
}
