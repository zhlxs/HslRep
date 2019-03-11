package com.jrwp.follow.entity;

import java.util.Date;

public class PayConfig {

    private Integer id;

    private Integer deptid;

    private Integer configtype;

    private String appid;

    private String paymentpartnerid;

    private String paykey;

    private String paybody;

    private String paymentaccount;

    private String callbackurl;

    private String merchantprivatekey;

    private Date createtime;

    private Integer isstart;

    private String configname;

    private Integer isdel;

    private Integer ismark;

    private String markname;

    private String appsecret;

    private String mapiprivatekey;

    private Integer ispaytype;

    private Integer isfacilitator;
    private byte[] qrcode;
    private Integer isfollow;
    private String mainUrl;
    private String merchantprivatekey_java;
    private int issubscibe;//是否订阅
    private String targetappid;
    private String targetappsecret;

    public byte[] getQrcode() {
        return qrcode;
    }

    public int getIssubscibe() {
        return issubscibe;
    }

    public void setIssubscibe(int issubscibe) {
        this.issubscibe = issubscibe;
    }

    public String getTargetappid() {
        return targetappid;
    }

    public void setTargetappid(String targetappid) {
        this.targetappid = targetappid;
    }

    public String getTargetappsecret() {
        return targetappsecret;
    }

    public void setTargetappsecret(String targetappsecret) {
        this.targetappsecret = targetappsecret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getConfigtype() {
        return configtype;
    }

    public void setConfigtype(Integer configtype) {
        this.configtype = configtype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPaymentpartnerid() {
        return paymentpartnerid;
    }

    public void setPaymentpartnerid(String paymentpartnerid) {
        this.paymentpartnerid = paymentpartnerid;
    }

    public String getPaykey() {
        return paykey;
    }

    public void setPaykey(String paykey) {
        this.paykey = paykey;
    }

    public String getPaybody() {
        return paybody;
    }

    public void setPaybody(String paybody) {
        this.paybody = paybody;
    }

    public String getPaymentaccount() {
        return paymentaccount;
    }

    public void setPaymentaccount(String paymentaccount) {
        this.paymentaccount = paymentaccount;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getMerchantprivatekey() {
        return merchantprivatekey;
    }

    public void setMerchantprivatekey(String merchantprivatekey) {
        this.merchantprivatekey = merchantprivatekey;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsstart() {
        return isstart;
    }

    public void setIsstart(Integer isstart) {
        this.isstart = isstart;
    }

    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getIsmark() {
        return ismark;
    }

    public void setIsmark(Integer ismark) {
        this.ismark = ismark;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getMapiprivatekey() {
        return mapiprivatekey;
    }

    public void setMapiprivatekey(String mapiprivatekey) {
        this.mapiprivatekey = mapiprivatekey;
    }

    public Integer getIspaytype() {
        return ispaytype;
    }

    public void setIspaytype(Integer ispaytype) {
        this.ispaytype = ispaytype;
    }

    public Integer getIsfacilitator() {
        return isfacilitator;
    }

    public void setIsfacilitator(Integer isfacilitator) {
        this.isfacilitator = isfacilitator;
    }

    public byte[] getQccode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }

    public Integer getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(Integer isfollow) {
        this.isfollow = isfollow;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getMerchantprivatekey_java() {
        return merchantprivatekey_java;
    }

    public void setMerchantprivatekey_java(String merchantprivatekey_java) {
        this.merchantprivatekey_java = merchantprivatekey_java;
    }
}