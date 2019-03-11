package com.jrwp.wx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Visitor {
    private String state="0";//0:等待，1：叫号状态
    private String name;
    private String queueNumber;
    private String windowNumber;
    private Integer deptid;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getWindowNumber() {
        return windowNumber;
    }

    public void setWindowNumber(String windowNumber) {
        this.windowNumber = windowNumber;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}