package com.jrwp.payMent.entity;

import java.util.List;

/**
 * 业务类型表
 * 
 * @author hsl
 * 
 */
public class BusinessconfigType {

	private Long id;// 主键
	private String serCode;// 业务编码
	private String applyTypeName;// 类型名称
	private String orderCode;// 排序码
	private Boolean isValid;// 是否有效

	private PoliceClassService policeClassService;

	private String configName;

	private List<BusinessconfigStuff> stuffs;

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

	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public PoliceClassService getPoliceClassService() {
		return policeClassService;
	}

	public void setPoliceClassService(PoliceClassService policeClassService) {
		this.policeClassService = policeClassService;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public List<BusinessconfigStuff> getStuffs() {
		return stuffs;
	}

	public void setStuffs(List<BusinessconfigStuff> stuffs) {
		this.stuffs = stuffs;
	}

}
