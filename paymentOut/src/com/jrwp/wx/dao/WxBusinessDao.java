package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.WxBusiness;

public interface WxBusinessDao {

	List<WxBusiness> list(@Param("code") String code);
}
