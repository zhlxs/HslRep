package com.jrwp.payMent.dao;

import java.util.List;

import com.jrwp.payMent.entity.DeptTimesAuth;

public interface IDeptTimesAuthDao {

	List<DeptTimesAuth> list();

	DeptTimesAuth getObjectById(Long id);

	void save(DeptTimesAuth deptTimesAuth);

	void updateById(DeptTimesAuth deptTimesAuth);

	void delete(Long id);
}
