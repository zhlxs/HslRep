package com.jrwp.wx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class MachineData {
    private Integer id;
    private String bh;

    private Integer deptId;

    private String gmsfhm;

    private Integer zffs;

    private String ddbh;

    private Integer slyy;

    private BigDecimal zfje;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date slsj;

    private Integer zfzt;

    private Integer tkzt;

    private Integer machineType;
    private Integer tbzt;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String slh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public Integer getZffs() {
        return zffs;
    }

    public void setZffs(Integer zffs) {
        this.zffs = zffs;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public Integer getSlyy() {
        return slyy;
    }

    public void setSlyy(Integer slyy) {
        this.slyy = slyy;
    }

    public BigDecimal getZfje() {
        return zfje;
    }

    public void setZfje(BigDecimal zfje) {
        this.zfje = zfje;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public Integer getZfzt() {
        return zfzt;
    }

    public void setZfzt(Integer zfzt) {
        this.zfzt = zfzt;
    }

    public Integer getTkzt() {
        return tkzt;
    }

    public void setTkzt(Integer tkzt) {
        this.tkzt = tkzt;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }

    public Integer getTbzt() {
        return tbzt;
    }

    public void setTbzt(Integer tbzt) {
        this.tbzt = tbzt;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSlh() {
        return slh;
    }

    public void setSlh(String slh) {
        this.slh = slh;
    }
}