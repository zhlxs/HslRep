package com.jrwp.wx.entity;

public class SupplementRecord {
	private Long id;
	private String ywckjsjip;
	private String sbkzjsjip;
	private String kbywlb;
	private int iswrite;
	private String sequenceinfoid;
	private Integer deptid;

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getKbywlb() {
		return kbywlb;
	}

	public void setKbywlb(String kbywlb) {
		this.kbywlb = kbywlb;
	}

	public int getIswrite() {
		return iswrite;
	}

	public void setIswrite(int iswrite) {
		this.iswrite = iswrite;
	}

	public String getSequenceinfoid() {
		return sequenceinfoid;
	}

	public void setSequenceinfoid(String sequenceinfoid) {
		this.sequenceinfoid = sequenceinfoid;
	}

}
