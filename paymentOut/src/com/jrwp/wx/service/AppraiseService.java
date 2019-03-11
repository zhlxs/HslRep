package com.jrwp.wx.service;

import java.util.List;

import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.entity.AppraiseJson;

public interface AppraiseService {

	String getSquenceidByCkbh(String jqm, String dateString);

	void saveAndUpdateStatu(Long appraisedetailid, int isdefault,
			String squence_infoid);

	AppraiseJson getAppraiseInfo();

	void updateIsWrite(Long id);

	List<AppraiseInfoForAndroid> getAllWaitAppraise(String dateString);

}
