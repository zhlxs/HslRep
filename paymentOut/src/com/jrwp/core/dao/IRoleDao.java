package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Role;

public interface IRoleDao {
	/**
	 * 增加角色
	 * 
	 * @param role
	 */
	void add(Core_Role role);

	/**
	 * 修改角色
	 * 
	 * @param role
	 */
	void update(Core_Role role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	void delete(@Param("id") long id);

	/**
	 * 角色列表
	 * 
	 * @param whereSql
	 * 
	 * @return
	 */
	List<Core_Role> list(@Param("whereSql") String whereSql);

	/**
	 * 根据角色id查找角色
	 * 
	 * @param id
	 * @return
	 */
	Core_Role getObjectById(long id);

	/**
	 * 添加角色权限
	 * 
	 * @param id
	 * @param authId
	 * @param authName
	 */
	void addAuth(@Param("roleId") long id, @Param("authId") String authId,
			@Param("authName") String authName);

	/**
	 * 移除角色所有权限
	 * 
	 * @param id
	 */
	void removeAuth(long id);

	/**
	 * 根据角色id查找角色列表
	 * 
	 * @param authids
	 * @return
	 */
	List<Core_Role> getRoleListByIds(@Param("ids") String ids);
}
