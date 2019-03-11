package com.jrwp.payMent.entity;
/**
 * 警种实体
 * @author hsl
 *
 */
public class Core_Police {
	// 属性

	private String pcCode;
	private String pcName;
	private String isDel;

	// 构造
	public Core_Police() {
	}

	// 封装
	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}

	public String getPcName() {
		return pcName;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

}
