package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.IAccountManagerDao;
import com.jrwp.payMent.entity.AccountManager;
import com.jrwp.payMent.service.IAccountManagerService;

@Service
public class IAccountManagerServiceImpl implements IAccountManagerService {

	@Resource
	private IAccountManagerDao accountManagerDao;

	@Override
	public List<AccountManager> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return accountManagerDao.list(whereSql);
	}

	@Override
	public void save(AccountManager account) {
		// TODO Auto-generated method stub
		accountManagerDao.save(account);
	}

	@Override
	public void update(AccountManager account) {
		// TODO Auto-generated method stub
		accountManagerDao.update(account);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		accountManagerDao.delete(id);
	}

	@Override
	public AccountManager getObjectById(Long id) {
		// TODO Auto-generated method stub
		return accountManagerDao.getObjectById(id);
	}

}
