package com.jrwp.appr.entity;

import java.util.ArrayList;
import java.util.List;

public class AppraiseDetail {
    private Long id;
    private Long appraisemodelid;
    private String appraisename;
    private int appraisevalue;
    private String iconpath;
    private byte[] icon;
    private int isdefault;
    private int iswechat;
    private int iswarning;
    private int isexport;
    private List<String> hotwords = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppraisemodelid() {
        return appraisemodelid;
    }

    public void setAppraisemodelid(Long appraisemodelid) {
        this.appraisemodelid = appraisemodelid;
    }

    public String getAppraisename() {
        return appraisename;
    }

    public void setAppraisename(String appraisename) {
        this.appraisename = appraisename;
    }

    public int getAppraisevalue() {
        return appraisevalue;
    }

    public void setAppraisevalue(int appraisevalue) {
        this.appraisevalue = appraisevalue;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public int getIswechat() {
        return iswechat;
    }

    public void setIswechat(int iswechat) {
        this.iswechat = iswechat;
    }

    public int getIswarning() {
        return iswarning;
    }

    public void setIswarning(int iswarning) {
        this.iswarning = iswarning;
    }

    public int getIsexport() {
        return isexport;
    }

    public void setIsexport(int isexport) {
        this.isexport = isexport;
    }

    public List<String> getHotwords() {
        return hotwords;
    }

    public void setHotwords(List<String> hotwords) {
        this.hotwords = hotwords;
    }
}
