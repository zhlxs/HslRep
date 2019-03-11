package com.jrwp.wx.entity;

import java.util.Date;

public class AppointmeInfoJson {
	private Long id;
	private String cardNumber;
	private String name;
	private Long deptid;
	private String appointmenttime;
	private int status;
	private String phone;
	private Date createtime;
	private String openid;
	private Long timequantum;
	private int isexport;
	private String ser_code;
	private int isproxy;
	private Long wx_proxypersonId;
	private String time;
	private int businessType;
	private String business;
	
	
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getAppointmenttime() {
		return appointmenttime;
	}
	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Long getTimequantum() {
		return timequantum;
	}
	public void setTimequantum(Long timequantum) {
		this.timequantum = timequantum;
	}
	public int getIsexport() {
		return isexport;
	}
	public void setIsexport(int isexport) {
		this.isexport = isexport;
	}
	public String getSer_code() {
		return ser_code;
	}
	public void setSer_code(String ser_code) {
		this.ser_code = ser_code;
	}
	public int getIsproxy() {
		return isproxy;
	}
	public void setIsproxy(int isproxy) {
		this.isproxy = isproxy;
	}
	public Long getWx_proxypersonId() {
		return wx_proxypersonId;
	}
	public void setWx_proxypersonId(Long wx_proxypersonId) {
		this.wx_proxypersonId = wx_proxypersonId;
	}
	
	
}
