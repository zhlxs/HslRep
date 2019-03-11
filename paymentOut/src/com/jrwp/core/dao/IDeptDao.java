package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.entity.Core_User;

public interface IDeptDao {
	Integer getsize(@Param("parentid") Long parentid);
	List<Core_Dept> getNextLevel(@Param("parentid") Long parentid);
	List<Core_Dept> getTopServices(@Param("parentid") Long parentid,@Param("star") Integer star,@Param("end") Integer end);

	List<Integer> getchildsid(@Param("deptid") Integer deptid);
	Integer countchild(@Param("parentid") Integer parentid);
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
	void delete(long id);

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	List<Core_Dept> list(@Param("whereSql") String whereSql);

	List<Core_Dept> listByLevel(Integer length);

	List<Core_Dept> getDepts();
	
	List<Core_Dept> getDeptByname(@Param("deptname") String deptname);

	/**
	 * 根据父节点查询子节点
	 * 
	 * @param id
	 * @return
	 */
	List<Core_Dept> list1(long id);
	List<Core_Dept> findAlldept(long id);

	/**
	 * 根据部门id查找部门
	 * 
	 * @param id
	 * @return
	 */
	Core_Dept getObjectById(long id);
	Core_Dept getBydeptCode(String deptcode);

	/**
	 * 获得插入数据的orderCode
	 * 
	 * @param parentId
	 * @return
	 */
	String getInsertCode(@Param("parentId") Long parentId);

	/**
	 * 系统级用户的菜单
	 * 
	 * @return
	 */
	List<Core_Dept> sysList();

	// 部门显示状态

	void show(@Param("id") Long id, @Param("isShow") int isShow);

	// 保存部门
	void save(Core_Dept dept);

	// 移动部门
	void move(@Param("id") Long id, @Param("neworder") String neworder);

	/**
	 * 根据部门的名字查询到部门的id
	 * 
	 * @param parentDeptName
	 * @return
	 */
	Long getDeptIdByDeptName(@Param("parentDeptName") String parentDeptName);

	/**
	 * 根据部门的名字查询到部门的编号
	 * 
	 * @param parentDeptName
	 * @return
	 */
	String getDeptCode(@Param("parentDeptName") String parentDeptName);

	// 根据部门名字查询部门id
	String getOrderCodeById(@Param("id") Long id);

	void updateOrderCode(@Param("neworder") String neworder,
			@Param("ordercode") String ordercode);

	void updateOtherName(@Param("newName") String newName,
			@Param("deptid") String deptid);

	String[] getAllchild(String code);

	List<Core_Dept> getDeptsBycode(String code);
	
}
