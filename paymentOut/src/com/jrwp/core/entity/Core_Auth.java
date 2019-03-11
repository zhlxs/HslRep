package com.jrwp.core.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 系统权限表
 * 
 */

public class Core_Auth {
	/**
	 * 权限ID 权限ID不能为空
	 */
	@NotNull(message = "{Core_Auth.id.notnull}")
	private Long id;
	/**
	 * 权限名称 权限名称不能为空 权限名称不能超过20字符
	 */
	@Size(min = 1, max = 20, message = "{Core_Auth.authName.length}")
	private String authName;
	/**
	 * 权限所属动作列表
	 */
	private List<Core_ActionForAuth> actions;
	/**
	 * 包含该权限的角色列表
	 */
	private List<Core_Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public List<Core_ActionForAuth> getActions() {
		return actions;
	}

	public void setActions(List<Core_ActionForAuth> actions) {
		this.actions = actions;
	}

	public List<Core_Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Core_Role> roles) {
		this.roles = roles;
	}

}
