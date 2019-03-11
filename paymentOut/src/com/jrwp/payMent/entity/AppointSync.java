package com.jrwp.payMent.entity;

/**
 * 预约同步表
 * 
 * @author hsl
 * 
 */
public class AppointSync {

	private Long id;
	private int status;
	private int isexport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsexport() {
		return isexport;
	}

	public void setIsexport(int isexport) {
		this.isexport = isexport;
	}

}
