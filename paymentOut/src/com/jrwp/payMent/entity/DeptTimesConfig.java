package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 部门取号次数限制对象
 * 
 * @author hsl
 * 
 */
public class DeptTimesConfig {

	private Long deptid;// 部门Id
	private Integer timeInter;// 时间间隔（天）
	private Integer times;// 次数
	private Long creator;// 创建人
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;// 更新时间

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public Integer getTimeInter() {
		return timeInter;
	}

	public void setTimeInter(Integer timeInter) {
		this.timeInter = timeInter;
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

}
