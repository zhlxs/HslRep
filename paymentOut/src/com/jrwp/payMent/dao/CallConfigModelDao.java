package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallConfigModel;

public interface CallConfigModelDao {

	List<CallConfigModel> list(@Param("whereSql")String whereSql);
	
	void save(CallConfigModel model);
	
	void update(CallConfigModel model);
	
	void delete(@Param("id")long id);
	
	CallConfigModel getObjectById(@Param("id")Long id);
}
