package com.jrwp.wx.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.WxBusiness;

public interface WxBusinessService {

	List<WxBusiness> list(@Param("code") String code);
}
