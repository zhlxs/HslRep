package com.jrwp.wx.service;

import java.util.List;

import com.jrwp.wx.entity.WxPmVistor;

public interface WxSquenceService {

	int getZhWaitCount(String dateString, Long deptid);

	List<WxPmVistor> getSquenceNumber(String dateString, Long deptid);

	int getDbWaitCount(String dateString, Long deptid);

	List<WxPmVistor> getDbSquenceNumber(String dateString, Long deptid);

	List<WxPmVistor> getAllSquenceInfo(Long deptid, String dateString);

}
