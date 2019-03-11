package com.jrwp.wx.service;

import java.util.Date;
import java.util.List;

import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.CallSquenceJson;
import com.jrwp.wx.entity.OrderCount;

public interface WxAppointmeService {

	void cancelAppointme(Long appointmeId);

	List<OrderCount> getOrderCount(int businessType, Long modelId);

	List<OrderCount> getAppointmeCount(String day, Long deptid, int businessType);

	AppointmeInfoJson getAppointmeByCardnumber(String appointmenttime,
			String cardNumber, Long deptid, int businessType, Long timequantum);

	void insertAppointmeInfo(AppointmeInfoJson appointmeInfoJson);

	List<CallSquenceJson> getCallSquenceJson();

	List<AppointmeInfoJson> getAppointmeByOpenid(Date date1, String openid,
			Integer deptid);

	void updateCallStatue(Long id);

	AppointmeInfoJson getAppointmeById(Long id);

	void insertAppointmeSync(Long appointmeId, int status, int isexport);

	
}
