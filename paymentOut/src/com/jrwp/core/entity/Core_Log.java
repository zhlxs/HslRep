package com.jrwp.core.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 系统日志表
 * 
 */
public class Core_Log {
	/**
	 * 日志ID 日志ID不能为空
	 */
	@NotNull(message = "{Core_Log.id.notnull}")
	@JsonProperty("ID")
	private Long id;
	/**
	 * 操作者ID 操作者ID不能为空
	 */
	@NotNull(message = "{Core_Log.userId.notnull}")
	private Core_User user;
	/**
	 * 操作区域 操作区域不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_Log.areaName.length}")
	private String areaName = "core";
	/**
	 * 操作控制器 操作控制器不能为空 操作控制器不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_Log.controlName.length}")
	private String controlName;
	/**
	 * 操作动作 操作动作不能为空 操作动作不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_Log.actionName.length}")
	private String actionName;
	/**
	 * 参数json字符串 参数json字符串不能为空 参数json字符串不能超过1000字符
	 */
	@Size(min = 1, max = 1000, message = "{Core_Log.parameterJson.length}")
	private String parameterJson = "{}";
	/**
	 * 操作时间
	 */
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;
	/**
	 * 操作IP 操作IP不能为空 操作IP不能超过20字符
	 */
	@Size(min = 1, max = 20, message = "{Core_User.recordIP.length}")
	private String recordIP;
	/**
	 * 控制器说明 控制器说明不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_User.controlDisplay.length}")
	@JsonProperty("controDisplay")
	private String controlDisplay;
	/**
	 * 动作说明 动作说明不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_User.actionDisplay.length}")
	private String actionDisplay;

	private Core_Dept dept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Core_User getUser() {
		return user;
	}

	public void setUser(Core_User user) {
		this.user = user;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getParameterJson() {
		return parameterJson;
	}

	public void setParameterJson(String parameterJson) {
		this.parameterJson = parameterJson;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordIP() {
		return recordIP;
	}

	public void setRecordIP(String recordIP) {
		this.recordIP = recordIP;
	}

	public String getControlDisplay() {
		return controlDisplay;
	}

	public void setControlDisplay(String controlDisplay) {
		this.controlDisplay = controlDisplay;
	}

	public String getActionDisplay() {
		return actionDisplay;
	}

	public void setActionDisplay(String actionDisplay) {
		this.actionDisplay = actionDisplay;
	}

	public Core_Dept getDept() {
		return dept;
	}

	public void setDept(Core_Dept dept) {
		this.dept = dept;
	}

}
