package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DeptTimesConfigJson {

	private Long deptid;
	private String deptname;
	private Integer timeinter;
	private Integer times;
	private Long creator;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;

	private int isdefault = 0;

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Integer getTimeinter() {
		return timeinter;
	}

	public void setTimeinter(Integer timeinter) {
		this.timeinter = timeinter;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

}
