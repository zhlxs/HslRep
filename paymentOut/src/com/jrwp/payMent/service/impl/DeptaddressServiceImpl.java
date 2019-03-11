package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.DicDeptaddressDao;
import com.jrwp.payMent.entity.DicDeptaddress;
import com.jrwp.payMent.service.DeptaddressService;

@Service
public class DeptaddressServiceImpl implements DeptaddressService {

	@Resource
	private DicDeptaddressDao deptaddressDao;

	@Override
	public List<DicDeptaddress> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return deptaddressDao.list(whereSql);
	}

	@Override
	public void save(DicDeptaddress deptaddress) {
		// TODO Auto-generated method stub
		if (deptaddress.getId() == null) {
			deptaddressDao.save(deptaddress);
		} else {
			deptaddressDao.update(deptaddress);
		}
	}

	@Override
	public void update(DicDeptaddress deptaddress) {
		// TODO Auto-generated method stub
		deptaddressDao.update(deptaddress);
	}

	@Override
	public DicDeptaddress getObjectById(Long id) {
		// TODO Auto-generated method stub
		return deptaddressDao.getObjectById(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		deptaddressDao.delete(id);
	}

	@Override
	public DicDeptaddress getObjectByDeptId(Long deptId) {
		// TODO Auto-generated method stub
		return deptaddressDao.getObjectByDeptId(deptId);
	}

}
