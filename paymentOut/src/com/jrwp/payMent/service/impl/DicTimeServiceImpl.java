package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.DicTimeDao;
import com.jrwp.payMent.entity.DicTime;
import com.jrwp.payMent.service.DicTimeService;

@Service
public class DicTimeServiceImpl implements DicTimeService {

	@Resource
	private DicTimeDao dicTimeDao;

	@Override
	public List<DicTime> list() {
		// TODO Auto-generated method stub
		return dicTimeDao.list();
	}

	@Override
	public DicTime getObjectById(Long id) {
		// TODO Auto-generated method stub
		return dicTimeDao.getObjectById(id);
	}

}
