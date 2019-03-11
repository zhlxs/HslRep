package com.jrwp.JSPay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RefundApply {

    private Integer id;
    private String ordernumber;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private Integer applyuser;
    private String applyusername;
    private int checkstatus;
    private String status;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checktime;
    private Integer checkuser;
    private String checkusername;
    private String refundreson;
    private Double paymoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getApplyuser() {
        return applyuser;
    }

    public void setApplyuser(Integer applyuser) {
        this.applyuser = applyuser;
    }

    public int getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(int checkstatus) {
        this.checkstatus = checkstatus;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public Integer getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(Integer checkuser) {
        this.checkuser = checkuser;
    }

    public String getRefundreson() {
        return refundreson;
    }

    public void setRefundreson(String refundreson) {
        this.refundreson = refundreson;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

    public Double getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(Double paymoney) {
        this.paymoney = paymoney;
    }

    public String getApplyusername() {
        return applyusername;
    }

    public void setApplyusername(String applyusername) {
        this.applyusername = applyusername;
    }

    public String getStatus() {
        //审核状态 0 待审核 1 审核通过 2 审核不通过 3 取消 4 成功
        if (checkstatus == 0) {
            return "待审核";
        } else if (checkstatus == 1) {
            return "审核通过";
        } else if (checkstatus == 2) {
            return "审核不通过";
        } else if (checkstatus == 3) {
            return "取消";
        } else {
            return "成功";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}