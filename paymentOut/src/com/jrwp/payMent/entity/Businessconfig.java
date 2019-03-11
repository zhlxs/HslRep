package com.jrwp.payMent.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 业务配置表
 * 
 * @author hsl
 * 
 */
@SuppressWarnings("serial")
public class Businessconfig implements java.io.Serializable {

	// Fields
	private Long id;// 主键
	private String serCode;// 所属业务编码
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;// 创建时间
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;// 更新时间
	private byte[] icon;// 图标
	private String iconpathstr;// 图标地址

	private String sqtj;// 申请条件说明
	private String sqcl;// 申请材料说明

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerCode() {
		return serCode;
	}

	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getIconpathstr() {
		return iconpathstr;
	}

	public void setIconpathstr(String iconpathstr) {
		this.iconpathstr = iconpathstr;
	}

	public String getSqtj() {
		return sqtj;
	}

	public void setSqtj(String sqtj) {
		this.sqtj = sqtj;
	}

	public String getSqcl() {
		return sqcl;
	}

	public void setSqcl(String sqcl) {
		this.sqcl = sqcl;
	}

}