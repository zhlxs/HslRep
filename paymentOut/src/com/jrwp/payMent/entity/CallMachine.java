package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrwp.core.entity.Core_Dept;

/**
 * 叫号机管理
 * 
 * @author hsl
 * 
 */
public class CallMachine {

	private Long id;
	private String deviceNumber;// 机器码
	private Long deptId;// 部门id
	private String machineIp;// 设备ip
	private Long modelId;// 参数配置模板id
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;

	private Core_Dept dept;
	private CallConfigModel model;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Core_Dept getDept() {
		return dept;
	}

	public void setDept(Core_Dept dept) {
		this.dept = dept;
	}

	public CallConfigModel getModel() {
		return model;
	}

	public void setModel(CallConfigModel model) {
		this.model = model;
	}

}
