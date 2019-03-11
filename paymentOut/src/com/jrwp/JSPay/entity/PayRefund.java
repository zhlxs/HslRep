package com.jrwp.JSPay.entity;

import java.util.Date;

public class PayRefund {
    private Long id;
    private String ordernumber;
    private int refundstatus;
    private Double refundmoney;
    private Date createtime;
    private Date refundtime;
    private String refundnumber;
    private String outrefundno;
    private int isexport;
    private int isbill;
    private Long userid;
    private int refundtype;
    public String getOutrefundno() {
        return outrefundno;
    }

    public void setOutrefundno(String outrefundno) {
        this.outrefundno = outrefundno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public int getRefundstatus() {
        return refundstatus;
    }

    public void setRefundstatus(int refundstatus) {
        this.refundstatus = refundstatus;
    }

    public Double getRefundmoney() {
        return refundmoney;
    }

    public void setRefundmoney(Double refundmoney) {
        this.refundmoney = refundmoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getRefundtime() {
        return refundtime;
    }

    public void setRefundtime(Date refundtime) {
        this.refundtime = refundtime;
    }

    public String getRefundnumber() {
        return refundnumber;
    }

    public void setRefundnumber(String refundnumber) {
        this.refundnumber = refundnumber;
    }

    public int getIsexport() {
        return isexport;
    }

    public void setIsexport(int isexport) {
        this.isexport = isexport;
    }

    public int getIsbill() {
        return isbill;
    }

    public void setIsbill(int isbill) {
        this.isbill = isbill;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public int getRefundtype() {
        return refundtype;
    }

    public void setRefundtype(int refundtype) {
        this.refundtype = refundtype;
    }
}
