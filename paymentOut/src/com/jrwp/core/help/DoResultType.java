package com.jrwp.core.help;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 运行结果类型
 * 
 * @author USER
 * 
 */
public enum DoResultType {
	// 成功
	success(0),
	// 失败
	fail(1),
	// 验证失败
	validFail(2),
	// 警告
	warning(3),
	//迟到
	late(5),
	// 信息
	info(4);
	Integer value;

	private DoResultType(Integer value) {
		this.value = value;
	}

	@JsonValue
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
