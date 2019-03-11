package com.jrwp.payMent.entity;

/**
 * 预约时间段字典表
 * 
 * @author hsl
 * 
 */
public class Time_Table {

	private Long id;// 主键
	private String time;// 时间段
	private int isValid;// 是否有效
	private Long modelId;// 所属模板id

	private String ordercode;// 排序码
	private int orderCount;// 可预约的人数

	private DicTime dicTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public DicTime getDicTime() {
		return dicTime;
	}

	public void setDicTime(DicTime dicTime) {
		this.dicTime = dicTime;
	}

}
