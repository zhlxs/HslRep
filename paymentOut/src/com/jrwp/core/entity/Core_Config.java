package com.jrwp.core.entity;

import javax.validation.constraints.Size;

/**
 * 系统参数表
 *
 */
/**
 * @author USER
 * 
 */
public class Core_Config {
	/**
	 * 配置名称 配置名称不能为空 配置名称不能超过30字符
	 */
	@Size(min=1,max=30,message="{Core_Config.configName.length}")
	private String configName;
	/**
	 * 参数json字符串 参数json字符串不能为空 参数json字符串不能超过1000字符
	 */
	@Size(min=1,max=1000,message="{Core_Config.configJson.length}")
	private String configJson;

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

}
