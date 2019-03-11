package com.jrwp.wx.entity;

public class GetNumberJson {
	private String squence;
	private int waitCount;
	private int waitCountOther;
	private String business;
	
	
	
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public int getWaitCountOther() {
		return waitCountOther;
	}
	public void setWaitCountOther(int waitCountOther) {
		this.waitCountOther = waitCountOther;
	}
	public String getSquence() {
		return squence;
	}
	public void setSquence(String squence) {
		this.squence = squence;
	}
	public int getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}
}
