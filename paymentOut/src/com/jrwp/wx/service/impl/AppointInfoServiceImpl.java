package com.jrwp.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.wx.dao.AppointInfoDao;
import com.jrwp.wx.entity.AppointInfo;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.AppointInfoService;

@Service
public class AppointInfoServiceImpl implements AppointInfoService {

	@Resource
	private AppointInfoDao appointInfoDao;

	@Override
	public List<AppointInfo> getInfo(String openId) {
		// TODO Auto-generated method stub
		return appointInfoDao.getInfo(openId);
	}

	@Override
	public WXSquenceInfo getSequenceByAppointId(Long appointme_infoid) {
		// TODO Auto-generated method stub
		return appointInfoDao.getSequenceByAppointId(appointme_infoid);
	}

}
