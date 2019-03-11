package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.ILogDao;
import com.jrwp.core.entity.Core_Log;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.ILogService;

@Service("logService")
public class LogServiceImpl implements ILogService {
	@Resource
	private ILogDao logDao;

	@Override
	public List<Core_Log> list(String queryinfo,String begintime,String endtime) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return logDao.list(whereSql,begintime,endtime);
	}

	@Override
	public Core_Log getObjectById(long id) {
		return logDao.getObjectById(id);
	}

	@Override
	public void save(Core_Log log) {
		logDao.save(log);
	}
}
