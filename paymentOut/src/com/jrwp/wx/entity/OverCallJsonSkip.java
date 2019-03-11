package com.jrwp.wx.entity;

public class OverCallJsonSkip {
	private String ywckjsjip = "";
	private String sbkzjsjip = "";
	private String qhxxxlh = "";
	private String pdh = "";
	private String ywlb = "";
	private String sfzmhm = "";
	private String dlrsfzmhm = "";
	private String qhrxm = "";
	private String qhsj = "";
	private String rylb = "";
	
	
	public String getYwckjsjip() {
		return ywckjsjip;
	}
	public void setYwckjsjip(String ywckjsjip) {
		this.ywckjsjip = ywckjsjip;
	}
	public String getSbkzjsjip() {
		return sbkzjsjip;
	}
	public void setSbkzjsjip(String sbkzjsjip) {
		this.sbkzjsjip = sbkzjsjip;
	}
	public String getQhxxxlh() {
		return qhxxxlh;
	}
	public void setQhxxxlh(String qhxxxlh) {
		this.qhxxxlh = qhxxxlh;
	}
	public String getPdh() {
		return pdh;
	}
	public void setPdh(String pdh) {
		this.pdh = pdh;
	}
	public String getYwlb() {
		return ywlb;
	}
	public void setYwlb(String ywlb) {
		this.ywlb = ywlb;
	}
	public String getSfzmhm() {
		return sfzmhm;
	}
	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}
	public String getDlrsfzmhm() {
		return dlrsfzmhm;
	}
	public void setDlrsfzmhm(String dlrsfzmhm) {
		this.dlrsfzmhm = dlrsfzmhm;
	}
	public String getQhrxm() {
		return qhrxm;
	}
	public void setQhrxm(String qhrxm) {
		this.qhrxm = qhrxm;
	}
	public String getQhsj() {
		return qhsj;
	}
	public void setQhsj(String qhsj) {
		this.qhsj = qhsj.substring(0, qhsj.indexOf("."));
	}
	public String getRylb() {
		return rylb;
	}
	public void setRylb(String rylb) {
		this.rylb = rylb;
	}
}
