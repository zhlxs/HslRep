package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_Role;

/**
 * 角色Service接口
 * 
 * @author USER
 * 
 */
public interface IRoleService {
	/**
	 * 增加角色
	 * 
	 * @param role
	 * @param authIDs
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
	void delete(long id);

	/**
	 * 角色列表
	 * 
	 * @param queryinfo
	 * 
	 * @return
	 */
	List<Core_Role> list(String queryinfo);

	/**
	 * 根据角色id查找角色
	 * 
	 * @param id
	 * @return
	 */
	Core_Role getObjectById(long id);

	/**
	 * 保存角色
	 * 
	 * @param role
	 * @param authIDs
	 */
	void save(Core_Role role, String authIDs, String authNames);

	/**
	 * 根据角色id查找角色列表
	 * 
	 * @param authids
	 * @return
	 */
	List<Core_Role> getRoleListByIds(String roleids);

}
