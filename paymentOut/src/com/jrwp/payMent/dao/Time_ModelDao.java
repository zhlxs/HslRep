package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Time_Model;

public interface Time_ModelDao {

	List<Time_Model> list(@Param("whereSql")String whereSql);
	
	void save(Time_Model timeModel);
	
	void delete(long id);
	
	Time_Model getObjectById(long id);
	
	void update(Time_Model model);
}
