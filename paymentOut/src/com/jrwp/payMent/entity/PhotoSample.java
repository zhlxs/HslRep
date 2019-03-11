package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 样图实例
 * 
 * @author hsl
 * 
 */
public class PhotoSample implements java.io.Serializable {

	private Long id;
	private String bconfigstrcname;
	private String photosamplepath;
	private byte[] photosample;
	private Boolean isvalid;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	private Long creator;
	private String describe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBconfigstrcname() {
		return bconfigstrcname;
	}

	public void setBconfigstrcname(String bconfigstrcname) {
		this.bconfigstrcname = bconfigstrcname;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
