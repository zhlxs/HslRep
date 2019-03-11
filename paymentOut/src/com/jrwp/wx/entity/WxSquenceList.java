package com.jrwp.wx.entity;

import java.util.List;

public class WxSquenceList {
	private String squenceName;
	private int waitCount;
	private int businessType;
	private List<WxPmVistor> winNumber;
	
	
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getSquenceName() {
		return squenceName;
	}
	public void setSquenceName(String squenceName) {
		this.squenceName = squenceName;
	}
	public int getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}
	public List<WxPmVistor> getWinNumber() {
		return winNumber;
	}
	public void setWinNumber(List<WxPmVistor> winNumber) {
		this.winNumber = winNumber;
	}
	
	
	
}
