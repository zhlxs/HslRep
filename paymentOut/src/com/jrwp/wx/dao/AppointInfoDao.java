package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.AppointInfo;
import com.jrwp.wx.entity.WXSquenceInfo;

public interface AppointInfoDao {

	List<AppointInfo> getInfo(@Param("openId") String openId);

	WXSquenceInfo getSequenceByAppointId(
			@Param("appointme_infoid") Long appointme_infoid);
	
	void updateState(AppointInfo info);
}
