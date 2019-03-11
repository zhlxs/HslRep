package com.jrwp.appr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserAppraise {
	private Long id;
	private Long userid;
	private Long appraisedetailid;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	private Long orderid;
	private int isdefault;// 保存时默认值为1
	private int iswechat;
	private String otheradvice;// 其他建议
	private String hotwords;// 热词
	private int isexport;

	private Long sequence_infoid;// 对应的队列id
	private int iswrite;// 是否写入

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getAppraisedetailid() {
		return appraisedetailid;
	}

	public void setAppraisedetailid(Long appraisedetailid) {
		this.appraisedetailid = appraisedetailid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

	public int getIswechat() {
		return iswechat;
	}

	public void setIswechat(int iswechat) {
		this.iswechat = iswechat;
	}

	public String getOtheradvice() {
		return otheradvice;
	}

	public void setOtheradvice(String otheradvice) {
		this.otheradvice = otheradvice;
	}

	public String getHotwords() {
		return hotwords;
	}

	public void setHotwords(String hotwords) {
		this.hotwords = hotwords;
	}

	public int getIsexport() {
		return isexport;
	}

	public void setIsexport(int isexport) {
		this.isexport = isexport;
	}

	public Long getSequence_infoid() {
		return sequence_infoid;
	}

	public void setSequence_infoid(Long sequence_infoid) {
		this.sequence_infoid = sequence_infoid;
	}

	public int getIswrite() {
		return iswrite;
	}

	public void setIswrite(int iswrite) {
		this.iswrite = iswrite;
	}

}
