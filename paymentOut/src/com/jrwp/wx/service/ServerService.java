package com.jrwp.wx.service;

import java.util.List;

import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.SquenceConfig;
import com.jrwp.wx.entity.SupplementRecord;
import com.jrwp.wx.entity.WXSquenceInfo;

public interface ServerService {

	void insertBeiAnInfo(BeiAnInfoJson beiAnInfo);

	BeiAnInfoJson getDeviceNumber(String ywckjsjip);
	
	WXSquenceInfo getCallSquence(Integer nowtimequan, int isappointment, Integer isearly, String date, Integer deptid,String ser_code);
	// 供综合平台使用，查出代理人信息
	WXSquenceInfo getCallSquenceTwo(Integer nowtimequan, int isappointment, Integer isearly, String date, Integer deptid,String ser_code);

	void updateSquenceInfo(String ywckjsjip, String serialnumber,Long id, String ckbh,String pdh,Integer deptid, int status, int isexport);

	WXSquenceInfo getSquenceInfoBySerialNumber(String qhxxxlh,String dateString);
	// 供综合平台使用，查出代理人信息
	WXSquenceInfo getSquenceInfoBySerialNumberTwo(String qhxxxlh,String dateString);

	void updateSquenceOver(Long id, int status, int isexport);

	void changeWinStatu(int statu,String ywckjsjip);

	void insertSupplementRecord(SupplementRecord supplementRecord);

	List<SupplementRecord> getSupplementRecord(String dateString, Integer deptid);

	List<OrderCount> getAllTime();

	void updateSquenceAndSupplementRecord(Long supplementRecordid, Long squenceid,
			String serialNumber, String ckbh, int statu);

	WXSquenceInfo getWindowBySerialNumber(String qhxxxlh);

	void insertWaitAppraise(Long id, String wicketnumber, String qhxxxlh);

	void updateSquenceFinish(Long id);

	void updateReatCall(String qhxxxlh,String dateString);

	void insertSquenceOrder(String ywckjsjip, String serialnumber);

	WXSquenceInfo getLastSerialNumber(String ywckjsjip,String dateString);

	void updateSquenceSNYC(Long id, int status, int isexport);

	void updateSquenceStatus(Long id, int status);

	void updateLedShow(Integer deptid, String squence, int status);

	SquenceConfig getSquenceConfig(String deptid);

	void updateLedStatus();

	void updateShowStatu(String winNumber, int status,Integer id);

	String getwinNumberById(Integer id);

	void updateRecallTime(String qhxxxlh, String dateString);

}
