package com.jrwp.payMent.service;

import java.util.List;

import com.jrwp.payMent.entity.PoliceClassService;
import org.apache.ibatis.annotations.Param;

public interface IPoliceClassService {

	void savePoliceService(PoliceClassService policeClassService);

	void deletePoliceService(String serCode);

	void updatePoliceService(PoliceClassService policeClassService);

	List<PoliceClassService> getPoliceServiceList();

	PoliceClassService getObjectByCode(String serCode);

	List<PoliceClassService> getObjectByNames(String serName, String pcName);

	List<PoliceClassService> findAddUserServices(String uiCode, String pcCode);

	List<PoliceClassService> getObjectByPcode(String pcCode);

	void updateServiceIsdel(String serCode);

	Integer countsize(String parentCode);

	List<PoliceClassService> getTopServices(Integer star, Integer end,
			String pcCode);

	List<PoliceClassService> getLevel2(String parentCode);

	List<PoliceClassService> getLevel2Uservice(String parentCode, String uiCode);

	List<PoliceClassService> getServices(String uiCode, String pcCode);

}
