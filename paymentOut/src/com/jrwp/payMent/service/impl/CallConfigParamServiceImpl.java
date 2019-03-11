package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.CallConfigModelDao;
import com.jrwp.payMent.dao.CallConfigParamDao;
import com.jrwp.payMent.entity.CallConfigParam;
import com.jrwp.payMent.service.CallConfigParamService;

@Service
public class CallConfigParamServiceImpl implements CallConfigParamService {

	@Resource
	private CallConfigParamDao configParamDao;

	@Override
	public List<CallConfigParam> listByModelId(Long modelId) {
		// TODO Auto-generated method stub
		// QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		// String whereSql = QueryHelper
		// .getWhereSql("", queryInfo.getWhereInfos());
		return configParamDao.listByModelId(modelId);
	}

	@Override
	public void save(CallConfigParam param) {
		// TODO Auto-generated method stub
		if(param.getParentId() == null){
			param.setParentId(0l);
		}
		if (param.getId() == null) {
			String code = configParamDao
					.getInsertCode(param.getParentId());
			if (code == null) {
				CallConfigParam configParam = configParamDao
						.getObjectById(param.getParentId());
				StringBuffer stringBuffer = new StringBuffer();
				if(configParam == null){
					stringBuffer.append("");
				}else{
					stringBuffer.append(configParam.getOrderCode());
				}
				for (int i = 0; i < 5; i++) {
					stringBuffer.append("0");
				}
				code = stringBuffer.toString();
			}
			String insertOrderCode = getInsertOrderCode(code);
			param.setOrderCode(insertOrderCode);
			configParamDao.save(param);
		} else {
			configParamDao.update(param);
		}
	}

	@Override
	public void update(CallConfigParam param) {
		// TODO Auto-generated method stub
		configParamDao.update(param);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		configParamDao.delete(id);
	}

	@Override
	public CallConfigParam getObjectById(Long id) {
		// TODO Auto-generated method stub
		return configParamDao.getObjectById(id);
	}

	// 获取下一个排序码
	public static String getInsertOrderCode(String code) {
		String s = code.substring(code.length() - 5, code.length());
		StringBuffer SB = new StringBuffer();
		SB.append(String.valueOf(Integer.parseInt(s) + 1));
		while (SB.length() < 5) {
			SB.insert(0, "0");
		}
		SB.insert(0, code.substring(0, code.length() - 5));
		return SB.toString();
	}

	@Override
	public List<CallConfigParam> listByModelIdDefault(Long modelId) {
		// TODO Auto-generated method stub
		return configParamDao.listByModelIdDefault(modelId);
	}

	@Override
	public void saveParam(CallConfigParam param) {
		// TODO Auto-generated method stub
		configParamDao.saveParam(param);
	}

}
