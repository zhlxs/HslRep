package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IDeptTimesConfigDao;
import com.jrwp.payMent.entity.DeptTimesConfig;
import com.jrwp.payMent.entity.DeptTimesConfigJson;
import com.jrwp.payMent.service.IDeptTimesConfigService;

@Service
public class DeptTimesConfigServiceImpl implements IDeptTimesConfigService {

	@Resource
	private IDeptTimesConfigDao deptTimesConfigDao;

	@Override
	public List<DeptTimesConfigJson> list(Long deptId, String deptname,
			List<String> startCodes) {
		// TODO Auto-generated method stub
		return deptTimesConfigDao.list(deptId, deptname, startCodes);
	}

	@Override
	public DeptTimesConfig getObjectById(Long id) {
		// TODO Auto-generated method stub
		return deptTimesConfigDao.getObjectById(id);
	}

	@Override
	public void save(DeptTimesConfig deptTimesConfig) {
		// TODO Auto-generated method stub
		deptTimesConfigDao.save(deptTimesConfig);
	}

	@Override
	public void update(DeptTimesConfig deptTimesConfig) {
		// TODO Auto-generated method stub
		deptTimesConfigDao.update(deptTimesConfig);
	}

}
