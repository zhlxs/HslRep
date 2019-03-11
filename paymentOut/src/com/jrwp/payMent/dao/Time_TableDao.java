package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.Time_Table;

public interface Time_TableDao {

	List<Time_Table> listforModel(long modelId);

	void save(Time_Table timeTable);

	void delete(long modelId);

	Time_Table getObjectById(@Param("id") Long id);
}
