package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Menu;

public interface IMenuDao {
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
	 */
	void delete(long id);

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
	void show(@Param("id") long id, @Param("isShow") int isShow);

	/**
	 * 移动菜单
	 * 
	 * @param id
	 * @param move
	 */
	void move(long id, int move);

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	void save(Core_Menu menu);

	/**
	 * 获得插入数据的orderCode
	 * 
	 * @param parentId
	 * @return
	 */
	String getInsertCode(@Param("parentId") Long parentId);

	/**
	 * 根据用户查找菜单
	 * 
	 * @return
	 */
	List<Core_Menu> getListByUserId(@Param("uid") Long id);
	
	/**
	 * 根据部门的名字查询到部门的id
	 * @param parentDeptName
	 * @return
	 */
	Long getDeptIdByMenuName(@Param("parentMenuName") String parentMenuName);

	/**
	 * 系统级用户的菜单
	 * 
	 * @return
	 */
	List<Core_Menu> sysList();
}
