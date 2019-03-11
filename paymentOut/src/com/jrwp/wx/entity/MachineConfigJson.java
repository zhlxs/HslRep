package com.jrwp.wx.entity;

import java.util.List;

public class MachineConfigJson {
	private Long id;
	private String devicenumber;
	private Long deptid;
	private String ip;
	private String updatetime;
	private Long modelid;
	private List<CallBusinessJson> callBusinessJson;
	private List<CallMenueJson> callMenueJson;
	
	
	public Long getModelid() {
		return modelid;
	}
	public void setModelid(Long modelid) {
		this.modelid = modelid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public List<CallMenueJson> getCallMenueJson() {
		return callMenueJson;
	}
	public void setCallMenueJson(List<CallMenueJson> callMenueJson) {
		this.callMenueJson = callMenueJson;
	}
	public List<CallBusinessJson> getCallBusinessJson() {
		return callBusinessJson;
	}
	public void setCallBusinessJson(List<CallBusinessJson> callBusinessJson) {
		this.callBusinessJson = callBusinessJson;
	}
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
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
