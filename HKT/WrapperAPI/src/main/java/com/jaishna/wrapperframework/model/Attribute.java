package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Attribute {

    private String status;
    private String locale;
    private String deliveryDescr1;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDeliveryDescr1() {
        return deliveryDescr1;
    }

    public void setDeliveryDescr1(String deliveryDescr1) {
        this.deliveryDescr1 = deliveryDescr1;
    }
}
