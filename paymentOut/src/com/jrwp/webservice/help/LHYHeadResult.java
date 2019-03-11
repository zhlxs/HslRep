package com.jrwp.webservice.help;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.jrwp.webservice.entity.tmrioutaccess.QueryHead;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHead;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHead02;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHeadLsh;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月6日
 */
public class LHYHeadResult<H> {
	/**
	 * 六合一平台响应头部
	 */
	@JSONField(serialize = false)
	private H head;
	/**
	 * 响应状态
	 */
	protected String stateType;
	/**
	 * 响应状态描述消息
	 */
	protected String stateMsg;
	/**
	 * 响应结果记录条数
	 */
	protected String stateCount;

	public LHYHeadResult<H> failHead() {
		this.stateType = "0";
		this.stateCount = "0";
		return this;
	}

	/**
	 * 响应结果
	 */
	public H getHead() {
		return head;
	}

	public void setHead(H head) {
		if (head instanceof QueryHead) {
			QueryHead responseHead = (QueryHead) head;
			this.stateType = responseHead.getCode();
			this.stateMsg = responseHead.getMessage();
			this.stateCount = StringUtils.isBlank(responseHead.getRownum()) == true ? "0" : responseHead.getRownum();
		} else if (head instanceof WriteHeadLsh) {
			WriteHeadLsh responseHead = (WriteHeadLsh) head;
			this.stateType = responseHead.getCode();
			this.stateMsg = responseHead.getMessage();
			this.stateCount = StringUtils.isBlank(responseHead.getLsh()) == true ? "0" : "1";
		} else if (head instanceof WriteHead) {
			WriteHead responseHead = (WriteHead) head;
			this.stateType = responseHead.getCode();
			this.stateMsg = responseHead.getMessage();
			this.stateCount = "0";
		} else if (head instanceof WriteHead02) {
			WriteHead02 responseHead = (WriteHead02) head;
			this.stateType = responseHead.getRetcode();
			this.stateMsg = responseHead.getRetdesc();
			this.stateCount = "0";
		}
		this.head = head;
	}

	public String getStateType() {
		return stateType;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public String getStateCount() {
		return stateCount;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public void setStateCount(String stateCount) {
		this.stateCount = stateCount;
	}
}
