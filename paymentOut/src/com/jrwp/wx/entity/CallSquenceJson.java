package com.jrwp.wx.entity;

public class CallSquenceJson {
	private Long id;
	private int isappointment;
	private String openid;
	private int iscall;
	
	
	public int getIsappointment() {
		return isappointment;
	}
	public void setIsappointment(int isappointment) {
		this.isappointment = isappointment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getIscall() {
		return iscall;
	}
	public void setIscall(int iscall) {
		this.iscall = iscall;
	}
	
	
}
