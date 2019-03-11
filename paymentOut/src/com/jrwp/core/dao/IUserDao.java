package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_User;

public interface IUserDao {

	/**
	 * 增加用户
	 * 
	 * @param role
	 * 
	 */

	Long getdeptid(String username);

	void add(Core_User user);

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
	List<Core_User> list(@Param("whereSql") String whereSql);

	List<Core_User> list1(@Param("whereSql") String whereSql,@Param("dept2") String dept2);

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
	 * 根据用户名查找用户
	 * 
	 * @param name
	 * @return
	 */
	Core_User getByUserName(String name);

	// 保存用户
	void save(Core_User user);

	// 储存用户角色
	void addRoles(@Param("userid") Long userid, @Param("roleid") Long roleid);

	// 根据id移除用户所属角色
	void removeRolesById(Long id);

	// 根据角色名查询角色的id
	Long getRoleIdByRoleName(String rolename);

	// 设置用户的启动状态
	void setStart(@Param("id") long id, @Param("isstart") int isstart);

	/**
	 * 登录保存
	 * 
	 * @param user
	 */
	void updateForLogin(Core_User user);
	
	Core_User getObjectByName(String username);
}
