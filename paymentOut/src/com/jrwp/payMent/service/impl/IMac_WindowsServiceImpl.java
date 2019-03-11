package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.IMac_WindowsDao;
import com.jrwp.payMent.entity.Mac_Windows;
import com.jrwp.payMent.service.IMac_WindowsService;

@Service
public class IMac_WindowsServiceImpl implements IMac_WindowsService {

	@Resource
	private IMac_WindowsDao macWindowsDao;

	@Override
	public List<Mac_Windows> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return macWindowsDao.list(whereSql);
	}

	@Override
	public void save(Mac_Windows mac) {
		// TODO Auto-generated method stub
		if (mac.getId() == null) {
			macWindowsDao.save(mac);
		} else {
			macWindowsDao.update(mac);
		}
	}

	@Override
	public void update(Mac_Windows mac) {
		// TODO Auto-generated method stub
		macWindowsDao.update(mac);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		macWindowsDao.delete(id);
	}

	@Override
	public Mac_Windows getObjectById(Long id) {
		// TODO Auto-generated method stub
		return macWindowsDao.getObjectById(id);
	}

	@Override
	public List<Mac_Windows> listForDept(Long deptId, String deviceNumber,
			String ckbh, List<String> startCodes) {
		// TODO Auto-generated method stub
		return macWindowsDao
				.listForDept(deptId, deviceNumber, ckbh, startCodes);
	}

}
