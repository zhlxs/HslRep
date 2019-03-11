package com.jrwp.JSPay.entity;

public class RefundDetails {
    private Long id;
    private Long refundid;
    private String workNumber;
    private Double refundMoney;
    private String workCode;
    private int isexport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefundid() {
        return refundid;
    }

    public void setRefundid(Long refundid) {
        this.refundid = refundid;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public int getIsexport() {
        return isexport;
    }

    public void setIsexport(int isexport) {
        this.isexport = isexport;
    }
}
