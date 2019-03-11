package com.jrwp.core.service;

import com.jrwp.core.entity.Core_User;

public interface IMainService {

	/**
	 * 登录
	 * 
	 * @param user
	 */
	Core_User login(Core_User user);

	/**
	 * 注销登录
	 */
	void exit();

}
