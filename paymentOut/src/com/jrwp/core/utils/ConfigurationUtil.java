/**
 * Copyright(c) 2005 zjhcsoft Techonologies, Ltd.
 *
 * History:
 *   2010-3-4 14:10:33 Created by Tiwen
 */
package com.jrwp.core.utils;

import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 * 系统配置文件参获取类
 *
 * @author <a href="mailto:Tiwen@qq.com">Tiwen</a>
 * @version 1.0 2010-3-4 14:10:33
 */
public class ConfigurationUtil {

	private static String lock = "lock";

	private static ConfigurationUtil systemConfiguration = new ConfigurationUtil();

	private static List<String> locations;

	private ConfigurationUtil() {

	}

	public static ConfigurationUtil getInstance() {
		if (!isRead) {
			synchronized (lock) {
				if (!isRead) {
					reload();
					isRead = true;
				}
			}
		}
		return systemConfiguration;
	}

	/**
	 * 重新加载配置文件
	 */
	public static void reload() {
		if (locations == null) {
			return;
		}
		// 预先清空原先的配置集合
		compositeConfiguration.clear();
		Configuration conf = null;

		for (String location : locations) {
			if (StringUtils.isNotEmpty(location)) {
				try {

					if (location.toLowerCase().endsWith(".xml")) {
						conf = new XMLConfiguration(location);
					} else {
						conf = new PropertiesConfiguration(location);
					}

					compositeConfiguration.addConfiguration(conf);
				} catch (ConfigurationException e) {
				}
			}
		}

	}

	private static CompositeConfiguration compositeConfiguration = new CompositeConfiguration();

	private static boolean isRead = false;

	/**
	 * 读取key，返回boolean结果，如果没有找到对应键值，则返回false
	 * 
	 * @param key
	 *            键
	 * @return boolean结果
	 */
	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 读取key，返回boolean结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return boolean结果
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return compositeConfiguration.getBoolean(key, defaultValue);
	}

	/**
	 * 读取key，返回字符串结果，如果没有找到对应键值，则返回null
	 * 
	 * @param key
	 *            键
	 * @return 键值字符串
	 */
	public static String getString(String key) {
		return getString(key, null);
	}

	/**
	 * 读取key，返回字符串结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值字符串
	 */
	public static String getString(String key, String defaultValue) {
		return compositeConfiguration.getString(key, defaultValue);
	}

	/**
	 * 读取key，返回int结果，如果没有找到对应键值，则返回0
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 读取key，返回int结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static int getInt(String key, int defaultValue) {
		return compositeConfiguration.getInt(key, defaultValue);
	}

	/**
	 * 读取key，返回double结果，如果没有找到对应键值，则返回0f
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static double getDouble(String key) {
		return getDouble(key, 0d);
	}

	/**
	 * 读取key，返回double结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static double getDouble(String key, double defaultValue) {
		return compositeConfiguration.getDouble(key, defaultValue);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回0
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static byte getByte(String key) {
		return getByte(key, (byte) 0);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static byte getByte(String key, byte defaultValue) {

		return compositeConfiguration.getByte(key, defaultValue);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回0f
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static float getFloat(String key) {
		return getFloat(key, 0f);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static float getFloat(String key, float defaultValue) {
		return compositeConfiguration.getFloat(key, defaultValue);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回0l
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static long getLong(String key) {
		return getLong(key, 0l);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static long getLong(String key, long defaultValue) {
		return compositeConfiguration.getLong(key, defaultValue);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回0
	 * 
	 * @param key
	 *            键
	 * @return 键值
	 */
	public static short getShort(String key) {
		return getShort(key, (short) 0);
	}

	/**
	 * 读取key，返回key结果，如果没有找到对应键值，则返回默认值
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 */
	public static short getShort(String key, short defaultValue) {
		return compositeConfiguration.getShort(key, defaultValue);
	}

	public List<String> getLocations() {
		return locations;
	}

	public static void setLocations(List<String> locations) {
		ConfigurationUtil.locations = locations;
	}

}