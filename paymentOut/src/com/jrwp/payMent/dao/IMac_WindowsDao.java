package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Mac_Windows;

public interface IMac_WindowsDao {

	List<Mac_Windows> list(@Param("whereSql") String whereSql);

	void save(Mac_Windows mac);

	void update(Mac_Windows mac);

	void delete(@Param("id") long id);

	Mac_Windows getObjectById(@Param("id") Long id);

	List<Mac_Windows> listForDept(@Param("deptId") Long deptId,
			@Param("deviceNumber") String deviceNumber,
			@Param("ckbh") String ckbh,
			@Param("startCodes") List<String> startCodes);
}
