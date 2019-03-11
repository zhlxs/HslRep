package com.jrwp.webservice.entity.tmrioutaccess;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月18日
 */
public class WriteHeadMsg {
	// 标记:1-成功,0-失败
	private String code;
	// 描述具体信息
	private String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
