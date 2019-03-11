package com.jrwp.payMent.entity;

import com.jrwp.core.entity.Core_Dept;

/**
 * 窗口设备管理
 * 
 * @author hsl
 * 
 */
public class Mac_Windows {

	private Long id;// 主键
	private String devicenumber;// 机器码
	private String ckbh;// 窗口编号
	private int status;// 状态（0正常，1暂停）
	private String deviceip;// 业务窗口计算机ip
	private Long deptid;// 归属部门
	private String systitle;
	private String sysunit;

	private Core_Dept dept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(String devicenumber) {
		this.devicenumber = devicenumber;
	}

	public String getCkbh() {
		return ckbh;
	}

	public void setCkbh(String ckbh) {
		this.ckbh = ckbh;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
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

	public String getSystitle() {
		return systitle;
	}

	public void setSystitle(String systitle) {
		this.systitle = systitle;
	}

	public String getSysunit() {
		return sysunit;
	}

	public void setSysunit(String sysunit) {
		this.sysunit = sysunit;
	}

}
