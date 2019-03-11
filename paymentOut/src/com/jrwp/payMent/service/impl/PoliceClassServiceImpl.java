package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.DeptHelper;
import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IPoliceServiceDao;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.service.IPoliceClassService;
import sun.org.mozilla.javascript.internal.ast.IfStatement;

@Service("policeclassService")
public class PoliceClassServiceImpl implements IPoliceClassService {

	@Resource
	private IPoliceServiceDao policeServiceDao;

	@Override
	public void savePoliceService(PoliceClassService policeClassService) {
		// TODO Auto-generated method stub
		try {
			policeClassService = initService(policeClassService);
			policeServiceDao.savePoliceService(policeClassService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// policeServiceDao.savePoliceService(policeClassService);
	}

	@Override
	public void updatePoliceService(PoliceClassService policeClassService) {
		// TODO Auto-generated method stub
		try {
			policeClassService = initService(policeClassService);
			policeServiceDao.updatePoliceService(policeClassService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePoliceService(String serCode) {
		// TODO Auto-generated method stub
		policeServiceDao.deletePoliceService(serCode);
	}

	public PoliceClassService initService(PoliceClassService policeClassService) {
		try {
			String parentCode = policeClassService.getParentCode();
			if (parentCode.equals("0") || parentCode == null) {
				policeClassService.setOrderCode(policeServiceDao
						.getInsertCode("0"));
			} else {
				StringBuffer oc = new StringBuffer();
				PoliceClassService parent = policeServiceDao
						.getObjectByCode(policeClassService.getParentCode());
				oc.append(parent.getOrderCode());
				oc.append(policeServiceDao.getInsertCode(policeClassService
						.getParentCode()));
				policeClassService.setPcCode(parent.getPcCode());
				policeClassService.setOrderCode(oc.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return policeClassService;
	}

	@Override
	public List<PoliceClassService> getPoliceServiceList() {
		// TODO Auto-generated method stub
		List<PoliceClassService> list = policeServiceDao.getPoliceServiceList();
		return list;
	}

	@Override
	public PoliceClassService getObjectByCode(String serCode) {
		// TODO Auto-generated method stub
		return policeServiceDao.getObjectByCode(serCode);
	}

	@Override
	public List<PoliceClassService> getObjectByNames(String serName,
			String pcName) {
		// TODO Auto-generated method stub
		return policeServiceDao.getObjectByNames(serName, pcName);
	}

	@Override
	public List<PoliceClassService> findAddUserServices(String uiCode,
			String pcCode) {
		return policeServiceDao.findAddUserServices(uiCode, pcCode);
	}

	@Override
	public List<PoliceClassService> getObjectByPcode(String pcCode) {
		// TODO Auto-generated method stub
		List<PoliceClassService> list = policeServiceDao
				.getObjectByPcode(pcCode);
		return list;
	}

	@Override
	public void updateServiceIsdel(String serCode) {
		policeServiceDao.updateServiceIsdel(serCode);
	}

	@Override
	public Integer countsize(String parentCode) {
		return policeServiceDao.countsize(parentCode);
	}

	@Override
	public List<PoliceClassService> getTopServices(Integer star, Integer end,
			String pcCode) {
		return policeServiceDao.getTopServices(star, end, pcCode);
	}

	@Override
	public List<PoliceClassService> getLevel2(String parentCode) {
		return policeServiceDao.getLevel2(parentCode);
	}

	@Override
	public List<PoliceClassService> getLevel2Uservice(String parentCode,
			String uiCode) {
		return policeServiceDao.getLevel2Uservice(parentCode, uiCode);
	}

	@Override
	public List<PoliceClassService> getServices(String uiCode, String pcCode) {
		return policeServiceDao.getServices(uiCode, pcCode);
	}

}
