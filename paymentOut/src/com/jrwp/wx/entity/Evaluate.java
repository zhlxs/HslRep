package com.jrwp.wx.entity;

public class Evaluate {
	private long evaluateID;
	private String title;
	private byte[] imageContent;
	private int isdefault;
	public long getEvaluateID() {
		return evaluateID;
	}
	public void setEvaluateID(long evaluateID) {
		this.evaluateID = evaluateID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getImageContent() {
		return imageContent;
	}
	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}
	public int getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}
	
}
