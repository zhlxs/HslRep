package com.jrwp.core.utils;

import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties props;
	static {
		loadProps();
	}

	synchronized static private void loadProps() {
		logger.info("开始加载context.properties文件内容.......");
		props = new Properties();
		InputStream in = null;
		try {
			in = PropertyUtil.class.getClassLoader().getResourceAsStream("context.properties");
			props.load(new InputStreamReader(in, "UTF-8"));
		} catch (FileNotFoundException e) {
			logger.error("context.properties文件未找到");
		} catch (IOException e) {
			logger.error("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("context.properties文件流关闭出现异常");
			}
		}
		logger.info("加载properties文件内容完成...........");
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key).trim();
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue).trim();
	}

	/**
	 * 最大密码错误限制，超过锁定用户
	 * 
	 * @return
	 */
	public static int getFailNumber() {
		return Integer.parseInt(PropertyUtil.getProperty("Core.password.fail.number", "3"));
	}

	/**
	 * 系统最大会话连接数
	 * 
	 * @return
	 */
	public static int getMaxNumber() {
		return Integer.parseInt(PropertyUtil.getProperty("Core.session.max.number", "50"));
	}
}
