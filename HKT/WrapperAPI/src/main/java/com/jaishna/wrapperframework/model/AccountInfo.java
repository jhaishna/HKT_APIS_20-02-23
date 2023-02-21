package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class AccountInfo {

    private String accountNo;
    private String id;
    private String glSegment;
    private String actgType;
    private String currency;
    private String businessType;
    private List<BalanceInfo> balInfo;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlSegment() {
        return glSegment;
    }

    public void setGlSegment(String glSegment) {
        this.glSegment = glSegment;
    }

    public String getActgType() {
        return actgType;
    }

    public void setActgType(String actgType) {
        this.actgType = actgType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public List<BalanceInfo> getBalInfo() {
        return balInfo;
    }

    public void setBalInfo(List<BalanceInfo> balInfo) {
        this.balInfo = balInfo;
    }
}
