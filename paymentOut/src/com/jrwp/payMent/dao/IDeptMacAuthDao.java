package com.jrwp.payMent.dao;

import java.util.List;

import com.jrwp.payMent.entity.DeptMacAuth;

public interface IDeptMacAuthDao {

	List<DeptMacAuth> list();

	DeptMacAuth getObjectById(Long id);

	void save(DeptMacAuth deptMacAuth);

	void updateById(DeptMacAuth deptMacAuth);

	void delete(Long id);
}
