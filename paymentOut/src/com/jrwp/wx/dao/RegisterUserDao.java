package com.jrwp.wx.dao;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.RegisterUser;

public interface RegisterUserDao {

	void save(RegisterUser user);

	void updateUser(RegisterUser user);

	RegisterUser getObjectByOpenId(@Param("wxOpenId") String wxOpenId);

	RegisterUser getObjectById(@Param("id") long id);

	RegisterUser checkObjectByCard(@Param("idCardNumber") String idCardNumber);

	String getCardByOpenId(@Param("wxOpenId") String wxOpenId);
}
