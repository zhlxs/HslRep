package com.jrwp.payMent.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.CallConfigModelDao;
import com.jrwp.payMent.dao.CallConfigParamDao;
import com.jrwp.payMent.entity.CallConfigModel;
import com.jrwp.payMent.entity.CallConfigParam;
import com.jrwp.payMent.service.CallConfigModelService;

@Service
public class CallConfigModelServiceImpl implements CallConfigModelService {

	@Resource
	private CallConfigModelDao configModelDao;
	@Resource
	private CallConfigParamDao paramDao;

	@Override
	public List<CallConfigModel> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return configModelDao.list(whereSql);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void save(CallConfigModel model) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (model.getId() == null) {
				configModelDao.save(model);
				List<CallConfigParam> list = paramDao.listByModelIdDefault(0l);
				for (CallConfigParam param : list) {
					param.setId(null);
					param.setUpdatetime(new Date());
					param.setModelId(model.getId());
					paramDao.saveParam(param);
				}
			} else {
				configModelDao.update(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			throw new Exception();
		}
	}

	@Override
	public void update(CallConfigModel model) {
		// TODO Auto-generated method stub
		configModelDao.update(model);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void delete(long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			configModelDao.delete(id);
			paramDao.deleteByModelId(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			throw new Exception();
		}
	}

	@Override
	public CallConfigModel getObjectById(Long id) {
		// TODO Auto-generated method stub
		return configModelDao.getObjectById(id);
	}

}
