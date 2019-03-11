package com.jrwp.webservice.utils;

import java.lang.reflect.Field;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json工具类
 * 
 * @author: ShenHaijie
 * @date: 2018年10月16日
 */
public class JsonUtil {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将对象转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSON(Object object) {
		JSONObject.DEFFAULT_DATE_FORMAT = DATE_FORMAT;
		return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue, // 输出值为null的字段
				SerializerFeature.WriteDateUseDateFormat, // 用制定的格式输出日期字段
				SerializerFeature.WriteNullStringAsEmpty, // String字段如果为null,输出为""
				SerializerFeature.WriteNullListAsEmpty, // List字段如果为null,输出为[]
				SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null,输出为false
				SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null,输出为0
				SerializerFeature.WriteEnumUsingToString// 调用枚举类型的toString()方法输出
		);
	}

	/**
	 * 通过反射给实体类的属性设置值
	 *
	 * @param object
	 * @param json
	 * @throws IllegalAccessException
	 */
	public static void reflectSetValue(JSONObject json, Object object) throws IllegalAccessException {
		Class<?> clazz = object.getClass();
		// 获取本类中的所有属性(包括私有)
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 授予访问私有变量的权限
			field.setAccessible(true);
			String fieldName = field.getName();
			field.set(object, json.getString(fieldName));
		}
	}

	/**
	 * 将json字符串转为java bean
	 *
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
		// try {
		// if (StringUtils.isEmpty(jsonString)) {
		// WSLogUtil.info("jsonString为空");
		// return null;
		// } else {
		// JSONObject.DEFFAULT_DATE_FORMAT = DATE_FORMAT;
		// T object = JSON.parseObject(jsonString, clazz);
		// if (object != null) {
		// return object;
		// }
		// return null;
		// }
		// } catch (Exception e) {
		// WSLogUtil.info("请检查是否是合法的json字符串[" + jsonString + "]");
		// WSLogUtil.error("json2bean转换失败:{}", e);
		// e.printStackTrace();
		// }
		// return null;
		JSONObject.DEFFAULT_DATE_FORMAT = DATE_FORMAT;
		return JSON.parseObject(jsonString, clazz);
	}

	public static <T> T jsonToBean(JSONObject json, Class<T> clazz) {
		JSONObject.DEFFAULT_DATE_FORMAT = DATE_FORMAT;
		return JSON.toJavaObject(json, clazz);
	}

	/**
	 * 将json字符串转为map类型的对象
	 *
	 * @param jsonString
	 * @param clazz1
	 *            map的键类型
	 * @param clazz2
	 *            map的值类型
	 * @return
	 */
	public static <K, V> Map<K, V> jsonToMap(String jsonString, Class<K> clazz1, Class<V> clazz2) {
		// try {
		// if (StringUtils.isEmpty(jsonString)) {
		// WSLogUtil.info("jsonString=[" + jsonString + "]为空");
		// return null;
		// }
		// Map<K, V> result = new HashMap<K, V>();
		// result = JSON.parseObject(jsonString, new TypeReference<Map<K, V>>() {
		// });
		// return result;
		// } catch (Exception e) {
		// WSLogUtil.info("请检查是否是合法的json字符串[" + jsonString + "]");
		// WSLogUtil.error("json2bean转换失败:{}", e);
		// e.printStackTrace();
		// }
		// return null;
		return JSON.parseObject(jsonString, new TypeReference<Map<K, V>>() {
		});
	}
}
