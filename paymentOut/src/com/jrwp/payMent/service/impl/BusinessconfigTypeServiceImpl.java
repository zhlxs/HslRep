package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.BusinessconfigTypeDao;
import com.jrwp.payMent.entity.BusinessconfigType;
import com.jrwp.payMent.service.BusinessconfigTypeService;

@Service
public class BusinessconfigTypeServiceImpl implements BusinessconfigTypeService {

	@Resource
	private BusinessconfigTypeDao businessconfigTypeDao;

	@Override
	public List<BusinessconfigType> listChild(String serCode) {
		// TODO Auto-generated method stub
		return businessconfigTypeDao.listChild(serCode);
	}

	@Override
	public void save(BusinessconfigType businessconfigType) {
		// TODO Auto-generated method stub
		String maxCode = businessconfigTypeDao.getMaxCode();
		if (businessconfigType.getId() == null) {
			if (maxCode == null) {
				businessconfigType.setOrderCode("00001");
			} else {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(String.valueOf(Integer.parseInt(maxCode) + 1));
				while (sBuffer.length() < 5) {
					sBuffer.insert(0, "0");
				}
				businessconfigType.setOrderCode(sBuffer.toString());
			}
			businessconfigTypeDao.save(businessconfigType);
		} else {
			businessconfigTypeDao.update(businessconfigType);
		}
	}

	@Override
	public void update(BusinessconfigType businessconfigType) {
		// TODO Auto-generated method stub
		businessconfigTypeDao.update(businessconfigType);
	}

	@Override
	public BusinessconfigType getObjectById(long id) {
		// TODO Auto-generated method stub
		return businessconfigTypeDao.getObjectById(id);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		businessconfigTypeDao.delete(id);
	}

	@Override
	public List<BusinessconfigType> listChildForWx(String serCode) {
		// TODO Auto-generated method stub
		return businessconfigTypeDao.listChildForWx(serCode);
	}

}
