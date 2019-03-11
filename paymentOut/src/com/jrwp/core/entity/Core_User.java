package com.jrwp.core.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrwp.payMent.entity.PayOrders;

/**
 * 用户表
 */
public class Core_User {
    /**
     * 用户ID
     */
    @NotNull(message = "{Core_User.id.notnull}")
    private Long id;
    /**
     * 用户名 用户名不能为空 用户名不能超过20字符
     */
    @Size(min = 1, max = 20, message = "{Core_User.userName.length}")
    private String userName;
    /**
     * 密码 密码不能为空 密码不能超过32字符
     */
    @Size(min = 1, max = 32, message = "{Core_User.passWord.length}")
    private String password;
    /**
     * 民警姓名 姓名不能为空 姓名不能超过20字符
     */
    @Size(min = 1, max = 32, message = "{Core_User.fullName.length}")
    private String fullName;

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    /**
     * 所属部门 请选择所属部门
     */
    private Core_Dept dept;

    // 部门id
    private Long deptId;

    /**
     * 联系电话 联系电话不能超过11字符
     */
    @Size(min = 1, max = 11, message = "{Core_User.phone.length}")
    private String phone;
    /**
     * 启用状态 0：停用 1：启用
     */
    private boolean isStart;
    /**
     * 是否系统级 0：非系统 1：系统
     */
    private Boolean isSys;
    /**
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 是否已删除 0：未删除 1：已删除
     */
    private boolean isDel;
    /**
     * 最后登录时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /**
     * 最后登录IP 最后登录IP不能超过20字符
     */
    @Size(min = 1, max = 20, message = "{Core_User.lastLoginIP.length}")
    private String lastLoginIP;
    /**
     * 用户角色列表
     */
    private List<Core_Role> roles;
    /*
     * 支付编码
     *
     * */
    private String payCode;
    /*
     * 创建用户
     *
     * */
    private String createBy;
    /*
     * 机器ip
     *
     * */
    private String machineIp;
    /*
     *
     * 支付类型,1.支付宝，2.微信，3.两种都开通
     *
     * */
    private Long payType;
    /*
     * 机器码
     *
     * */
    private String machineCode;

    private String pcCode;

    //用户对应多条订单
    private List<PayOrders> orderList;
    //0 普通用户 1 部门管理员 2 超级管理员
    private Integer isAdmin;

    /**
     * 商品描述
     * @return
     */
    @Size(min = 1,max = 255,message = "{Core_User.goodsremark.length}")
    private String goodsremark;

    public String getGoodsremark() {
        return goodsremark;
    }

    public void setGoodsremark(String goodsremark) {
        this.goodsremark = goodsremark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setDel(boolean isDel) {
        this.isDel = isDel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Core_Dept getDept() {
        return dept;
    }

    public void setDept(Core_Dept dept) {
        this.dept = dept;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public Boolean getIsSys() {
        return isSys;
    }

    public void setIsSys(Boolean isSys) {
        this.isSys = isSys;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public List<Core_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Core_Role> roles) {
        this.roles = roles;
    }

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }

    public List<PayOrders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<PayOrders> orderList) {
        this.orderList = orderList;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
