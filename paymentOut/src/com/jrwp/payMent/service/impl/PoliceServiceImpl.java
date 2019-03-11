package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IPoliceDao;
import com.jrwp.payMent.entity.Core_Police;
import com.jrwp.payMent.service.IPoliceService;

@Service("policeService")
public class PoliceServiceImpl implements IPoliceService {
	
	@Resource
	private IPoliceDao policeDao;

	@Override
	public void savePolice(Core_Police core_Police) {
		// TODO Auto-generated method stub
		policeDao.savePolice(core_Police);
	}

	@Override
	public void deletePolice(String pcCode) {
		// TODO Auto-generated method stub
		policeDao.deletePolice(pcCode);
	}

	@Override
	public void updatePolice(Core_Police police,String id) {
		// TODO Auto-generated method stub
		policeDao.updatePolice(police.getPcName(),id);
	}

	@Override
	public List<Core_Police> getPoliceList() {
		// TODO Auto-generated method stub
		List<Core_Police> list = policeDao.getPoliceList();
		return list;
	}

	@Override
	public Core_Police getObjectByCode(String pcCode) {
		// TODO Auto-generated method stub
		return policeDao.getObjectByCode(pcCode);
	}

	@Override
	public List<Core_Police> getObjectByName(String pcName) {
		// TODO Auto-generated method stub
		return policeDao.getObjectByName(pcName);
	}

	@Override
	public void updatePoliceIsdel(String id) {
		policeDao.updatePoliceIsdel(id);
	}

}
