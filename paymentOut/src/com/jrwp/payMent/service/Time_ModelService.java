package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Time_Model;
import com.jrwp.payMent.entity.Time_Table;

public interface Time_ModelService {

	List<Time_Model> list(@Param("whereSql") String whereSql);

	void save(Time_Model timeModel,List<Time_Table> tables) throws Exception;
	
	void delete(long id) throws Exception;

	Time_Model getObjectById(long id);
}
