package com.jrwp.payMent.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 叫号机模板配置参数表
 * 
 * @author hsl
 * 
 */
public class CallConfigParam {

	private Long id;
	private Long modelId;
	private Long parentId;
	private String orderCode;
	private byte[] icon;
	private String title;
	private String topCoord;
	private String leftCoord;
	private int businessType;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;
	private String remark;

	private List<CallConfigParam> children;

	private String parentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
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

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopCoord() {
		return topCoord;
	}

	public void setTopCoord(String topCoord) {
		this.topCoord = topCoord;
	}

	public String getLeftCoord() {
		return leftCoord;
	}

	public void setLeftCoord(String leftCoord) {
		this.leftCoord = leftCoord;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CallConfigParam> getChildren() {
		return children;
	}

	public void setChildren(List<CallConfigParam> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
