package com.jrwp.payMent.entity;

import java.io.Serializable;
import java.util.Date;

public class DeptMacAuth implements Serializable {

	private Long id;
	private Long deptid;
	private String ckbh;
	private String macip;
	private Date createtime;
	private Long operator;
	private Date authtime;
	private Date starttime;
	private Date endtime;
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getCkbh() {
		return ckbh;
	}

	public void setCkbh(String ckbh) {
		this.ckbh = ckbh;
	}

	public String getMacip() {
		return macip;
	}

	public void setMacip(String macip) {
		this.macip = macip;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Date getAuthtime() {
		return authtime;
	}

	public void setAuthtime(Date authtime) {
		this.authtime = authtime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
