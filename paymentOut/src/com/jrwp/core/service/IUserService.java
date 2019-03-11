package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_User;

public interface IUserService {

	/**
	 * 增加用户
	 * 
	 * @param role
	 */
	void add(Core_User user);

	Long getdeptid(String username);

	/**
	 * 修改用户
	 * 
	 * @param role
	 */
	void update(Core_User user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	void delete(long id);

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	List<Core_User> list(String queryinfo);

	/**
	 * 根据用户id查找用户
	 * 
	 * @param id
	 * @return
	 */
	Core_User getObjectById(long id);

	/**
	 * 根据部门id查找用户
	 * 
	 * @param deptId
	 * @return
	 */
	List<Core_User> getDeptId(long deptId);

	/**
	 * 设置用户状态
	 * 
	 * @param id
	 * @param state
	 */
	void setStart(long id, int isstart);

	/**
	 * 部门人员树
	 * 
	 * @return
	 */
	List<Core_User> tree(String queryinfo);

	List<Core_User> list1(String whereSql, String dept2);

	/**
	 * 根据用户名查找用户
	 * 
	 * @param currentUsername
	 * @return
	 */
	Core_User getByUserName(String name);

	// 保存用户
	void save(Core_User user, String deptName, String roleNames);

	/**
	 * 登录保存
	 * 
	 * @param user
	 */
	void updateForLogin(Core_User user);

	/**
	 * 根据用户名获取pc_code
	 * 
	 * @param username
	 * @return
	 */
	Core_User getObjectByName(String username);

}
