package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.BusinessconfigType;

public interface BusinessconfigTypeDao {

	/**
	 * 根据业务编码查询其业务分类
	 * 
	 * @param serCode
	 * @return
	 */
	List<BusinessconfigType> listChild(String serCode);

	List<BusinessconfigType> listChildForWx(@Param("serCode") String serCode);

	void save(BusinessconfigType businessconfigType);

	String getMaxCode();

	void update(BusinessconfigType businessconfigType);

	BusinessconfigType getObjectById(long id);

	void delete(long id);
}
