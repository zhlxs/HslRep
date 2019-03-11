package com.jrwp.core.help;

import java.util.List;

/**
 * 运行结果
 * 
 * @author USER
 * 
 */
public class DoResult {
	/**
	 * 状态类型
	 */
	private DoResultType stateType;
	/**
	 * 结果值
	 */
	private Object stateValue;
	/**
	 * 结果消息
	 */
	private String stateMsg;
	/**
	 * 跳转页面
	 */
	private String url;
	/**
	 * 验证信息列表
	 */
	private List<ValidationResult> validationResults;

	public DoResultType getStateType() {
		return stateType;
	}

	public void setStateType(DoResultType stateType) {
		this.stateType = stateType;
	}

	public Object getStateValue() {
		return stateValue;
	}

	public void setStateValue(Object stateValue) {
		this.stateValue = stateValue;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ValidationResult> getValidationResults() {
		return validationResults;
	}

	public void setValidationResults(List<ValidationResult> validationResults) {
		this.validationResults = validationResults;
	}

}
