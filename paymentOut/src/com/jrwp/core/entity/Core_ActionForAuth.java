package com.jrwp.core.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 系统权限所属动作表
 * 
 */
public class Core_ActionForAuth {
	/**
	 * 权限动作id 权限动作ID不能为空
	 */
	@JsonProperty("ID")
	@NotNull(message = "{Core_ActionForAuth.id.notnull}")
	private Long id;
	/**
	 * 权限id 权限ID不能为空
	 */
	@JsonProperty("authID")
	@NotNull(message = "{Core_ActionForAuth.authId.notnull}")
	private long authId;
	/**
	 * 控制器名称 控制器名称不能为空 控制器名称不能超过50字符
	 */
	@JsonProperty("controName")
	@Size(min = 1, max = 50, message = "{Core_ActionForAuth.controlName.length}")
	private String controlName;
	/**
	 * 动作名称 动作名称不能为空 动作名称不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_ActionForAuth.actionName.length}")
	private String actionName;
	/**
	 * 说明 说明不能超过50字符
	 */
	@Size(min = 1, max = 50, message = "{Core_ActionForAuth.display.length}")
	private String display;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAuthId() {
		return authId;
	}

	public void setAuthId(long authId) {
		this.authId = authId;
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

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
