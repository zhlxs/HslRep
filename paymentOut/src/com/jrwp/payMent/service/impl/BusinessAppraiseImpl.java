package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.BusinessAppraiseDao;
import com.jrwp.payMent.entity.BusinessAppraise;
import com.jrwp.payMent.service.BusinessAppraiseService;

@Service
public class BusinessAppraiseImpl implements BusinessAppraiseService {

	@Resource
	private BusinessAppraiseDao businessAppraiseDao;

	@Override
	public List<BusinessAppraise> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return businessAppraiseDao.list(whereSql);
	}

	@Override
	public void save(BusinessAppraise businessAppraise) {
		// TODO Auto-generated method stub
		businessAppraiseDao.save(businessAppraise);
	}

}
