package com.jrwp.wx.entity;


import com.jrwp.core.entity.Core_Dept;

public class WXSquenceInfo {
	private Long id;

    private int isappointment;

    private String cardnumber;

    private Integer isearly;

    private String appointmenttime;
    private Integer timeQuantum;
    //预约的时间段
    private TimeQuan timeQuan;

    private String squence;
    private int status;
    private String openid;
    private Core_Dept dept;
    private Integer deptid;
    private String createtime;
    private int isproxy;
    private int getNumberType;
    private String ser_code;
    private Long appointme_infoid;
    private int iscall;
    private String name;
    private int businessType;
    private String deviceip;
    private String controlip;
    private String serialnumber;
    private String wicketnumber;
    private String proxyname;
    private String proxycardnumber;
    
	public String getProxyname() {
		return proxyname;
	}
	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}
	public String getProxycardnumber() {
		return proxycardnumber;
	}
	public void setProxycardnumber(String proxycardnumber) {
		this.proxycardnumber = proxycardnumber;
	}
	public String getWicketnumber() {
		return wicketnumber;
	}
	public void setWicketnumber(String wicketnumber) {
		this.wicketnumber = wicketnumber;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getDeviceip() {
		return deviceip;
	}
	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
	public String getControlip() {
		return controlip;
	}
	public void setControlip(String controlip) {
		this.controlip = controlip;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIsappointment() {
		return isappointment;
	}
	public void setIsappointment(int isappointment) {
		this.isappointment = isappointment;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public Integer getIsearly() {
		return isearly;
	}
	public void setIsearly(Integer isearly) {
		this.isearly = isearly;
	}
	public String getAppointmenttime() {
		return appointmenttime;
	}
	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}
	public Integer getTimeQuantum() {
		return timeQuantum;
	}
	public void setTimeQuantum(Integer timeQuantum) {
		this.timeQuantum = timeQuantum;
	}
	public TimeQuan getTimeQuan() {
		return timeQuan;
	}
	public void setTimeQuan(TimeQuan timeQuan) {
		this.timeQuan = timeQuan;
	}
	public String getSquence() {
		return squence;
	}
	public void setSquence(String squence) {
		this.squence = squence;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Core_Dept getDept() {
		return dept;
	}
	public void setDept(Core_Dept dept) {
		this.dept = dept;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getIsproxy() {
		return isproxy;
	}
	public void setIsproxy(int isproxy) {
		this.isproxy = isproxy;
	}
	public int getGetNumberType() {
		return getNumberType;
	}
	public void setGetNumberType(int getNumberType) {
		this.getNumberType = getNumberType;
	}
	public String getSer_code() {
		return ser_code;
	}
	public void setSer_code(String ser_code) {
		this.ser_code = ser_code;
	}
	public Long getAppointme_infoid() {
		return appointme_infoid;
	}
	public void setAppointme_infoid(Long appointme_infoid) {
		this.appointme_infoid = appointme_infoid;
	}
	public int getIscall() {
		return iscall;
	}
	public void setIscall(int iscall) {
		this.iscall = iscall;
	}
    
    
}
