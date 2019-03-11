package com.jrwp.core.entity;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * 角色表
 * 
 */
public class Core_Role {
	/**
	 * 角色ID
	 */
	private Long id;
	/**
	 * 角色名称 角色名称不能为空 角色名称不能超过20字符
	 */
	@Size(min = 1, max = 20, message = "{Core_Role.roleName.length}")
	private String roleName;
	/**
	 * 角色图标 CSS类名 角色图标不能超过20字符
	 */
	@Size(min = 1, max = 20, message = "{Core_Role.iconCssClass.length}")
	private String iconCssClass;
	/**
	 * 是否系统级 0：否 1：是
	 */
	private Integer isSys;
	/**
	 * 角色包含权限列表
	 */
	private List<Core_Auth> auths;
	/**
	 * 拥有该角色的用户列表
	 */
	private List<Core_User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIconCssClass() {
		return iconCssClass;
	}

	public void setIconCssClass(String iconCssClass) {
		this.iconCssClass = iconCssClass;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public List<Core_Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Core_Auth> auths) {
		this.auths = auths;
	}

	public List<Core_User> getUsers() {
		return users;
	}

	public void setUsers(List<Core_User> users) {
		this.users = users;
	}

}
