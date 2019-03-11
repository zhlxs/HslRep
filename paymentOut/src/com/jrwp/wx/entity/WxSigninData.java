package com.jrwp.wx.entity;

public class WxSigninData {
	private Long id;

	private Integer deptid;
	private String name;
	private String cardNumber;
	private int businessType;
	private String ser_code;
	private int checkResult;
	private String xsd;
	private String photobase64;
	private String cardbase64;
	
	
	public String getCardbase64() {
		return cardbase64;
	}

	public void setCardbase64(String cardbase64) {
		this.cardbase64 = cardbase64;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getSer_code() {
		return ser_code;
	}

	public void setSer_code(String ser_code) {
		this.ser_code = ser_code;
	}

	public int getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(int checkResult) {
		this.checkResult = checkResult;
	}

	public String getXsd() {
		return xsd;
	}

	public void setXsd(String xsd) {
		this.xsd = xsd;
	}

	public String getPhotobase64() {
		return photobase64;
	}

	public void setPhotobase64(String photobase64) {
		this.photobase64 = photobase64;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
