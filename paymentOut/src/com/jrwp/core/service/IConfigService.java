package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_Config;

public interface IConfigService {
	/**
	 * 获得系统基本信息
	 * 
	 * @return
	 */
	List<Core_Config> getSysBaseInfo();

	/**
	 * 保存系统基本信息
	 * 
	 * @param config
	 */
	void save(Core_Config config);

}
