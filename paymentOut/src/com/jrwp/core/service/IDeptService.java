package com.jrwp.core.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Dept;

/**
 * @author USER
 * 
 */
public interface IDeptService {
	List<Core_Dept> findAlldept(long id);
	Integer getsize( Long parentid);
	List<Core_Dept> getNextLevel( Long parentid);
	List<Core_Dept> getDeptsBycode(String code);
	List<Core_Dept> getTopServices(Long parentid,Integer star, Integer end);
	Core_Dept getBydeptCode(String deptcode);

	List<Core_Dept> listByLevel(Integer length);

	List<Core_Dept> getDepts();
	
	List<Core_Dept> getDeptByname(String deptname);

	String[] getAllchild(String code);
	List<Integer> getchildsid( Integer deptid);
	Integer countchild( Integer parentid);
	/**
	 * 增加部门
	 * 
	 * @param role
	 */
	void add(Core_Dept dept);

	/**
	 * 修改部门
	 * 
	 * @param role
	 */
	void update(Core_Dept dept);

	/**
	 * 删除部门
	 * 
	 * @param id
	 */
	void delete(long id) throws Exception;

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	List<Core_Dept> list(String queryinfo);

	/**
	 * 根据部门id查找部门
	 * 
	 * @param id
	 * @return
	 */
	Core_Dept getObjectById(long id);

	/**
	 * 部门显示状态
	 * 
	 * @param id
	 * @param isShow
	 */
	void show(Long id, int isShow);

	/**
	 * 移动部门
	 * 
	 * @param id
	 * @param move
	 */
	void move(long id, int movetype) throws Exception;

	/**
	 * 部门树
	 * 
	 * @return
	 */
	List<Core_Dept> tree(String queryinfo);

	/**
	 * 根据父节点查询子节点
	 * 
	 * @param id
	 * @return
	 */
	List<Core_Dept> list1(long id);

	// 保存部门
	void save(Core_Dept dept, String parentDeptName);

	/**
	 * 根据部门的名字查询到部门的id
	 * 
	 * @param parentDeptName
	 * @return
	 */
	Long getDeptIdByDeptName(String parentDeptName);

	/**
	 * 根据部门的名字查询到部门的编号
	 * 
	 * @param parentDeptName
	 * @return
	 */
	String getDeptCode(String parentDeptName);

	void updateOtherName(String newName, String deptid);
}
