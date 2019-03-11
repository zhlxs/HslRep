package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.wx.entity.RegisterUser;

/**
 * 业务评价考核表
 * 
 * @author hsl
 * 
 */
public class BusinessAppraise {

	private Long id;// 主键
	private String dlbh;// 队列编号
	private Long qusxh_sxh;// 取号顺序号
	private int ywpjjg;// 业务评价结果1满意，2一般，3不满意
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date ywpjrqsj_rqsj;// 业务评价日期时间
	private String ywlsh;// 业务流水号
	private Long userid;// 用户id
	private Long deptid;// 部门id

	private Core_Dept dept;
	private RegisterUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDlbh() {
		return dlbh;
	}

	public void setDlbh(String dlbh) {
		this.dlbh = dlbh;
	}

	public Long getQusxh_sxh() {
		return qusxh_sxh;
	}

	public void setQusxh_sxh(Long qusxh_sxh) {
		this.qusxh_sxh = qusxh_sxh;
	}

	public int getYwpjjg() {
		return ywpjjg;
	}

	public void setYwpjjg(int ywpjjg) {
		this.ywpjjg = ywpjjg;
	}

	public Date getYwpjrqsj_rqsj() {
		return ywpjrqsj_rqsj;
	}

	public void setYwpjrqsj_rqsj(Date ywpjrqsj_rqsj) {
		this.ywpjrqsj_rqsj = ywpjrqsj_rqsj;
	}

	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public RegisterUser getUser() {
		return user;
	}

	public void setUser(RegisterUser user) {
		this.user = user;
	}

}
