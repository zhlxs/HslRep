package com.jrwp.webservice.help;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 相应状态码定义
 * 
 * @author: ShenHaijie
 * @date: 2018年10月16日
 */
public enum WSResultType {
	/**
	 * 失败
	 */
	fail(0),
	/**
	 * 成功
	 */
	success(1),
	/**
	 * 验证失败
	 */
	validFail(2);
	/**
	 * 状态码
	 */
	Integer value;

	private WSResultType(Integer value) {
		this.value = value;
	}

	@JsonValue
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static WSResultType toEnum(String value) {
		try {
			WSResultType[] enums = WSResultType.values();
			for (WSResultType wsResultType : enums) {
				if (value.equals(wsResultType.value.toString())) {
					return wsResultType;
				}
			}
		} catch (Exception e) {
		}
		return Enum.valueOf(WSResultType.class, value);
	}
}
