package com.jrwp.core.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 部门表
 * 
 */
public class Core_Dept {

	/**
	 * 部门ID 部门ID不能为空
	 */
	@NotNull(message = "{Core_Dept.id.notNull}")
	@JsonProperty(value = "ID")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	/**
	 * 所属部门 请选择所属部门
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "parentID")
	private Long parentId;

	/**
	 * 排序码 排序码不能超过1000字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 1000, message = "{Core_Dept.orderCode.length}")
	private String orderCode;

	/**
	 * 是否系统级
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "isSys")
	private Boolean isSys;

	/**
	 * 部门名称 部门名称不能为空 部门名称不能超过20字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 20, message = "{Core_Dept.deptName.length}")
	private String deptName;

	/**
	 * 显示状态 0：不显示 1：显示
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "isShow")
	private Boolean isShow;

	/**
	 * 部门说明 部门说明不能超过200字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 200, message = "{Core_Dept.remark.length}")
	private String remark;

	/**
	 * 部门编号 部门编号不能为空 部门编号不能超过20字符
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(min = 1, max = 20, message = "{Core_Dept.deptCode.length}")
	private String deptCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Core_Dept> children;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String otherName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String adminRegionId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String unitid;
	private boolean isexpand;
	private boolean isdelay;
	private boolean ischecked;
	private Integer macId;

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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<Core_Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Core_Dept> children) {
		this.children = children;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getAdminRegionId() {
		return adminRegionId;
	}

	public void setAdminRegionId(String adminRegionId) {
		this.adminRegionId = adminRegionId;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public boolean isIsdelay() {
		return isdelay;
	}

	public void setIsdelay(boolean isdelay) {
		this.isdelay = isdelay;
	}

	public Integer getmacId() {
		return macId;
	}

	public void setmacId(Integer macId) {
		this.macId = macId;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
}
