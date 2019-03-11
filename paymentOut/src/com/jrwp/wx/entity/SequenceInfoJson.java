package com.jrwp.wx.entity;

public class SequenceInfoJson {

	private Long aid;
	private String name;//姓名
	private String business;//业务名称
	private String day;//预约日期
	private String timeInter;//预约时段
	private Integer status;//状态
	private Integer peopleNumber;//前面多少人
	private int businessType;//业务类型
	private String createtime;//创建时间

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeInter() {
		return timeInter;
	}

	public void setTimeInter(String timeInter) {
		this.timeInter = timeInter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
