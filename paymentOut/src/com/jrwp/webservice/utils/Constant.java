package com.jrwp.webservice.utils;

import com.jrwp.core.utils.PropertyUtil;

/**
 * 常量定义， 接口名称和WebServiceDistry中的方法名一致
 * 
 * @author: ShenHaijie
 * @date: 2018年10月16日
 */
public class Constant {
	/**
	 * 新版接口地址
	 */
	public static final String tmriOutNewAccessAddress = PropertyUtil.getProperty("TmriOutNewAccess.address");
	/**
	 * 新版接口命名空间
	 */
	public static final String TmriOutNewAccessNamespace = PropertyUtil.getProperty("TmriOutNewAccess.namespace");
	/**
	 * 接口序列号
	 */
	public static final String jkxlh = PropertyUtil.getProperty("Access.jkxlh");
	/**
	 * 用户标识
	 */
	public static final String yhbz = PropertyUtil.getProperty("Access.yhbz");
	/**
	 * 用户姓名
	 */
	public static final String yhxm = PropertyUtil.getProperty("Access.yhxm");
	/**
	 * 单位名称
	 */
	public static final String dwmc = PropertyUtil.getProperty("Access.dwmc");
	/**
	 * 单位机构代码
	 */
	public static final String dwjgdm = PropertyUtil.getProperty("Access.dwjgdm");
	/**
	 * 终端标识
	 */
	public static final String zdbs = PropertyUtil.getProperty("Access.zdbs");
}
