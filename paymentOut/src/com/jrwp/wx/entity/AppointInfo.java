package com.jrwp.wx.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 预约信息
 * 
 * @author hsl
 * 
 */
public class AppointInfo {

	private Long id;
	private String cardNumber;
	private String name;
	private Long deptId;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointmenttime;// 预约时间
	private Integer status;// 预约状态 0 取消 1 预约成功 2 以签到
	private String phone;// 联系电话
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	private String openId;
	private Long timeQuantum;// 预约的时间段，对应时间段字典表的ID
	private int isExport;// 是否导出（0待导出，1已导出）
	private String serCode;// 业务编码，ser_code
	private int isProxy;// 是否他人代理（0否，1是）
	private Long proxypersonId;// Wx_PROXYPERSONID，代理人ID
	private int isCheck;// 是否进行了在逃比对
	private int businessType;// 业务类型（1机动车业务，2驾驶证业务，4违法业务）

	private String deptName;// 预约网点名称
	private String timeInter;// 预约时段
	private Integer isAttend;// 是否签到
	private String serName;// 业务名称
	private String appTime;// 预约时间

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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Date getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(Date appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getTimeQuantum() {
		return timeQuantum;
	}

	public void setTimeQuantum(Long timeQuantum) {
		this.timeQuantum = timeQuantum;
	}

	public int getIsExport() {
		return isExport;
	}

	public void setIsExport(int isExport) {
		this.isExport = isExport;
	}

	public String getSerCode() {
		return serCode;
	}

	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}

	public int getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(int isProxy) {
		this.isProxy = isProxy;
	}

	public Long getProxypersonId() {
		return proxypersonId;
	}

	public void setProxypersonId(Long proxypersonId) {
		this.proxypersonId = proxypersonId;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTimeInter() {
		return timeInter;
	}

	public void setTimeInter(String timeInter) {
		this.timeInter = timeInter;
	}

	public Integer getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(Integer isAttend) {
		this.isAttend = isAttend;
	}

	public String getSerName() {
		return serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

}
