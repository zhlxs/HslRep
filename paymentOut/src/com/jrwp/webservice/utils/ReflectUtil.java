package com.jrwp.webservice.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月30日
 */
public class ReflectUtil {
	private static List<Field> fieldList = new ArrayList<Field>();

	/**
	 * 打印bean属性值
	 * 
	 * @param object
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentExceptio
	 */
	public static void printBean(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			System.out.println(field.getName() + "|" + field.get(object));
		}
	}

	public static void printBeanPlus(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		getFelids(clazz);
		for (Field field : fieldList) {
			field.setAccessible(true);
			System.out.println(field.getName() + "|" + field.get(object));
		}
		fieldList.clear();
	}

	private static void getFelids(Class<?> clazz) {
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null && superclass != Object.class) {
			getFelids(superclass);
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			fieldList.add(field);
		}
	}

	/**
	 * 通过反射给实体类的属性设置值
	 *
	 * @param object
	 * @param json
	 * @throws IllegalAccessException
	 */
	public static void reflectSetValue(Object object, JSONObject json) throws IllegalAccessException {
		Class<?> clazz = object.getClass();
		// 获取本类中的所有属性(包括私有)
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 授予访问私有变量的权限
			field.setAccessible(true);
			String value = json.getString(field.getName());
			if (value != null) {
				field.set(object, value);
			}
		}
	}
}
