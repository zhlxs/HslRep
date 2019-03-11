package com.jrwp.webservice.utils;

import java.util.UUID;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月17日
 */
public class IDGen {
	/**
	 * 创建32位的UUID
	 *
	 * @return
	 */
	public static String createUUID32() {
		return createUUID36().replaceAll("-", "");
	}

	/**
	 * 创建36位的UUID
	 *
	 * @return
	 */
	public static String createUUID36() {
		return UUID.randomUUID().toString();
	}
}
