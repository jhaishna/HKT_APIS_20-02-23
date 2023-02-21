package com.jaishna.wrapperframework.model.subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Subscription {
    private String lastName;
    private String firstName;
    private String title;
    private String staffCcc;
    private String company;
    private String idType;
    private String localGprsOptOut;
    private String roamGprsOptOut;
    private String eGprsOptOut;
    private String city;
    private String iddRoamThreshold;
    private String iddRoamoutIn;
    private String jocCity;
    private String optOutDm;
    private String optOutEmail;
    private String optOutSms;
    private String optOutPccw;
    private String eppEid;
    private String eppRole;
    private String realMrtInd;
    private String country;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStaffCcc() {
        return staffCcc;
    }

    public void setStaffCcc(String staffCcc) {
        this.staffCcc = staffCcc;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getLocalGprsOptOut() {
        return localGprsOptOut;
    }

    public void setLocalGprsOptOut(String localGprsOptOut) {
        this.localGprsOptOut = localGprsOptOut;
    }

    public String getRoamGprsOptOut() {
        return roamGprsOptOut;
    }

    public void setRoamGprsOptOut(String roamGprsOptOut) {
        this.roamGprsOptOut = roamGprsOptOut;
    }

    public String geteGprsOptOut() {
        return eGprsOptOut;
    }

    public void seteGprsOptOut(String eGprsOptOut) {
        this.eGprsOptOut = eGprsOptOut;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIddRoamThreshold() {
        return iddRoamThreshold;
    }

    public void setIddRoamThreshold(String iddRoamThreshold) {
        this.iddRoamThreshold = iddRoamThreshold;
    }

    public String getIddRoamoutIn() {
        return iddRoamoutIn;
    }

    public void setIddRoamoutIn(String iddRoamoutIn) {
        this.iddRoamoutIn = iddRoamoutIn;
    }

    public String getJocCity() {
        return jocCity;
    }

    public void setJocCity(String jocCity) {
        this.jocCity = jocCity;
    }

    public String getOptOutDm() {
        return optOutDm;
    }

    public void setOptOutDm(String optOutDm) {
        this.optOutDm = optOutDm;
    }

    public String getOptOutEmail() {
        return optOutEmail;
    }

    public void setOptOutEmail(String optOutEmail) {
        this.optOutEmail = optOutEmail;
    }

    public String getOptOutSms() {
        return optOutSms;
    }

    public void setOptOutSms(String optOutSms) {
        this.optOutSms = optOutSms;
    }

    public String getOptOutPccw() {
        return optOutPccw;
    }

    public void setOptOutPccw(String optOutPccw) {
        this.optOutPccw = optOutPccw;
    }

    public String getEppEid() {
        return eppEid;
    }

    public void setEppEid(String eppEid) {
        this.eppEid = eppEid;
    }

    public String getEppRole() {
        return eppRole;
    }

    public void setEppRole(String eppRole) {
        this.eppRole = eppRole;
    }

    public String getRealMrtInd() {
        return realMrtInd;
    }

    public void setRealMrtInd(String realMrtInd) {
        this.realMrtInd = realMrtInd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
