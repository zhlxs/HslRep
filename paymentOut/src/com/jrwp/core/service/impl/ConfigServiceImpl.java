package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IConfigDao;
import com.jrwp.core.entity.Core_Config;
import com.jrwp.core.service.IConfigService;

@Service("configService")
public class ConfigServiceImpl implements IConfigService {
	@Resource
	private IConfigDao configDao;

	@Override
	public List<Core_Config> getSysBaseInfo() {
		return configDao.getSysBaseInfo();
	}

	@Override
	public void save(Core_Config config) {
		configDao.save(config);
	}
}
