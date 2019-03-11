package com.jrwp.appr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AppraiseModel {
    private Long id;
    private String modelname;
    private Long createuser;
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private int outtime;
    private int isautoappraise;
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
    private int iswechat;
    private int iswarn;
    private int isexport;
    private int istest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public Long getCreateuser() {
        return createuser;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getOuttime() {
        return outtime;
    }

    public void setOuttime(int outtime) {
        this.outtime = outtime;
    }

    public int getIsautoappraise() {
        return isautoappraise;
    }

    public void setIsautoappraise(int isautoappraise) {
        this.isautoappraise = isautoappraise;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getIswechat() {
        return iswechat;
    }

    public void setIswechat(int iswechat) {
        this.iswechat = iswechat;
    }

    public int getIswarn() {
        return iswarn;
    }

    public void setIswarn(int iswarn) {
        this.iswarn = iswarn;
    }

    public int getIsexport() {
        return isexport;
    }

    public void setIsexport(int isexport) {
        this.isexport = isexport;
    }

    public int getIstest() {
        return istest;
    }

    public void setIstest(int istest) {
        this.istest = istest;
    }
}
