package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IDeptDao;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.DeptHelper;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.LogUtil;

@Service("deptService")
public class DeptServiceImpl implements IDeptService {

	@Resource
	private IDeptDao deptDao;


	@Override
	public List<Core_Dept> list(String queryinfo) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return deptDao.list(whereSql);
	}

	@Override
	public void add(Core_Dept dept) {
		deptDao.add(dept);
	}

	@Override
	public void update(Core_Dept dept) {
		deptDao.update(dept);
	}

	@Override
	public void delete(long id) throws Exception {
		if (DeptHelper.isHaveChlidren(id)) {
			throw new Exception("have chlidren");
		}
		System.out.println("删除菜单");
		deptDao.delete(id);
	}

	@Override
	public Core_Dept getObjectById(long id) {

		return deptDao.getObjectById(id);
	}

	@Override
	public void show(Long id, int isShow) {
		deptDao.show(id, isShow);
	}

	@Override
	public void move(long id, int movetype) throws Exception {
		List<Core_Dept> move = DeptHelper.move(id, movetype);
		for (int i = 0; i < move.size(); i++) {
			deptDao.update(move.get(i));
		}
	}

	@Override
	public List<Core_Dept> tree(String queryinfo) {
		return deptDao.list(queryinfo);
	}

	@Override
	public void save(Core_Dept dept, String parentDeptName) {
		Long parentId = deptDao.getDeptIdByDeptName(parentDeptName);
		if (parentId == null) {
			parentId = 0l;
			dept.setParentId(parentId);
		} else {
			dept.setParentId(parentId);
		}
		if (dept.getId() == null || dept.getId() == 0) {
			String orderCode = deptDao.getInsertCode(parentId);
			if (orderCode == null) {
				Core_Dept core_Dept = deptDao.getObjectById(parentId);
				StringBuilder sb = new StringBuilder();
				sb.append(core_Dept.getOrderCode());
				for (int i = 0; i < DeptHelper.length; i++) {
					sb.append("0");
				}
				orderCode = sb.toString();
		}
		LogUtil.debug("保存的orderCode:{}", orderCode);
		String insertOrderCode = DeptHelper.getInsertOrderCode(orderCode);
		dept.setOrderCode(insertOrderCode);

		deptDao.save(dept);
		} else {
			deptDao.update(dept);
		}
	}

	@Override
	public List<Core_Dept> list1(long id) {
		return deptDao.list1(id);
	}

	@Override
	public Long getDeptIdByDeptName(String parentDeptName) {
		return deptDao.getDeptIdByDeptName(parentDeptName);
	}

	@Override
	public String getDeptCode(String parentDeptName) {
		return deptDao.getDeptCode(parentDeptName);
	}

	@Override
	public void updateOtherName(String newName, String deptid) {
		// TODO Auto-generated method stub
		deptDao.updateOtherName(newName, deptid);
	}

	@Override
	public List<Core_Dept> listByLevel(Integer length) {
		// TODO Auto-generated method stub
		return deptDao.listByLevel(length);
	}

	@Override
	public List<Core_Dept> getDepts() {
		// TODO Auto-generated method stub
		return deptDao.getDepts();
	}

	@Override
	public String[] getAllchild(String code) {
		// TODO Auto-generated method stub
		return deptDao.getAllchild(code);
	}

	@Override
	public List<Integer> getchildsid(Integer deptid) {
		return deptDao.getchildsid(deptid);
	}

	@Override
	public Integer countchild(Integer parentid) {
		return deptDao.countchild(parentid);
	}

	@Override
	public List<Core_Dept> findAlldept(long id) {
		return deptDao.findAlldept(id);
	}

	@Override
	public Integer getsize(Long parentid) {
		return deptDao.getsize(parentid);
	}

	@Override
	public List<Core_Dept> getNextLevel(Long parentid) {
		return deptDao.getNextLevel(parentid);
	}

	@Override
	public List<Core_Dept> getDeptsBycode(String code) {
		// TODO Auto-generated method stub
		return deptDao.getDeptsBycode(code);
	}

	@Override
	public List<Core_Dept> getTopServices(Long parentid, Integer star, Integer end) {
		return deptDao.getTopServices(parentid,star,end);
	}

	@Override
	public Core_Dept getBydeptCode(String deptcode) {
		return deptDao.getBydeptCode(deptcode);
	}

	@Override
	public List<Core_Dept> getDeptByname(String deptname) {
		List<Core_Dept> deptList=null;
		try {
		deptList=deptDao.getDeptByname(deptname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deptList;
	}

}