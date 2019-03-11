package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Core_Police;

public interface IPoliceService {

	//增加警种
	void savePolice(Core_Police core_Police);
	//删除警种
	void deletePolice(String pcCode);
	//修改警种
	void updatePolice(Core_Police police,String id);
	//警种列表
	List<Core_Police> getPoliceList();
	//单个警种对象
	Core_Police getObjectByCode(String pcCode);
	//模糊查询
	List<Core_Police> getObjectByName(String pcName);
	//假删除（将isdel修改为1）
	void updatePoliceIsdel(@Param("id") String id);
}
