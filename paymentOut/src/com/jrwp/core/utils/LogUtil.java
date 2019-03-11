package com.jrwp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	private static Logger logger;
	static {
		try {
			logger = LoggerFactory.getLogger(LogUtil.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final Logger getLogger() {
		return LogUtil.logger;
	}

	public static void debug(String paramString, Object... paramArrayOfObject) {
		if (logger != null) {
			LogUtil.getLogger().debug(paramString, paramArrayOfObject);
		}
	}

	public static void info(String paramString, Object... paramArrayOfObject) {
		if (logger != null) {
			LogUtil.getLogger().info(paramString, paramArrayOfObject);
		}
	}

	public static void warn(String paramString, Object... paramArrayOfObject) {
		if (logger != null) {
			LogUtil.getLogger().warn(paramString, paramArrayOfObject);
		}
	}

	public static void error(String paramString, Object... paramArrayOfObject) {
		if (logger != null) {
			LogUtil.getLogger().error(paramString, paramArrayOfObject);
		}
	}
}
