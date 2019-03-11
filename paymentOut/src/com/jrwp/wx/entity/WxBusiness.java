package com.jrwp.wx.entity;

import java.util.List;

import com.jrwp.payMent.entity.Businessconfig;

/**
 * 微信业务
 * 
 * @author hsl
 * 
 */
public class WxBusiness {

	private String serCode;// 编码
	private String serName;// 名称
	private String pcCode;// 所属警种编码
	private String parentCode;
	private String orderCode;// 排序码
	private int isValid;
	private int businessType;
	private List<WxBusiness> children;// 子业务
	private Businessconfig config;// 业务配置
	private int isproxy;// 是否代办

	public String getSerCode() {
		return serCode;
	}

	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}

	public String getSerName() {
		return serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public List<WxBusiness> getChildren() {
		return children;
	}

	public void setChildren(List<WxBusiness> children) {
		this.children = children;
	}

	public Businessconfig getConfig() {
		return config;
	}

	public void setConfig(Businessconfig config) {
		this.config = config;
	}

	public int getIsproxy() {
		return isproxy;
	}

	public void setIsproxy(int isproxy) {
		this.isproxy = isproxy;
	}

}
