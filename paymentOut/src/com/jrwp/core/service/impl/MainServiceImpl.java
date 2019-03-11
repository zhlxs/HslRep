package com.jrwp.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IMainDao;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.service.IMainService;

@Service("mainService")
public class MainServiceImpl implements IMainService {

	@Resource
	private IMainDao mainDao;

	@Override
	public Core_User login(Core_User user) {
		return mainDao.login(user);
	}

	@Override
	public void exit() {
		mainDao.exit();
	}

}
