package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PayInfo {

    private String id;
    private String flags;
    private String payType;
    private String name;
    private InheritedInfo inheritedInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InheritedInfo getInheritedInfo() {
        return inheritedInfo;
    }

    public void setInheritedInfo(InheritedInfo inheritedInfo) {
        this.inheritedInfo = inheritedInfo;
    }



}
