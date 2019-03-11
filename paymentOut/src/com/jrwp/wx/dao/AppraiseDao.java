package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.entity.AppraiseJson;

public interface AppraiseDao {
	public String getSquenceidByCkbh(@Param("jqm")String jqm,@Param("dateString")String dateString);

	public void saveAppraiseInfo(@Param("appraisedetailid")Long appraisedetailid, @Param("isdefault")int isdefault,
			@Param("squence_infoid")String squence_infoid);

	public void updateWaitStatu(@Param("squence_infoid")String squence_infoid);
	public AppraiseJson getAppraiseInfo();
	public void updateIsWrite(@Param("id")Long id);
	public List<AppraiseInfoForAndroid> getAllWaitAppraise(@Param("dateString")String dateString);
}
