package com.jrwp.core.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 菜单表
 * 
 */
public class Core_Menu {
	/**
	 * 菜单ID 菜单ID不能为空
	 */
	@NotNull(message = "{Core_Menu.id.notnull}")
	@JsonProperty(value = "ID")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;
	/**
	 * 所属菜单 请选择所属菜单
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "parentID")
	private Long parentId;
	/**
	 * 排序码 排序码不能超过1000字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 1000, message = "{Core_Menu.orderCode.length}")
	private String orderCode = "";
	/**
	 * 是否系统级（数据库中）0 非系统 1 系统
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "isSys")
	private Boolean isSys;
	/**
	 * 菜单名称 菜单名称不能为空 菜单名称不能超过20字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 20, message = "{Core_Menu.menuName.length}")
	private String menuName;
	/**
	 * 菜单图标 菜单图标不能超过20字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 20, message = "{Core_Menu.iconCssClass.length}")
	private String iconCssClass = "";
	/**
	 * 菜单URL 菜单URL不能超过100字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 100, message = "{Core_Menu.menuUrl.length}")
	private String menuUrl = "";
	/**
	 * 动作列表 动作列表不能超过1000字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 1000, message = "{Core_Menu.actionList.length}")
	private String actionList = "";
	/**
	 * 显示状态 （数据库中）0 不显示 1 显示
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "isShow")
	private Boolean isShow;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Core_Menu> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Boolean getIsSys() {
		return isSys;
	}

	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIconCssClass() {
		return iconCssClass;
	}

	public void setIconCssClass(String iconCssClass) {
		this.iconCssClass = iconCssClass;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getActionList() {
		return actionList;
	}

	public void setActionList(String actionList) {
		this.actionList = actionList;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public List<Core_Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Core_Menu> children) {
		this.children = children;
	}

}
