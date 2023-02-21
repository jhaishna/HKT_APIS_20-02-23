package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class BillingAccount implements Serializable {

    private String id;
    private String dealObj;
    private String name;
    private String flags;
    private List<Locale> locales;
    private List<PayInfo> payInfo;
    private List<NameInfo> nameInfo;
    private List<Profile> profiles;
    private String descr;
    private List<BalanceInfo> balInfo;
    private List<AccountInfo> acctInfo;
    private String endT;
    private List<BillInfo> billInfo;

    public List<Locale> getLocales() {
        return locales;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<BalanceInfo> getBalInfo() {
        return balInfo;
    }

    public void setBalInfo(List<BalanceInfo> balInfo) {
        this.balInfo = balInfo;
    }

    public String getEndT() {
        return endT;
    }

    public void setEndT(String endT) {
        this.endT = endT;
    }

    public List<BillInfo> getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(List<BillInfo> billInfo) {
        this.billInfo = billInfo;
    }

    public List<PayInfo> getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(List<PayInfo> payInfo) {
        this.payInfo = payInfo;
    }

    public List<NameInfo> getNameInfo() {
        return nameInfo;
    }

    public void setNameInfo(List<NameInfo> nameInfo) {
        this.nameInfo = nameInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public List<AccountInfo> getAcctInfo() {
        return acctInfo;
    }

    public void setAcctInfo(List<AccountInfo> acctInfo) {
        this.acctInfo = acctInfo;
    }

    public String getDealObj() {
        return dealObj;
    }

    public void setDealObj(String dealObj) {
        this.dealObj = dealObj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
