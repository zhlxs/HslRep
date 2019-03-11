package com.jrwp.webservice.entity.tmrioutaccess;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public class WriteHead {
	// 标记:1-成功,0-失败
	private String code;
	// 描述具体信息
	private String message;

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
}
