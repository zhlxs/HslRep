package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.PoliceClassService;

public interface IPoliceServiceDao {

	Integer countsize(String parentCode);

	List<PoliceClassService> getTopServices(@Param("star") Integer star,
			@Param("end") Integer end, @Param("pcCode") String pcCode);

	List<PoliceClassService> getLevel2(@Param("parentCode") String parentCode);

	List<PoliceClassService> getLevel2Uservice(
			@Param("parentCode") String parentCode,
			@Param("uiCode") String uiCode);

	void savePoliceService(PoliceClassService policeClassService);

	void deletePoliceService(String serCode);

	/**
	 * 业务更新
	 * 
	 */
	void updatePoliceService(PoliceClassService policeClassService);

	List<PoliceClassService> getPoliceServiceList();

	PoliceClassService getObjectByCode(String serCode);

	/**
	 * 模糊查询
	 * 
	 * @param serName
	 * @param pcName
	 * @return
	 */
	List<PoliceClassService> getObjectByNames(@Param("serName") String serName,
			@Param("pcName") String pcName);

	List<PoliceClassService> findAddUserServices(
			@Param("uiCode") String uiCode, @Param("pcCode") String pcCode);

	List<PoliceClassService> getObjectByPcode(@Param("pcCode") String pcCode);

	// 假删除（将isdel修改为1）
	void updateServiceIsdel(String serCode);

	List<PoliceClassService> getServices(@Param("uiCode") String uiCode,
			@Param("pcCode") String pcCode);

	String getInsertCode(@Param("parentCode") String parentCode);

	void updateNotice(@Param("businessnoticeId") Long businessnoticeId,
			@Param("serCode") String serCode);
}
