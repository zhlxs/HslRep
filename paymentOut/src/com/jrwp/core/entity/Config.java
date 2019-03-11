package com.jrwp.core.entity;

/**
 * 系统基本信息辅助类
 * 
 * @author USER
 * 
 */
public class Config {
	/**
	 * 系统名字
	 */
	private String sysName;
	/**
	 * 版权信息
	 */
	private String copyrightInfo;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getCopyrightInfo() {
		return copyrightInfo;
	}

	public void setCopyrightInfo(String copyrightInfo) {
		this.copyrightInfo = copyrightInfo;
	}

}
