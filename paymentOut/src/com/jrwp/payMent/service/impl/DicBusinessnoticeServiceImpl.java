package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.DicBusinessnoticeDao;
import com.jrwp.payMent.entity.DicBusinessnotice;
import com.jrwp.payMent.service.DicBusinessnoticeService;
@Service
public class DicBusinessnoticeServiceImpl implements DicBusinessnoticeService {

	@Resource
	private DicBusinessnoticeDao businessnoticeDao;
	
	@Override
	public void save(DicBusinessnotice businessnotice) {
		// TODO Auto-generated method stub
		if(businessnotice.getId() == null){
			businessnoticeDao.save(businessnotice);
		}else{
			businessnoticeDao.update(businessnotice);
		}
	}

	@Override
	public void update(DicBusinessnotice businessnotice) {
		// TODO Auto-generated method stub
		businessnoticeDao.update(businessnotice);
	}

	@Override
	public List<DicBusinessnotice> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return businessnoticeDao.list(whereSql);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		businessnoticeDao.delete(id);
	}

	@Override
	public DicBusinessnotice getObjectById(long id) {
		// TODO Auto-generated method stub
		return businessnoticeDao.getObjectById(id);
	}

}
