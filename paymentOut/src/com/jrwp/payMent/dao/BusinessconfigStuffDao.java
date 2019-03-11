package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.BusinessconfigStuff;

public interface BusinessconfigStuffDao {

	/**
	 * 根据申请类型id获取列表
	 */
	List<BusinessconfigStuff> listChildren(Long id);

	void save(BusinessconfigStuff businessconfigStuff);

	String getMaxCode();

	void update(BusinessconfigStuff businessconfigStuff);

	BusinessconfigStuff getObjectById(long id);

	void delete(long id);

	void updateordercode(@Param("ordercode") String ordercode,
			@Param("id") long id);
}
