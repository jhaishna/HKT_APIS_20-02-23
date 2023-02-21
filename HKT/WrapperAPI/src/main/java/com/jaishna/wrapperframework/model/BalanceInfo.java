package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class BalanceInfo {

    private String name;
    private String id;
    private String currency;
    private String businessType;
    private List<BillInfo> billInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<BillInfo> getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(List<BillInfo> billInfo) {
        this.billInfo = billInfo;
    }
}
