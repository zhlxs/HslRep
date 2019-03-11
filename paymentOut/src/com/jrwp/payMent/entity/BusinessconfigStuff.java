package com.jrwp.payMent.entity;

/**
 * 
 * @author hsl
 * 
 */
public class BusinessconfigStuff implements java.io.Serializable {

	// Fields
	private Long id;
	private String bconfigstrname;// 材料名称
	private Boolean ismustfill;// 是否必须
	private String ordercode;// 排序码
	private Long sampleid;// 样图id
	private Long applytypeid;// 业务申请类型id
	private String datatype;// 数据类型
	private Long showtype;// 展示类型0：展示图片 1：展示附件 2：两者都展示

	private PhotoSample photoSample;
	private BusinessconfigType businessconfigType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBconfigstrname() {
		return bconfigstrname;
	}

	public void setBconfigstrname(String bconfigstrname) {
		this.bconfigstrname = bconfigstrname;
	}

	public Boolean getIsmustfill() {
		return ismustfill;
	}

	public void setIsmustfill(Boolean ismustfill) {
		this.ismustfill = ismustfill;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public Long getSampleid() {
		return sampleid;
	}

	public void setSampleid(Long sampleid) {
		this.sampleid = sampleid;
	}

	public Long getApplytypeid() {
		return applytypeid;
	}

	public void setApplytypeid(Long applytypeid) {
		this.applytypeid = applytypeid;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public Long getShowtype() {
		return showtype;
	}

	public void setShowtype(Long showtype) {
		this.showtype = showtype;
	}

	public PhotoSample getPhotoSample() {
		return photoSample;
	}

	public void setPhotoSample(PhotoSample photoSample) {
		this.photoSample = photoSample;
	}

	public BusinessconfigType getBusinessconfigType() {
		return businessconfigType;
	}

	public void setBusinessconfigType(BusinessconfigType businessconfigType) {
		this.businessconfigType = businessconfigType;
	}

}
