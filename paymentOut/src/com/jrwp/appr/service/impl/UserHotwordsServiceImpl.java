package com.jrwp.appr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.appr.dao.UserHotwordsDao;
import com.jrwp.appr.entity.UserHotwords;
import com.jrwp.appr.service.UserHotwordsService;
@Service
public class UserHotwordsServiceImpl implements UserHotwordsService{

	@Resource
	private UserHotwordsDao userHotwordsDao;
	
	@Override
	public void save(UserHotwords userHotwords) {
		// TODO Auto-generated method stub
		userHotwordsDao.save(userHotwords);
	}

}
