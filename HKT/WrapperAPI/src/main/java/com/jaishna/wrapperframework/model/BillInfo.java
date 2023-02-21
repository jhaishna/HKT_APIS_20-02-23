package com.jaishna.wrapperframework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class BillInfo implements Serializable {

    private String id;
    private String actgFutureDom;
    private String billinfoId;
    private String billWhen;
    private String payType;
    private List<PayInfo> payinfo;
    private List<BalanceInfo> BalInfo;
    private String intercomCcc;
    private String receiptAccountNo;
    private String paymentNo;
    private String ccc;
    private String billDivertLocation;
    private String billDivertRmnCyc;
    private String billWholdRmnCyc;
    private String itemizedBillRmnCyc;
    private List<Attribute> attributes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActgFutureDom() {
        return actgFutureDom;
    }

    public void setActgFutureDom(String actgFutureDom) {
        this.actgFutureDom = actgFutureDom;
    }

    public String getBillinfoId() {
        return billinfoId;
    }

    public void setBillinfoId(String billinfoId) {
        this.billinfoId = billinfoId;
    }

    public String getBillWhen() {
        return billWhen;
    }

    public void setBillWhen(String billWhen) {
        this.billWhen = billWhen;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<PayInfo> getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(List<PayInfo> payinfo) {
        this.payinfo = payinfo;
    }

    public List<BalanceInfo> getBalInfo() {
        return BalInfo;
    }

    public void setBalInfo(List<BalanceInfo> balInfo) {
        BalInfo = balInfo;
    }

    public String getIntercomCcc() {
        return intercomCcc;
    }

    public void setIntercomCcc(String intercomCcc) {
        this.intercomCcc = intercomCcc;
    }

    public String getReceiptAccountNo() {
        return receiptAccountNo;
    }

    public void setReceiptAccountNo(String receiptAccountNo) {
        this.receiptAccountNo = receiptAccountNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getBillDivertLocation() {
        return billDivertLocation;
    }

    public void setBillDivertLocation(String billDivertLocation) {
        this.billDivertLocation = billDivertLocation;
    }

    public String getBillDivertRmnCyc() {
        return billDivertRmnCyc;
    }

    public void setBillDivertRmnCyc(String billDivertRmnCyc) {
        this.billDivertRmnCyc = billDivertRmnCyc;
    }

    public String getBillWholdRmnCyc() {
        return billWholdRmnCyc;
    }

    public void setBillWholdRmnCyc(String billWholdRmnCyc) {
        this.billWholdRmnCyc = billWholdRmnCyc;
    }

    public String getItemizedBillRmnCyc() {
        return itemizedBillRmnCyc;
    }

    public void setItemizedBillRmnCyc(String itemizedBillRmnCyc) {
        this.itemizedBillRmnCyc = itemizedBillRmnCyc;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
