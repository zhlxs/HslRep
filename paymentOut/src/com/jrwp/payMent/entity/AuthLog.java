package com.jrwp.payMent.entity;

import java.util.Date;

/**
 * 授权日志
 * 
 * @author hsl
 * 
 */
public class AuthLog {

	private Long id;
	private Long deptid;
	private Long operator;
	private String logType;
	private String operatvalue;
	private String operatname;
	private Date operattime;
	private int isAccess;// 是否成功（-1失败，1成功）

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

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getOperatvalue() {
		return operatvalue;
	}

	public void setOperatvalue(String operatvalue) {
		this.operatvalue = operatvalue;
	}

	public String getOperatname() {
		return operatname;
	}

	public void setOperatname(String operatname) {
		this.operatname = operatname;
	}

	public Date getOperattime() {
		return operattime;
	}

	public void setOperattime(Date operattime) {
		this.operattime = operattime;
	}

	public int getIsAccess() {
		return isAccess;
	}

	public void setIsAccess(int isAccess) {
		this.isAccess = isAccess;
	}

}
