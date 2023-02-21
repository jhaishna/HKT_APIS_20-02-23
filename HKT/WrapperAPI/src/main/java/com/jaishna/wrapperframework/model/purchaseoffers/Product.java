package com.jaishna.wrapperframework.model.purchaseoffers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Product {

    private Poid productObj;
    private String cycleDiscount;
    private String cycleEndDetails;
    private String cycleEndt;
    private String cycleStartDetails;
    private String cycleStartt;
    private String purchaseDiscount;
    private String purchaseEndDetails;
    private String purchaseEndt;
    private String purchaseStartDetails;
    private String purchaseStartt;
    private String quantity;
    private String status;
    private String statusFlags;
    private String usageDiscount;
    private String usageEndDetails;
    private String usageEndt;
    private String usageStartDetails;
    private String usageStartt;
    private String usageStartUnit;
    private String usageStartOffset;
    private String descr;

    public Poid getProductObj() {
        return productObj;
    }

    public void setProductObj(Poid productObj) {
        this.productObj = productObj;
    }

    public String getCycleDiscount() {
        return cycleDiscount;
    }

    public void setCycleDiscount(String cycleDiscount) {
        this.cycleDiscount = cycleDiscount;
    }

    public String getCycleEndDetails() {
        return cycleEndDetails;
    }

    public void setCycleEndDetails(String cycleEndDetails) {
        this.cycleEndDetails = cycleEndDetails;
    }

    public String getCycleEndt() {
        return cycleEndt;
    }

    public void setCycleEndt(String cycleEndt) {
        this.cycleEndt = cycleEndt;
    }

    public String getCycleStartDetails() {
        return cycleStartDetails;
    }

    public void setCycleStartDetails(String cycleStartDetails) {
        this.cycleStartDetails = cycleStartDetails;
    }

    public String getCycleStartt() {
        return cycleStartt;
    }

    public void setCycleStartt(String cycleStartt) {
        this.cycleStartt = cycleStartt;
    }

    public String getPurchaseDiscount() {
        return purchaseDiscount;
    }

    public void setPurchaseDiscount(String purchaseDiscount) {
        this.purchaseDiscount = purchaseDiscount;
    }

    public String getPurchaseEndDetails() {
        return purchaseEndDetails;
    }

    public void setPurchaseEndDetails(String purchaseEndDetails) {
        this.purchaseEndDetails = purchaseEndDetails;
    }

    public String getPurchaseStartDetails() {
        return purchaseStartDetails;
    }

    public void setPurchaseStartDetails(String purchaseStartDetails) {
        this.purchaseStartDetails = purchaseStartDetails;
    }

    public String getPurchaseStartt() {
        return purchaseStartt;
    }

    public void setPurchaseStartt(String purchaseStartt) {
        this.purchaseStartt = purchaseStartt;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(String statusFlags) {
        this.statusFlags = statusFlags;
    }

    public String getUsageDiscount() {
        return usageDiscount;
    }

    public void setUsageDiscount(String usageDiscount) {
        this.usageDiscount = usageDiscount;
    }

    public String getUsageEndDetails() {
        return usageEndDetails;
    }

    public void setUsageEndDetails(String usageEndDetails) {
        this.usageEndDetails = usageEndDetails;
    }

    public String getUsageEndt() {
        return usageEndt;
    }

    public void setUsageEndt(String usageEndt) {
        this.usageEndt = usageEndt;
    }

    public String getUsageStartDetails() {
        return usageStartDetails;
    }

    public void setUsageStartDetails(String usageStartDetails) {
        this.usageStartDetails = usageStartDetails;
    }

    public String getUsageStartt() {
        return usageStartt;
    }

    public void setUsageStartt(String usageStartt) {
        this.usageStartt = usageStartt;
    }

    public String getUsageStartUnit() {
        return usageStartUnit;
    }

    public void setUsageStartUnit(String usageStartUnit) {
        this.usageStartUnit = usageStartUnit;
    }

    public String getUsageStartOffset() {
        return usageStartOffset;
    }

    public void setUsageStartOffset(String usageStartOffset) {
        this.usageStartOffset = usageStartOffset;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPurchaseEndt() {
        return purchaseEndt;
    }

    public void setPurchaseEndt(String purchaseEndt) {
        this.purchaseEndt = purchaseEndt;
    }
}
