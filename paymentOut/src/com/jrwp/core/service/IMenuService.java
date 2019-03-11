package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_Menu;

public interface IMenuService {
	/**
	 * 增加菜单
	 * 
	 * @param role
	 */
	void add(Core_Menu menu);

	/**
	 * 修改菜单
	 * 
	 * @param role
	 */
	void update(Core_Menu menu);

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(long id) throws Exception;

	/**
	 * 菜单列表
	 * 
	 * @return
	 */
	List<Core_Menu> list();

	/**
	 * 根据菜单id查找菜单
	 * 
	 * @param id
	 * @return
	 */
	Core_Menu getObjectById(long id);

	/**
	 * 菜单显示状态
	 * 
	 * @param id
	 * @param isShow
	 */
	void show(long id, int isShow);

	/**
	 * 移动菜单
	 * 
	 * @param id
	 * @param move
	 * @throws Exception
	 */
	void move(long id, int move) throws Exception;

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	void save(Core_Menu menu,String parentMenuName);

	/**
	 * 根据用户查找菜单
	 * 
	 * @return
	 */
	List<Core_Menu> getListByUserId(Long id);

	/**
	 * 系统级用户的菜单
	 * 
	 * @return
	 */
	List<Core_Menu> syslist();
}
