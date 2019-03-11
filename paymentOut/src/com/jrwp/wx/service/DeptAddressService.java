package com.jrwp.wx.service;

import com.jrwp.wx.entity.DeptAddress;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeptAddressService {

	List<DeptAddress> selectByDeptid(String code);

	DeptAddress getObjectById(@Param("id") Long id);
}
