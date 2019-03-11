package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 设备样图实例20190306
 * 
 * @author hsl
 * 
 */
public class MAC_PHOTO implements java.io.Serializable {

	private Long id;
	private String photoname;
	private String photosamplepath;
	private byte[] photosample;
	private Boolean isvalid;
	private String describe;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;

	private int photoType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoname() {
		return photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getPhotosamplepath() {
		return photosamplepath;
	}

	public void setPhotosamplepath(String photosamplepath) {
		this.photosamplepath = photosamplepath;
	}

	public byte[] getPhotosample() {
		return photosample;
	}

	public void setPhotosample(byte[] photosample) {
		this.photosample = photosample;
	}

	public Boolean getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getPhotoType() {
		return photoType;
	}

	public void setPhotoType(int photoType) {
		this.photoType = photoType;
	}

}
