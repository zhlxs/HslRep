package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Mac_Windows;

public interface IMac_WindowsService {

	List<Mac_Windows> list(@Param("whereSql") String whereSql);

	void save(Mac_Windows mac);

	void update(Mac_Windows mac);

	void delete(@Param("id") long id);

	Mac_Windows getObjectById(@Param("id") Long id);

	List<Mac_Windows> listForDept(Long deptId, String deviceNumber,
			String ckbh, List<String> startCodes);
}
