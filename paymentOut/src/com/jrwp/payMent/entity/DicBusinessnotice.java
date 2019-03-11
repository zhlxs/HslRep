package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 经办流程、依据字典表
 * 
 * @author hsl
 * 
 */
public class DicBusinessnotice {

	private Long id;
	private String name;// 名称
	private String businessNotice;// 经办流程
	private String mattersneedAttendtion;// 办事依据
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;//创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessNotice() {
		return businessNotice;
	}

	public void setBusinessNotice(String businessNotice) {
		this.businessNotice = businessNotice;
	}

	public String getMattersneedAttendtion() {
		return mattersneedAttendtion;
	}

	public void setMattersneedAttendtion(String mattersneedAttendtion) {
		this.mattersneedAttendtion = mattersneedAttendtion;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
