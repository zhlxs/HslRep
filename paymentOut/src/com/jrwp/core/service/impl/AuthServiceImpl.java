package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IAuthDao;
import com.jrwp.core.entity.Core_Auth;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.IAuthService;

@Service("authService")
public class AuthServiceImpl implements IAuthService {
	@Resource
	private IAuthDao authDao;

	// 获取权限列表的实现方法
	@Override
	public List<Core_Auth> list(String queryinfo) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return authDao.list(whereSql);
	}

	// 增加权限的实现方法
	@Override
	public void add(Core_Auth core_Auth) {
		authDao.add(core_Auth);

	}

	// 修改权限的方法
	@Override
	public void update(Core_Auth core_Auth) {

		authDao.update(core_Auth);
	}

	// 删除权限的方法
	@Override
	public void delete(Long id) {
		authDao.delete(id);
		authDao.removeAction(id);
	}

	@Override
	public List<Core_Auth> getAuthListByRoleId(long roleid) {
		return authDao.getAuthListByRoleId(roleid);
	}

	@Override
	public List<Core_Auth> getAuthListByIds(String authids) {
		authids = authids == ",," ? "0" : authids;
		if (!authids.equals("0")) {
			String str = authids.substring(1, authids.length() - 1);
			StringBuilder sb = new StringBuilder();
			sb.append("where 1=1");
			sb.append(" and id in (").append(str).append(")");
			return authDao.getAuthListByIds(sb.toString());
		}
		return null;
	}

	@Override
	public void save(Core_Auth core_Auth, String actionValues,
			String actionNames) {
		if (core_Auth.getId() != null) {
			authDao.update(core_Auth);
		} else {
			authDao.add(core_Auth);
		}
		String[] values = actionValues.split(",");
		String[] names = actionNames.split(",");
		authDao.removeAction(core_Auth.getId());
		if (values[0] != "" && values.length == names.length) {
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				authDao.addAction(
						core_Auth.getId(),
						value.substring(0, value.indexOf("|")),
						value.substring(value.indexOf("|") + 1, value.length()),
						names[i]);
			}
		}
	}

	@Override
	public Core_Auth getObjectById(long id) {
		return authDao.getObjectById(id);
	}

}
