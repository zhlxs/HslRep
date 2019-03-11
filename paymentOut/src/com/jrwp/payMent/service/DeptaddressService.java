package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.DicDeptaddress;

public interface DeptaddressService {

	List<DicDeptaddress> list(@Param("whereSql") String whereSql);

	void save(DicDeptaddress deptaddress);

	void update(DicDeptaddress deptaddress);

	DicDeptaddress getObjectById(@Param("id") Long id);

	DicDeptaddress getObjectByDeptId(@Param("deptId") Long deptId);

	void delete(@Param("id") Long id);
}
