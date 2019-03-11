package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IUserDao;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserDao userDao;

	@Override
	public void add(Core_User user) {
		userDao.add(user);
	}

	@Override
	public void update(Core_User user) {
		userDao.update(user);
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
		// 删除用户所属的角色
		userDao.removeRolesById(id);
	}

	@Override
	public List<Core_User> list(String queryinfo) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return userDao.list(whereSql);
	}
	
	@Override
	public List<Core_User> list1(String queryinfo,String dept2) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return userDao.list1(whereSql,dept2);
	}

	// 部门ID查用户
	@Override
	public List<Core_User> getDeptId(long deptId) {

		return userDao.getDeptId(deptId);
	}

	@Override
	public Core_User getObjectById(long id) {
		return userDao.getObjectById(id);
	}

	@Override
	public void setStart(long id, int isstart) {
		userDao.setStart(id, isstart);
	}

	@Override
	public List<Core_User> tree(String queryinfo) {
		return userDao.list(queryinfo);
	}

	@Override
	public Core_User getByUserName(String name) {
		return userDao.getByUserName(name);
	}

	@Override
	public void save(Core_User user, String deptName, String roleNames) {

		if (user.getId() != null) {
			// 将密码转密再传入数据库当中
			String password = DigestUtils.md5Hex(user.getPassword());
			//存入大写的MD5
			user.setPassword(password.toUpperCase());
			userDao.update(user);
			// 如果查询到用户id，则先移除用户的角色
			userDao.removeRolesById(user.getId());
		} else {
			// 将密码转密再传入数据库当中
			String password = DigestUtils.md5Hex(user.getPassword());
			user.setPassword(password.toUpperCase());
			// 将部门id放入user表中
			userDao.save(user);
		}
		String[] rolename = roleNames.split(",");
		for (int i = 0; i < rolename.length; i++) {
			// 根据角色名查询角色的id
			Long roleid = userDao.getRoleIdByRoleName(rolename[i]);
			// 将数据储存至userforrole表中
			Long userid = user.getId();
			userDao.addRoles(userid, roleid);
		}
	}

	@Override
	public void updateForLogin(Core_User user) {
		userDao.updateForLogin(user);
	}

	@Override
	public Long getdeptid(String username) {
		// TODO Auto-generated method stub
		return userDao.getdeptid(username);
	}

	@Override
	public Core_User getObjectByName(String username) {
		// TODO Auto-generated method stub
		Core_User user = userDao.getObjectByName(username);
		return user;
	}

}
