package com.jrwp.webservice.entity.tmrioutaccess;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public class QueryHead {
	private String code;
	private String message;
	private String rownum;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
}
