package com.jrwp.wx.service.impl;

import com.jrwp.wx.dao.DeptAddressMapper;
import com.jrwp.wx.entity.DeptAddress;
import com.jrwp.wx.service.DeptAddressService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service
public class DeptAddressServiceImpl implements DeptAddressService {
	@Resource
	private DeptAddressMapper deptAddressMapper;

	@Override
	public List<DeptAddress> selectByDeptid(String code) {
		return deptAddressMapper.selectByDeptid(code);
	}

	@Override
	public DeptAddress getObjectById(Long id) {
		// TODO Auto-generated method stub
		return deptAddressMapper.getObjectById(id);
	}
}
