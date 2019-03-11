package com.jrwp.webservice.entity.tmrioutaccess.response;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * 
 * @author: ShenHaijie
 * @date: 2018年10月31日
 */
@XStreamAliasType("queue")
public class Response25C10 {
	@XStreamAsAttribute
	private String id = "";
	// 设备控制计算机编号
	private String sbkzjsjbh = "";
	// 计算机类别
	private String jsjlb = "";
	// 计算机IP
	private String jsjip = "";
	// 窗口编号
	private String ckbh = "";
	// 可办业务类别
	private String kbywlb = "";

	public String getSbkzjsjbh() {
		return sbkzjsjbh;
	}

	public void setSbkzjsjbh(String sbkzjsjbh) {
		this.sbkzjsjbh = sbkzjsjbh;
	}

	public String getJsjlb() {
		return jsjlb;
	}

	public void setJsjlb(String jsjlb) {
		this.jsjlb = jsjlb;
	}

	public String getJsjip() {
		return jsjip;
	}

	public void setJsjip(String jsjip) {
		this.jsjip = jsjip;
	}

	public String getCkbh() {
		return ckbh;
	}

	public void setCkbh(String ckbh) {
		this.ckbh = ckbh;
	}

	public String getKbywlb() {
		return kbywlb;
	}

	public void setKbywlb(String kbywlb) {
		this.kbywlb = kbywlb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
