package com.jrwp.webservice.help;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年10月15日
 */
public class WSResult<T> {
	/**
	 * 响应状态
	 */
	// @XStreamConverter(value=CustomEnumSingleNameConverter.class)
	private WSResultType stateType;
	/**
	 * 响应状态描述消息
	 */
	private String stateMsg;
	/**
	 * 响应结果记录条数
	 */
	private Integer stateCount;
	/**
	 * 响应结果
	 */
	private T stateValue;

	/**
	 * 请求list类型数据响应成功
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> successList(List<?> list) {
		this.stateType = WSResultType.success;
		this.stateCount = list.size();
		this.stateValue = (T) list;
		return this;
	}

	/**
	 * 请求boolean类型数据响应成功
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> successBoolean() {
		this.stateType = WSResultType.success;
		this.stateCount = 1;
		this.stateValue = (T) new Boolean(true);
		return this;
	}

	/**
	 * 请求java bean 或其他非list类型数据响应成功
	 * 
	 * @param object
	 * @return
	 */
	public WSResult<T> successObject(T object) {
		this.stateType = WSResultType.success;
		this.stateCount = 1;
		this.stateValue = object;
		return this;
	}

	/**
	 * 请求java bean 或其他非list类型数据响应成功
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> successObject() {
		this.stateType = WSResultType.success;
		this.stateValue = (T) new Object();
		return this;
	}

	/**
	 * 请求六合一平台数据响应成功
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WSResult<T> successLHYHead(LHYHeadResult LHYResult) {
		this.stateType = WSResultType.success;
		this.stateCount = 1;
		this.stateValue = (T) LHYResult;
		return this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WSResult<T> successLHY(LHYResult LHYResult) {
		this.stateType = WSResultType.success;
		this.stateCount = 1;
		this.stateValue = (T) LHYResult;
		return this;
	}

	/**
	 * 请求响应失败
	 * 
	 * @return
	 */
	public WSResult<T> fail() {
		this.stateType = WSResultType.fail;
		return this;
	}

	/**
	 * 请求list类型数据响应失败
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> failList() {
		this.stateType = WSResultType.fail;
		this.stateValue = (T) new ArrayList<Object>();
		return this;
	}

	/**
	 * 请求boolean类型数据响应失败
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> failBoolean() {
		this.stateType = WSResultType.fail;
		this.stateValue = (T) new Boolean(false);
		return this;
	}

	/**
	 * 请求java bean 或其他非list类型数据响应失败
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> failObject() {
		this.stateType = WSResultType.fail;
		this.stateValue = (T) new Object();
		return this;
	}

	/**
	 * 请求六合一平台数据数据响应失败
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WSResult<T> failLHY() {
		this.stateType = WSResultType.fail;
		LHYResult<Object, List<Object>> LHYResult = new LHYResult<Object, List<Object>>();
		LHYResult.setHead(new Object());
		LHYResult.setBody(new ArrayList<Object>());
		this.stateValue = (T) LHYResult;
		return this;
	}

	@SuppressWarnings("unchecked")
	public WSResult<T> failLHYHead() {
		this.stateType = WSResultType.fail;
		LHYHeadResult<Object> LHYHeadResult = new LHYHeadResult<Object>();
		LHYHeadResult.setHead(new Object());
		this.stateValue = (T) LHYHeadResult;
		return this;
	}

	public WSResultType getStateType() {
		return stateType;
	}

	public void setStateType(WSResultType stateType) {
		this.stateType = stateType;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public WSResult<T> setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
		return this;
	}

	public Integer getStateCount() {
		return stateCount;
	}

	public void setStateCount(Integer stateCount) {
		this.stateCount = stateCount;
	}

	public T getStateValue() {
		return stateValue;
	}

	public void setStateValue(T stateValue) {
		this.stateValue = stateValue;
	}
}
