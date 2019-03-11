package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Auth;

public interface IAuthDao {
	// 获取权限列表
	List<Core_Auth> list(@Param("whereSql") String whereSql);

	// 增加权限
	void add(Core_Auth core_Auth);

	// 修改编辑权限
	void update(Core_Auth core_Auth);

	// 删除权限的方法
	void delete(Long id);

	/**
	 * 根据角色id获得权限
	 * 
	 * @param parseLong
	 * @return
	 */
	List<Core_Auth> getAuthListByRoleId(long roleid);

	/**
	 * 根据权限id查找权限列表
	 * 
	 * @param string
	 * @return
	 */
	List<Core_Auth> getAuthListByIds(@Param("ids") String ids);

	/**
	 * 
	 * 删除权限的所有动作
	 * 
	 * @param id
	 */
	void removeAction(Long id);

	/**
	 * 添加权限的动作
	 * 
	 * @param id
	 * @param value
	 * @param name
	 */
	void addAction(@Param("authId") Long authId,
			@Param("contro") String contro, @Param("action") String action,
			@Param("display") String display);

	/**
	 * 根据id查找权限
	 * 
	 * @param id
	 * @return
	 */
	Core_Auth getObjectById(long id);
}
