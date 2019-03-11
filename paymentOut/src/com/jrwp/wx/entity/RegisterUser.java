package com.jrwp.wx.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Object for Users
 * 
 * @author hsl
 * 
 */
public class RegisterUser {

	private Long id;
	private String wxOpenId;// wechat openid
	private String username;// username//姓名
	private String idCardNumber;// 身份证
	private String phoneNumber;// can setting value by null手机号码
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private int status;// 状态（0未绑定，1绑定中，2已解绑）
	private Date createtime;// 创建时间
	private int isappoint;// 是否预约用户
	private Long seqid;// 队列id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getIsappoint() {
		return isappoint;
	}

	public void setIsappoint(int isappoint) {
		this.isappoint = isappoint;
	}

	public Long getSeqid() {
		return seqid;
	}

	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}

}
