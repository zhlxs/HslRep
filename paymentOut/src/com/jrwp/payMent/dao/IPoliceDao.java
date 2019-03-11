package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Core_Police;

public interface IPoliceDao {

	/**
	 * 增加警种
	 * @param core_Police
	 */
	void savePolice(Core_Police core_Police);
	/**
	 * 删除警种
	 * @param pcCode
	 */
	void deletePolice(@Param("pcCode")String pcCode);
	/**
	 * 修改警种
	 * @param pcCode
	 */
	void updatePolice(@Param("pcName")String pcName,@Param("id")String id);
	/**
	 * 警种列表
	 * @return
	 */
	List<Core_Police> getPoliceList();
	/**
	 * 单个警种对象
	 * @param pcCode
	 * @return
	 */
	Core_Police getObjectByCode(String pcCode);
	/**
	 * 模糊查询
	 * @param pcName
	 * @return
	 */
	List<Core_Police> getObjectByName(@Param("pcName")String pcName);
	
	/*
	 * 假删除（将isdel修改为1）
	 * 
	 * */
	void updatePoliceIsdel(@Param("id") String id);
}
