package com.jrwp.appr.entity;

public class UserHotwords {

	private Long id;
	private Long userapprid;
	private String hotword;
	private int isexport;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserapprid() {
		return userapprid;
	}
	public void setUserapprid(Long userapprid) {
		this.userapprid = userapprid;
	}
	public String getHotword() {
		return hotword;
	}
	public void setHotword(String hotword) {
		this.hotword = hotword;
	}
	public int getIsexport() {
		return isexport;
	}
	public void setIsexport(int isexport) {
		this.isexport = isexport;
	}
	
}
