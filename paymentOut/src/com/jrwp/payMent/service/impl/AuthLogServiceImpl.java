package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IAuthLogDao;
import com.jrwp.payMent.entity.AuthLog;
import com.jrwp.payMent.service.IAuthLogService;

@Service
public class AuthLogServiceImpl implements IAuthLogService {

	@Resource
	private IAuthLogDao authLogDao;

	@Override
	public void save(AuthLog log) {
		// TODO Auto-generated method stub
		authLogDao.saveAuthLog(log);
	}

	@Override
	public List<AuthLog> list() {
		// TODO Auto-generated method stub
		return authLogDao.list();
	}

	@Override
	public AuthLog getObjectById(Long id) {
		// TODO Auto-generated method stub
		return authLogDao.getObjectById(id);
	}
}
