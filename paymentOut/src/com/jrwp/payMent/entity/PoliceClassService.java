package com.jrwp.payMent.entity;

import java.util.List;

/**
 * 业务配置
 * 
 * @author Administrator
 * 
 */
public class PoliceClassService {

	// @JsonProperty("id")
	private String serCode;// 编码
	private String serName;// 名称
	private String pcCode;// 所属警种编码
	private double price;// 价格
	private String isDel;
	private String parentCode;
	private String orderCode;
	private int isProxy;// 是否可以代办（0否，1是）
	private Long businessnoticeId;// 流程、依据ID（子类默认跟随父类）
	private Integer businessType;// 业务类型字段
	private List<PoliceClassService> children;
	private boolean isexpand;
	private boolean isdelay;
	private boolean isopen;
	private PoliceClassService parent;

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	private Core_Police police;

	public Core_Police getPolice() {
		return police;
	}

	public void setPolice(Core_Police police) {
		this.police = police;
	}

	public PoliceClassService() {
	}

	public String getSerCode() {
		return serCode;
	}

	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}

	public String getSerName() {
		return serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public List<PoliceClassService> getChildren() {
		return children;
	}

	public void setChildren(List<PoliceClassService> children) {
		this.children = children;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public boolean isIsdelay() {
		return isdelay;
	}

	public void setIsdelay(boolean isdelay) {
		this.isdelay = isdelay;
	}

	public boolean isIsopen() {
		return isopen;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}

	public PoliceClassService getParent() {
		return parent;
	}

	public void setParent(PoliceClassService parent) {
		this.parent = parent;
	}

	public int getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(int isProxy) {
		this.isProxy = isProxy;
	}

	public Long getBusinessnoticeId() {
		return businessnoticeId;
	}

	public void setBusinessnoticeId(Long businessnoticeId) {
		this.businessnoticeId = businessnoticeId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

}
