package com.jrwp.wx.entity;

public class PersonInfo {
	private String id;
	private String account;
	private String password;
	private String jynumber;
	private String name;
	private byte[] photo;
	private long deptID;
	
	
	public long getDeptID() {
		return deptID;
	}
	public void setDeptID(long deptID) {
		this.deptID = deptID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJynumber() {
		return jynumber;
	}
	public void setJynumber(String jynumber) {
		this.jynumber = jynumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}
