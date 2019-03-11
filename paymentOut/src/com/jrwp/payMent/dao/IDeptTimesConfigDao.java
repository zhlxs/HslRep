package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.DeptTimesConfig;
import com.jrwp.payMent.entity.DeptTimesConfigJson;

public interface IDeptTimesConfigDao {

	List<DeptTimesConfigJson> list(@Param("deptId") Long deptId,
			@Param("deptname") String deptname,
			@Param("startCodes") List<String> startCodes);

	DeptTimesConfig getObjectById(Long id);

	void save(DeptTimesConfig deptTimesConfig);

	void update(DeptTimesConfig deptTimesConfig);

}
