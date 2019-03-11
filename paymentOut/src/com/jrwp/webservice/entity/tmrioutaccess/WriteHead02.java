package com.jrwp.webservice.entity.tmrioutaccess;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public class WriteHead02 {
	// 标记，1成功；非1失败
	private String retcode = "";
	// 描述信息
	private String retdesc = "";

	public String getRetcode() {
		return retcode;
	}

	public String getRetdesc() {
		return retdesc;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public void setRetdesc(String retdesc) {
		this.retdesc = retdesc;
	}
}
