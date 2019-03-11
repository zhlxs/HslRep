package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.Time_ModelDao;
import com.jrwp.payMent.dao.Time_TableDao;
import com.jrwp.payMent.entity.Time_Model;
import com.jrwp.payMent.entity.Time_Table;
import com.jrwp.payMent.service.Time_ModelService;

@Service
public class Time_ModelServiceImpl implements Time_ModelService {

	@Resource
	private Time_ModelDao timeModelDao;
	@Resource
	private Time_TableDao timeTableDao;

	@Override
	public List<Time_Model> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return timeModelDao.list(whereSql);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})//事务控制
	public void save(Time_Model timeModel,List<Time_Table> tables) throws Exception {
		// TODO Auto-generated method stub
		try {
			if(timeModel.getId() == null){//新增
				timeModelDao.save(timeModel);
				for(int i = 0; i < tables.size(); i ++){
					tables.get(i).setModelId(timeModel.getId());
					tables.get(i).setIsValid(1);
					timeTableDao.save(tables.get(i));
				}
			}else{//修改
				timeModelDao.update(timeModel);
				/**
				 * 先删除子节点
				 */
				timeTableDao.delete(timeModel.getId());
				for(int i = 0; i < tables.size(); i ++){
					tables.get(i).setModelId(timeModel.getId());
					tables.get(i).setIsValid(1);
					timeTableDao.save(tables.get(i));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new Exception();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})//事务控制
	public void delete(long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			timeModelDao.delete(id);
			timeTableDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new Exception();
		}
	}

	@Override
	public Time_Model getObjectById(long id) {
		// TODO Auto-generated method stub
		return timeModelDao.getObjectById(id);
	}

}
