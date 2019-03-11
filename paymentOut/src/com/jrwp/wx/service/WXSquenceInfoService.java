package com.jrwp.wx.service;

import java.util.Date;
import java.util.List;

import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.AppointmeInfoJsonForMachine;
import com.jrwp.wx.entity.Appointme_sync;
import com.jrwp.wx.entity.DeptGZHPic;
import com.jrwp.wx.entity.LedShow;
import com.jrwp.wx.entity.MachineConfigJson;
import com.jrwp.wx.entity.PmVisitor;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.entity.WaitCountJson;
import com.jrwp.wx.entity.WarmingInfoJson;

public interface WXSquenceInfoService {

	String getnextSquence(int isappointment, String date, Integer isearly,
			Integer deptid,int businessType);

	Long insertSquenceInfoAndUpdateStates(WXSquenceInfo squenceInfo);

	Long insertXcSquenceInfo(WXSquenceInfo squenceInfo);

	List<WaitCountJson> getWaitCount(Integer deptid, String date);

	int getWaitCountOne(Integer deptid, Date date, int businessType);

	List<AppointmeInfoJsonForMachine> getAppointmeByCardNumber(String date,
			String cardNumber, Integer deptid);

	int getWaitCountWXZS(Integer deptid, String date, int businessType);

	void insertCheckResult(String name, String cardNumber, int checkResult, String xsd, byte[] b,byte[] cardb);

	int getXcCount(Integer deptid, String date, int businessType);

	MachineConfigJson getMachineConfig(String deviceNumber);

	AppointmeInfoJson getAppointmeInfoJson();

	void updateIsCheck(Long id);

	WarmingInfoJson getWarmingInfoJson();

	void updateXCIsCheck(Long id);
	
	List<PmVisitor> getVisitor( Date nowdate, Integer deptid,int status,Integer seq);
    List<PmVisitor> getCalledVisitor( Date nowdate,Integer deptid);

	Integer getConfigIdByDeviceNumber(String deviceNumber);

	void insertDeviceNumber(String deviceNumber);

	MachineConfigJson getMachineConfigJsonByDeviceNumber(String deviceNumber);

	DeptGZHPic getgzhpic(String deviceNumber);

	void insertSquenceSync(Long squence_id, int status, int isexport);

	int getAbleAcceptCount(String deptid);

	int getAlreadyAcceptCount(String cardNumber, String proxyCardNumber,
			String dateString);

	String getBlackCardNumber(String cardNumber, String proxyCardNumber);

	Long insertDlSquenceInfo(WXSquenceInfo squenceInfo);

	void insertWarmInfo(String warmingInfo, Long mac_photomatchid, String name,
			String cardnumber);

	void insertAppointWarmInfo(String warmingInfo, Long id, String name,
			String cardNumber, String phone);

	List<LedShow> getShow(Long deptid, int status);

	Appointme_sync getWxAppintStatus();

	void updateAppointStatus(long id, int status);

	

}
