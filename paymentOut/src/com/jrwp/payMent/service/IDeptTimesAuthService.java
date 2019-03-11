package com.jrwp.payMent.service;

import java.util.List;

import com.jrwp.payMent.entity.DeptTimesAuth;

public interface IDeptTimesAuthService {

	List<DeptTimesAuth> list();

	DeptTimesAuth getObjectById(Long id);

	void save(DeptTimesAuth deptTimesAuth);

	void updateById(DeptTimesAuth deptTimesAuth, String idCard, String userName);

	void delete(Long id);
}
