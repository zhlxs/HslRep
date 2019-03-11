package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.TheBlackListDao;
import com.jrwp.payMent.entity.TheBlackList;
import com.jrwp.payMent.service.TheBlackListService;

@Service
public class TheBlackListServiceImpl implements TheBlackListService {

	@Resource
	private TheBlackListDao blackListDao;

	@Override
	public List<TheBlackList> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return blackListDao.list(whereSql);
	}

	@Override
	public void save(TheBlackList blackList) {
		// TODO Auto-generated method stub
		blackListDao.save(blackList);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		blackListDao.delete(id);
	}

}
