package com.jrwp.webservice.utils;

import com.jrwp.core.utils.LogUtil;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月17日
 */
public class WSLogUtil extends LogUtil {
	public static void error(String paramString, Throwable e) {
		if (LogUtil.getLogger() != null) {
			LogUtil.getLogger().error(paramString, e);
		}
	}
}
