package com.jrwp.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrwp.wx.dao.AppraiseDao;
import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.entity.AppraiseJson;
import com.jrwp.wx.service.AppraiseService;

@Service("appraiseService")
public class AppraiseServiceImpl implements AppraiseService{
	
	@Resource
	private AppraiseDao appraiseDao;

	@Override
	public String getSquenceidByCkbh(String jqm,String dateString) {
		// TODO Auto-generated method stub
		return appraiseDao.getSquenceidByCkbh(jqm,dateString);
	}

	@Override
	@Transactional
	public void saveAndUpdateStatu(Long appraisedetailid, int isdefault,
			String squence_infoid) {
		// TODO Auto-generated method stub
		//appraiseDao.saveAppraiseInfo(appraisedetailid,isdefault,squence_infoid);
		appraiseDao.updateWaitStatu(squence_infoid);
	}

	@Override
	public AppraiseJson getAppraiseInfo() {
		// TODO Auto-generated method stub
		return appraiseDao.getAppraiseInfo();
	}

	@Override
	public void updateIsWrite(Long id) {
		// TODO Auto-generated method stub
		appraiseDao.updateIsWrite(id);
	}

	@Override
	public List<AppraiseInfoForAndroid> getAllWaitAppraise(String dateString) {
		// TODO Auto-generated method stub
		return appraiseDao.getAllWaitAppraise(dateString);
	}
}
