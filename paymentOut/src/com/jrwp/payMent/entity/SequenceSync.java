package com.jrwp.payMent.entity;

/**
 * 队列同步表
 * 
 * @author hsl
 * 
 */
public class SequenceSync {

	private Long id;
	private int status;
	private int isexport;
	private String winnumber;

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

	public String getWinnumber() {
		return winnumber;
	}

	public void setWinnumber(String winnumber) {
		this.winnumber = winnumber;
	}

}
