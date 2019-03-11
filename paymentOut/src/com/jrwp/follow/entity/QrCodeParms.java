package com.jrwp.follow.entity;

import java.util.Date;

public class QrCodeParms {
    //id=16&workID=65527&machineID=1qwe&machineType=9&payType=2&deptCode=910000000000&orderDescribe=我的测试支付&payMoney=0.01&s=zfcs
    // &userID=144&orderid=16&apprseq=2&apprtype=2&evluateID=1
    private Long id;//sync的主键
    private String workID;//workid
    private String machineID;//机器码
    private Integer machineType;//设备类型
    private Integer payType;//支付方式
    private String deptCode;//部门code  支付code
    private String orderDescribe;//商品描述
    private Double payMoney;//支付金额
    private String s;//是否需要存储code
    private Long userID;//用户id
    private Integer apprseq;//评价顺序：1先支付后评价（无需调接口）；2先评价后支付（调接口）
    private Integer apprtype;//评价方式
    private Long evluateID;//模板id
    private Long orderid;//订单id

    //一下是辅助的字段
    private Integer tid;
    private Date createtime;
    private int paystatus;
    private String ordernumber;
    private Date updatetime;
    private String openid;
    private String sub_openid;
    private Long payid;
    private Long configid;
    private Long deptid;

    public Long getPayid() {
        return payid;
    }

    public void setPayid(Long payid) {
        this.payid = payid;
    }

    public Long getConfigid() {
        return configid;
    }

    public void setConfigid(Long configid) {
        this.configid = configid;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getOrderDescribe() {
        return orderDescribe;
    }

    public void setOrderDescribe(String orderDescribe) {
        this.orderDescribe = orderDescribe;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getApprseq() {
        return apprseq;
    }

    public void setApprseq(Integer apprseq) {
        this.apprseq = apprseq;
    }

    public Integer getApprtype() {
        return apprtype;
    }

    public void setApprtype(Integer apprtype) {
        this.apprtype = apprtype;
    }

    public Long getEvluateID() {
        return evluateID;
    }

    public void setEvluateID(Long evluateID) {
        this.evluateID = evluateID;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }
}
