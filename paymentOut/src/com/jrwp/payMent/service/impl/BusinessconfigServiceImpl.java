package com.jrwp.payMent.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.BusinessconfigDao;
import com.jrwp.payMent.dao.IPoliceServiceDao;
import com.jrwp.payMent.entity.Businessconfig;
import com.jrwp.payMent.service.BusinessconfigService;

@Service
public class BusinessconfigServiceImpl implements BusinessconfigService {

	@Resource
	private BusinessconfigDao businessconfigDao;
	@Resource
	private IPoliceServiceDao policeServiceDao;
	
	@Override
	public void save(Businessconfig businessconfig,Long noticeId) {
		// TODO Auto-generated method stub
		if(businessconfig.getId() == null){
			businessconfigDao.save(businessconfig);
			policeServiceDao.updateNotice(noticeId, businessconfig.getSerCode());
		}else{
			businessconfig.setUpdatetime(new Date());
			businessconfigDao.update(businessconfig);
			policeServiceDao.updateNotice(noticeId, businessconfig.getSerCode());
		}
	}

	@Override
	public Businessconfig getObjectByCode(String serCode) {
		// TODO Auto-generated method stub
		return businessconfigDao.getObjectByCode(serCode);
	}

	@Override
	public Businessconfig getObjectById(long id) {
		// TODO Auto-generated method stub
		return businessconfigDao.getObjectById(id);
	}

	@Override
	public void update(Businessconfig businessconfig) {
		// TODO Auto-generated method stub
		businessconfigDao.update(businessconfig);
	}

}
