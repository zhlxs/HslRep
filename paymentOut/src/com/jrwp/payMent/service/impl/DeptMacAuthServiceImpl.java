package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IDeptMacAuthDao;
import com.jrwp.payMent.entity.DeptMacAuth;
import com.jrwp.payMent.service.IDeptMacAuthService;

@Service
public class DeptMacAuthServiceImpl implements IDeptMacAuthService {

	@Resource
	private IDeptMacAuthDao deptMacAuthDao;

	@Override
	public List<DeptMacAuth> list() {
		// TODO Auto-generated method stub
		return deptMacAuthDao.list();
	}

	@Override
	public DeptMacAuth getObjectById(Long id) {
		// TODO Auto-generated method stub
		return deptMacAuthDao.getObjectById(id);
	}

	@Override
	public void save(DeptMacAuth deptMacAuth) {
		// TODO Auto-generated method stub
		deptMacAuthDao.save(deptMacAuth);
	}

	@Override
	public void updateById(DeptMacAuth deptMacAuth) {
		// TODO Auto-generated method stub
		deptMacAuthDao.updateById(deptMacAuth);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
