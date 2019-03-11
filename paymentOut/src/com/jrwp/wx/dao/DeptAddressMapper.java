package com.jrwp.wx.dao;

import com.jrwp.wx.entity.DeptAddress;

import javax.validation.constraints.Null;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptAddressMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Short id);

	List<DeptAddress> selectByDeptid(String code);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	int insert(DeptAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	int insertSelective(DeptAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	DeptAddress selectByPrimaryKey(Short id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(DeptAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table DEPT_ADDRESS
	 * 
	 * @mbggenerated
	 */
	int updateByPrimaryKey(DeptAddress record);
	
	DeptAddress getObjectById(@Param("id")Long id);
}