package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_Auth;

public interface IAuthService {
	// 获取权限列表
	List<Core_Auth> list(String queryinfo);

	// 添加权限
	void add(Core_Auth core_Auth);

	// 编辑权限
	void update(Core_Auth core_Auth);

	// 删除权限
	void delete(Long id);

	/**
	 * 根据角色id获得权限
	 * 
	 * @param parseLong
	 * @return
	 */
	List<Core_Auth> getAuthListByRoleId(long parseLong);

	/**
	 * 根据权限id查找权限列表
	 * 
	 * @param authids
	 * @return
	 */
	List<Core_Auth> getAuthListByIds(String authids);

	/**
	 * 保存角色
	 * 
	 * @param auth
	 * @param actionValues
	 * @param actionNames
	 */
	void save(Core_Auth core_Auth, String actionValues, String actionNames);

	/**
	 * 根据id查找权限
	 * 
	 * @param id
	 * @return
	 */
	Core_Auth getObjectById(long id);
}
