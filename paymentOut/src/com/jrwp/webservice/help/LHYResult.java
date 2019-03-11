package com.jrwp.webservice.help;

import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月5日
 */
public class LHYResult<H, B> extends LHYHeadResult<H> {
	/**
	 * 六合一平台响应body
	 */
	@JSONField(serialize = false)
	private B body;
	/**
	 * 响应结果
	 */
	private B stateValue;

	@SuppressWarnings("unchecked")
	public LHYResult<H, B> failList() {
		super.stateType = "0";
		super.stateCount = "0";
		this.stateValue = (B) new ArrayList<Object>();
		return this;
	}

	@SuppressWarnings("unchecked")
	public LHYResult<H, B> failString() {
		super.stateType = "0";
		super.stateCount = "0";
		this.stateValue = (B) "";
		return this;
	}

	public B getBody() {
		return body;
	}

	public void setBody(B body) {
		this.body = body;
		this.stateValue = body;
	}

	public B getStateValue() {
		return stateValue;
	}

	public void setStateValue(B stateValue) {
		this.stateValue = stateValue;
	}
}
