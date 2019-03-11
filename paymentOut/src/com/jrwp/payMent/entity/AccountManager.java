package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrwp.core.entity.Core_Dept;

/**
 * 账号信息
 * 
 * @author hsl
 * 
 */
public class AccountManager {

	private Long id;// 主键
	private String account;// 账号
	private String password;// 密码
	private String jynumber;// 警员编号
	private String name;// 姓名
	private byte[] photo;// 头像
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;// 创建时间
	private Long deptid;// 归属部门

	private Core_Dept dept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public Core_Dept getDept() {
		return dept;
	}

	public void setDept(Core_Dept dept) {
		this.dept = dept;
	}

}
