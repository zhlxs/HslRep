package com.jrwp.wx.entity;


public class CallBusinessJson {
	private Long parentid;
	private String ordercode;
	private byte[] icon;
	private String topcoord;
	private String leftcoord;
	private int businesstype;
	private String ser_code;
	private String base64;
	private String width;
	private String height;
	
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getSer_code() {
		return ser_code;
	}
	public void setSer_code(String ser_code) {
		this.ser_code = ser_code;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getTopcoord() {
		return topcoord;
	}
	public void setTopcoord(String topcoord) {
		this.topcoord = topcoord;
	}
	public String getLeftcoord() {
		return leftcoord;
	}
	public void setLeftcoord(String leftcoord) {
		this.leftcoord = leftcoord;
	}
	public int getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}
}
