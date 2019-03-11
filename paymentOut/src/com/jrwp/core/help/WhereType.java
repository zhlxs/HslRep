package com.jrwp.core.help;

/**
 * 查询条件类型
 * 
 * @author USER
 * 
 */
public enum WhereType {
	group(0), where(1);
	int value;

	private WhereType(int value) {
		this.value = value;
	}
}
