package com.jrwp.webservice.utils;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public enum SystemType {
	/**
	 * 机动车登记业务
	 */
	jdcdjyw("01"),
	/**
	 * 驾驶证管理业务
	 */
	jszglyw("02"),
	/**
	 * 事故处理
	 */
	sgcl("03"),
	/**
	 * 违法处理
	 */
	wfcl("04"),
	/**
	 * 交警队平台
	 */
	jjdpt("05"),
	/**
	 * 剧毒品业务
	 */
	jdpyw("06");
	private String code;

	private SystemType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}
}
