package com.jrwp.wx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.wx.dao.RegisterUserDao;
import com.jrwp.wx.entity.RegisterUser;
import com.jrwp.wx.service.RegisterUserService;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	@Resource
	private RegisterUserDao registerUserDao;

	@Override
	public void save(RegisterUser user) {
		// TODO Auto-generated method stub
		registerUserDao.save(user);
	}

	@Override
	public void updateUser(RegisterUser user) {
		// TODO Auto-generated method stub
		registerUserDao.updateUser(user);
	}

	@Override
	public RegisterUser getObjectByOpenId(String wxOpenId) {
		// TODO Auto-generated method stub
		return registerUserDao.getObjectByOpenId(wxOpenId);
	}

	@Override
	public RegisterUser getObjectById(long id) {
		// TODO Auto-generated method stub
		return registerUserDao.getObjectById(id);
	}

	@Override
	public RegisterUser checkObjectByCard(String idCardNumber) {
		// TODO Auto-generated method stub
		return registerUserDao.checkObjectByCard(idCardNumber);
	}

	@Override
	public String getCardByOpenId(String wxOpenId) {
		return registerUserDao.getCardByOpenId(wxOpenId);
	}
}
