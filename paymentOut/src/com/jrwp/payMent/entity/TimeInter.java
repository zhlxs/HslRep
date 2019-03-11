package com.jrwp.payMent.entity;

public class TimeInter {

	private String preHour;
	private String preMin;
	private String sufHour;
	private String sufMin;

	private int orderCount;

	private Long id;
	private Integer order;

	public String getPreHour() {
		return preHour;
	}

	public void setPreHour(String preHour) {
		this.preHour = preHour;
	}

	public String getPreMin() {
		return preMin;
	}

	public void setPreMin(String preMin) {
		this.preMin = preMin;
	}

	public String getSufHour() {
		return sufHour;
	}

	public void setSufHour(String sufHour) {
		this.sufHour = sufHour;
	}

	public String getSufMin() {
		return sufMin;
	}

	public void setSufMin(String sufMin) {
		this.sufMin = sufMin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

}
