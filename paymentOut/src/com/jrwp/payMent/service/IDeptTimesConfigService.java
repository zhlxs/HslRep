package com.jrwp.payMent.service;

import java.util.List;

import com.jrwp.payMent.entity.DeptTimesConfig;
import com.jrwp.payMent.entity.DeptTimesConfigJson;

public interface IDeptTimesConfigService {

	List<DeptTimesConfigJson> list(Long deptId, String deptname,
			List<String> startCodes);

	DeptTimesConfig getObjectById(Long id);

	void save(DeptTimesConfig deptTimesConfig);

	void update(DeptTimesConfig deptTimesConfig);
}
