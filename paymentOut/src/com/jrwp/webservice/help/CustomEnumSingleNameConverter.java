package com.jrwp.webservice.help;

import java.lang.reflect.Method;

import com.thoughtworks.xstream.converters.enums.EnumSingleValueConverter;

/**
 * http://www.blogjava.net/DLevin/archive/2012/11/30/392240.html
 * 
 * @author: ShenHaijie
 * @date: 2018年11月8日
 */
public class CustomEnumSingleNameConverter extends EnumSingleValueConverter {
	private Class<? extends Enum<?>> enumType;

	public CustomEnumSingleNameConverter(Class<? extends Enum<?>> type) {
		super(type);
		this.enumType = type;
	}

	@Override
	public String toString(Object obj) {
		try {
			Method method = enumType.getMethod("toString");
			if (method != null) {
				return (String) method.invoke(obj);
			}
		} catch (Exception e) {
		}
		return super.toString(obj);
	}

	@Override
	public Object fromString(String str) {
		try {
			Method method = enumType.getMethod("toEnum", String.class);
			if (method != null) {
				return method.invoke(null, str);
			}
		} catch (Exception e) {
		}
		return super.fromString(str);
	}

}
