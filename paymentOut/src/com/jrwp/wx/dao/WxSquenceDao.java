package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.WxPmVistor;

public interface WxSquenceDao {
	public int getZhWaitCount(@Param("dateString")String dateString,@Param("deptid") Long deptid);
	public List<WxPmVistor> getSquenceNumber(@Param("dateString")String dateString, @Param("deptid")Long deptid);
	public int getDbWaitCount(@Param("dateString")String dateString, @Param("deptid")Long deptid);
	public List<WxPmVistor> getDbSquenceNumber(@Param("dateString")String dateString,@Param("deptid") Long deptid);
	public List<WxPmVistor> getAllSquenceInfo(@Param("deptid")Long deptid, @Param("dateString")String dateString);
}
