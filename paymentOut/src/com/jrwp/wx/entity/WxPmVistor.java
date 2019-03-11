package com.jrwp.wx.entity;

public class WxPmVistor {
	private String state="0";//0:等待，1：叫号状态
    private String name;
    private String queueNumber;
    private String windowNumber;
    private Integer deptid;
    private int businessType;
    private String cardNumber;
    private int isred;
    private Long appointme_infoid;
    
    
	public Long getAppointme_infoid() {
		return appointme_infoid;
	}
	public void setAppointme_infoid(Long appointme_infoid) {
		this.appointme_infoid = appointme_infoid;
	}
	public int getIsred() {
		return isred;
	}
	public void setIsred(int isred) {
		this.isred = isred;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQueueNumber() {
		return queueNumber;
	}
	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}
	public String getWindowNumber() {
		return windowNumber;
	}
	public void setWindowNumber(String windowNumber) {
		this.windowNumber = windowNumber;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
    
    
}
