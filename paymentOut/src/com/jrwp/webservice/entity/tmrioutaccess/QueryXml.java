package com.jrwp.webservice.entity.tmrioutaccess;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月31日
 */
public class QueryXml<T> {
	private T QueryCondition;

	public T getQueryCondition() {
		return QueryCondition;
	}

	public void setQueryCondition(T queryCondition) {
		QueryCondition = queryCondition;
	}
}
