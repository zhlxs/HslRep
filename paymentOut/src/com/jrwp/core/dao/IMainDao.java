package com.jrwp.core.dao;

import com.jrwp.core.entity.Core_User;

public interface IMainDao {

	/**
	 * 登录
	 * 
	 * @param user
	 * @return
	 */
	Core_User login(Core_User user);

	/**
	 * 注销登录
	 */
	void exit();
}
