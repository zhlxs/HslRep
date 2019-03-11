package com.jrwp.wx.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.CallSquenceJson;
import com.jrwp.wx.entity.OrderCount;

public interface WxAppointmeDao {
	public void cancelAppointme(@Param("appointmeId") Long appointmeId);

	public List<OrderCount> getOrderCount(
			@Param("businessType") int businessType,
			@Param("modelId") Long modelId);

	public List<OrderCount> getAppointmeCount(@Param("day") String day,
			@Param("deptid") Long deptid,
			@Param("businessType") int businessType);

	public AppointmeInfoJson getAppointmeByCardnumber(
			@Param("appointmenttime") String appointmenttime,
			@Param("cardNumber") String cardNumber,
			@Param("deptid") Long deptid,
			@Param("businessType") int businessType,
			@Param("timequantum") Long timequantum);

	public void insertAppointmeInfo(AppointmeInfoJson appointmeInfoJson);

	public List<CallSquenceJson> getCallSquenceJson();

	public List<AppointmeInfoJson> getAppointmeByOpenid(
			@Param("date1") Date date1, @Param("openid") String openid,
			@Param("deptid") Integer deptid);

	public void updateCallStatue(@Param("id") Long id);

	public AppointmeInfoJson getAppointmeById(@Param("id") Long id);
	
	public void insertAppointmeSync(@Param("id")Long appointmeId, @Param("status")int status, @Param("isexport")int isexport);
	
}
