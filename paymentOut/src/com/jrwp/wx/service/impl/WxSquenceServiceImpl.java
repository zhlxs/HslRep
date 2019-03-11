package com.jrwp.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.wx.dao.WxSquenceDao;
import com.jrwp.wx.entity.WxPmVistor;
import com.jrwp.wx.service.WxSquenceService;

@Service("wxSquenceService")
public class WxSquenceServiceImpl implements WxSquenceService{

	@Resource
	private WxSquenceDao wxSquenceDao;
	
	@Override
	public int getZhWaitCount(String dateString, Long deptid) {
		// TODO Auto-generated method stub
		return wxSquenceDao.getZhWaitCount(dateString, deptid);
	}

	@Override
	public List<WxPmVistor> getSquenceNumber(String dateString, Long deptid) {
		// TODO Auto-generated method stub
		return wxSquenceDao.getSquenceNumber(dateString, deptid);
	}

	@Override
	public int getDbWaitCount(String dateString, Long deptid) {
		// TODO Auto-generated method stub
		return wxSquenceDao.getDbWaitCount(dateString, deptid);
	}

	@Override
	public List<WxPmVistor> getDbSquenceNumber(String dateString, Long deptid) {
		// TODO Auto-generated method stub
		return wxSquenceDao.getDbSquenceNumber(dateString, deptid);
	}

	@Override
	public List<WxPmVistor> getAllSquenceInfo(Long deptid, String dateString) {
		// TODO Auto-generated method stub
		return wxSquenceDao.getAllSquenceInfo(deptid, dateString);
	}

}
