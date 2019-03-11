package com.jrwp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.dao.IRoleDao;
import com.jrwp.core.entity.Core_Role;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.IRoleService;

/**
 * 角色Service实现
 * 
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Resource
	private IRoleDao roleDao;

	@Override
	public void add(Core_Role role) {
		roleDao.add(role);
	}

	@Override
	public void update(Core_Role role) {
		roleDao.update(role);
	}

	@Override
	public void delete(long id) {
		roleDao.delete(id);
		roleDao.removeAuth(id);
	}

	@Override
	public Core_Role getObjectById(long id) {
		return roleDao.getObjectById(id);
	}

	@Override
	public void save(Core_Role role, String authIDs, String authNames) {
		if (role.getId() != null) {
			roleDao.update(role);
		} else {
			roleDao.add(role);
		}
		String[] ids = authIDs.split(",");
		String[] names = authNames.split(",");
		roleDao.removeAuth(role.getId());
		if (ids[0] != "" && ids.length == names.length) {
			for (int i = 0; i < ids.length; i++) {
				roleDao.addAuth(role.getId(), ids[i], names[i]);
			}
		}
	}

	@Override
	public List<Core_Role> list(String queryinfo) {

		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return roleDao.list(whereSql);
	}

	@Override
	public List<Core_Role> getRoleListByIds(String roleids) {
		roleids = roleids == ",," ? "0" : roleids;
		if (!roleids.equals("0")) {
			String str = roleids.substring(1, roleids.length() - 1);
			StringBuilder sb = new StringBuilder();
			sb.append("where 1=1");
			sb.append(" and id in (").append(str).append(")");
			return roleDao.getRoleListByIds(sb.toString());
		}
		return null;
	}
}
