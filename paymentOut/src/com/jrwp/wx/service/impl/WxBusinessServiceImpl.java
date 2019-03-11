package com.jrwp.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.wx.dao.WxBusinessDao;
import com.jrwp.wx.entity.WxBusiness;
import com.jrwp.wx.service.WxBusinessService;

@Service
public class WxBusinessServiceImpl implements WxBusinessService {

	@Resource
	private WxBusinessDao wxBusinessDao;

	@Override
	public List<WxBusiness> list(String code) {
		// TODO Auto-generated method stub
		return wxBusinessDao.list(code);
	}

}
