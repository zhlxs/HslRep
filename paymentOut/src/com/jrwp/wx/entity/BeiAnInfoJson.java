package com.jrwp.wx.entity;

public class BeiAnInfoJson {
	private Long id;
	private String sbkzjsjbh;
	private String jsjlb;
	private String jsjip;
	private String ckbh;
	private String kbywlb;
	private Integer deptid;
	private String controlip;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getControlip() {
		return controlip;
	}
	public void setControlip(String controlip) {
		this.controlip = controlip;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getSbkzjsjbh() {
		return sbkzjsjbh;
	}
	public void setSbkzjsjbh(String sbkzjsjbh) {
		this.sbkzjsjbh = sbkzjsjbh;
	}
	public String getJsjlb() {
		return jsjlb;
	}
	public void setJsjlb(String jsjlb) {
		this.jsjlb = jsjlb;
	}
	public String getJsjip() {
		return jsjip;
	}
	public void setJsjip(String jsjip) {
		this.jsjip = jsjip;
	}
	public String getCkbh() {
		return ckbh;
	}
	public void setCkbh(String ckbh) {
		this.ckbh = ckbh;
	}
	public String getKbywlb() {
		return kbywlb;
	}
	public void setKbywlb(String kbywlb) {
		this.kbywlb = kbywlb;
	}
}
