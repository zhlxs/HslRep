package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.SquenceConfig;
import com.jrwp.wx.entity.SupplementRecord;
import com.jrwp.wx.entity.WXSquenceInfo;

public interface ServerDao {
	public void insertBeiAnInfo(BeiAnInfoJson beiAnInfo);
	public BeiAnInfoJson getDeviceNumber(@Param("ywckjsjip")String ywckjsjip);
	public WXSquenceInfo getCallSquence(@Param("nowtimequan")Integer nowtimequan, @Param("isappointment")int isappointment,
			@Param("isearly")Integer isearly, @Param("date")String date, @Param("deptid")Integer deptid, @Param("ser_code")String ser_code);
	// 供综合平台使用，查出代理人信息
	public WXSquenceInfo getCallSquenceTwo(@Param("nowtimequan")Integer nowtimequan, @Param("isappointment")int isappointment,
			@Param("isearly")Integer isearly, @Param("date")String date, @Param("deptid")Integer deptid, @Param("ser_code")String ser_code);
	public void updateSquenceInfo(@Param("id")Long id,@Param("ckbh")String ckbh);
	public WXSquenceInfo getSquenceInfoBySerialNumber(@Param("qhxxxlh")String qhxxxlh,@Param("dateString")String dateString);
	// 供综合平台使用，查出代理人信息s
	public WXSquenceInfo getSquenceInfoBySerialNumberTwo(@Param("qhxxxlh")String qhxxxlh,@Param("dateString")String dateString);
	public void updateSquenceOver(@Param("id")Long id);
	public void changeWinStatu(@Param("ywckjsjip")String ywckjsjip);
	public int getWinStatu(@Param("ywckjsjip")String ywckjsjip);
	public void updateWinStatu(@Param("statuAfter")int statuAfter, @Param("ywckjsjip")String ywckjsjip);
	public void insertChangeHistory(@Param("statuBefore")int statuBefore, @Param("ywckjsjip")String ywckjsjip);
	public void insertSupplementRecord(SupplementRecord supplementRecord);
	public List<SupplementRecord> getSupplementRecord(@Param("dateString")String dateString,
			@Param("deptid")Integer deptid);
	public List<OrderCount> getAllTime();
	public void updateSupplement(@Param("supplementRecordid")Long supplementRecordid, @Param("squenceid")Long squenceid);
	public void updateSquence(@Param("squenceid")Long squenceid, @Param("serialNumber")String serialNumber,
			@Param("ckbh")String ckbh, @Param("statu")int statu);
	public WXSquenceInfo getWindowBySerialNumber(@Param("qhxxxlh")String qhxxxlh);
	public void insertWaitAppraise(@Param("squence_infoid")Long id, @Param("wicketnumber")String wicketnumber,@Param("qhxxxlh")String qhxxxlh);
	public void updateSquenceFinish(@Param("id")Long id);
	public void updateReatCall(@Param("qhxxxlh")String qhxxxlh,@Param("dateString")String dateString);
	public void updatePassBefore(@Param("wicketnumber")String wicketnumber);
	public void insertSquenceOrder(@Param("ywckjsjip")String ywckjsjip, @Param("serialnumber")String serialnumber);
	public WXSquenceInfo getLastSerialNumber(@Param("ywckjsjip")String ywckjsjip,@Param("dateString")String dateString);
	public void updateSquenceSNYC(@Param("id")Long id, @Param("status")int status, @Param("isexport")int isexport);
	public void updateSquenceStatus(@Param("id")Long id, @Param("status")int status);
	public void updateLedShow(@Param("winnumber")String winnumber, @Param("pdh")String pdh,@Param("deptid")Integer deptid);
	public void updateLedShowGhOrfinish(@Param("deptid")Integer deptid, @Param("squence")String squence, @Param("status")int status);
	public SquenceConfig getSquenceConfig(@Param("deptid")String deptid);
	public void updateLedStatus();
	public void updateShowStatu(@Param("winNumber")String winNumber, @Param("status")int status,@Param("id")Integer id);
	public String getwinNumberById(@Param("id")Integer id);
	public void updateSquenceSNYCAndWinNumber(@Param("id")Long id, @Param("status")int status,
			@Param("isexport")int isexport, @Param("ckbh")String ckbh);
	public void updateRecallTime(@Param("qhxxxlh")String qhxxxlh, @Param("dateString")String dateString);
}
