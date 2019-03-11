package com.jrwp.webservice.utils;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月18日
 */
public class CommonUtil {
	/**
	 * 获取调用这个方法的指定类的方法名
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getSpecifyMethodName(Class<?> clazz) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			if (stackTraceElement.getClassName().equals(clazz.getName())) {
				return stackTraceElement.getMethodName();
			}
		}
		return null;
	}

	public static String nullStringToEmpty(String str) {
		return str = str == null ? "" : str;
	}
}
