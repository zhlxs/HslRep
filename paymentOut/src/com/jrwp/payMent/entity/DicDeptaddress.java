package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrwp.core.entity.Core_Dept;

/**
 * 部门地址信息配置表
 * 
 * @author hsl
 * 
 */
public class DicDeptaddress {

	private Long id;
	private Long deptId;// 关联部门
	private String address;// 地址信息
	private String longitude;// 经度
	private String latitude;// 纬度
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;// 创建时间
	private String photoNumber;// 联系电话
	private Long modelId;// 评价模板id
	private Long tmodelId;// 时间模板id
	private int ableacceptcount;// 单人单日可取号数
	private byte[] qrCode;

	/**
	 * 队列维护功能配置参数
	 */
	private int isopen;// 是否开启维护
	private int minutes;// 超时分钟数
	private int personcount;// 固定人数

	private Core_Dept dept;
	private AppraiseModel model;
	private Time_Model tModel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPhotoNumber() {
		return photoNumber;
	}

	public void setPhotoNumber(String photoNumber) {
		this.photoNumber = photoNumber;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Core_Dept getDept() {
		return dept;
	}

	public void setDept(Core_Dept dept) {
		this.dept = dept;
	}

	public AppraiseModel getModel() {
		return model;
	}

	public void setModel(AppraiseModel model) {
		this.model = model;
	}

	public Long getTmodelId() {
		return tmodelId;
	}

	public void setTmodelId(Long tmodelId) {
		this.tmodelId = tmodelId;
	}

	public Time_Model gettModel() {
		return tModel;
	}

	public void settModel(Time_Model tModel) {
		this.tModel = tModel;
	}

	public int getAbleacceptcount() {
		return ableacceptcount;
	}

	public void setAbleacceptcount(int ableacceptcount) {
		this.ableacceptcount = ableacceptcount;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

	public int getIsopen() {
		return isopen;
	}

	public void setIsopen(int isopen) {
		this.isopen = isopen;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getPersoncount() {
		return personcount;
	}

	public void setPersoncount(int personcount) {
		this.personcount = personcount;
	}

}
