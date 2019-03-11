package com.jrwp.wx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.AppointInfo;
import com.jrwp.wx.entity.WXSquenceInfo;

public interface AppointInfoService {

	List<AppointInfo> getInfo(@Param("openId") String openId);

	WXSquenceInfo getSequenceByAppointId(
			@Param("appointme_infoid") Long appointme_infoid);
}
