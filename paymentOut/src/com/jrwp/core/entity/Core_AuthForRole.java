package com.jrwp.core.entity;

import javax.validation.constraints.NotNull;

/**
 * 权限所属角色表
 * 
 */
public class Core_AuthForRole {
	/**
	 * 角色ID 角色ID不能为空
	 */
	@NotNull(message = "{Core_AuthForRole.roleId.notnull}")
	private long roleId;
	/**
	 * 权限ID 权限ID不能为空
	 */
	@NotNull(message = "{Core_AuthForRole.authId.notnull}")
	private long authId;
	/**
	 * 权限标题 权限标题不能为空
	 */
	@NotNull(message = "{Core_AuthForRole.authname.notnull}")
	private String authname;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getAuthId() {
		return authId;
	}

	public void setAuthId(long authId) {
		this.authId = authId;
	}

	public String getAuthname() {
		return authname;
	}

	public void setAuthname(String authname) {
		this.authname = authname;
	}

}
