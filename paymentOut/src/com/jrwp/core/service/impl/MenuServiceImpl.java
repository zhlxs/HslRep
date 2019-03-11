package com.jrwp.core.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.jrwp.core.dao.IMenuDao;
import com.jrwp.core.entity.Core_Menu;
import com.jrwp.core.help.MenuHelper;
import com.jrwp.core.service.IMenuService;
import com.jrwp.core.utils.LogUtil;

@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	@Resource
	private IMenuDao menuDao;

	// 添加菜单
	@Override
	public void add(Core_Menu menu) {
		menuDao.add(menu);
	}

	// 修改菜单
	@Override
	public void update(Core_Menu menu) {
		menuDao.update(menu);
	}

	// 删除菜单
	@Override
	public void delete(long id) throws Exception {
		if (MenuHelper.isHaveChlidren(id)) {
			throw new Exception("have chlidren");
		}
		System.out.println("删除菜单");
		menuDao.delete(id);
	}

	// 获取菜单
	@Override
	public List<Core_Menu> list() {
		return menuDao.list();
	}

	@Override
	public Core_Menu getObjectById(long id) {
		return menuDao.getObjectById(id);
	}

	// 菜单的显示状态
	@Override
	public void show(long id, int isShow) {
		menuDao.show(id, isShow);
	}

	// 移动菜单
	@Override
	public void move(long id, int movetype) throws Exception {
		List<Core_Menu> move = MenuHelper.move(id, movetype);
		for (int i = 0; i < move.size(); i++) {
			menuDao.update(move.get(i));
		}
	}

	@Override
	public void save(Core_Menu menu, String parentMenuName) {
		Long parentId = menuDao.getDeptIdByMenuName(parentMenuName);
		if (parentId == null) {
			parentId = 0l;
			menu.setParentId(parentId);
		}
		String orderCode = menuDao.getInsertCode(parentId);
		if (orderCode == null) {
			Core_Menu core_Menu = menuDao.getObjectById(parentId);
			StringBuilder sb = new StringBuilder();
			sb.append(core_Menu.getOrderCode());
			for (int i = 0; i < MenuHelper.length; i++) {
				sb.append("0");
			}
			orderCode = sb.toString();
		}
		LogUtil.debug("保存的insertOrderCode:{}", orderCode);
		String insertOrderCode = MenuHelper.getInsertOrderCode(orderCode);
		menu.setOrderCode(insertOrderCode);
		if (menu.getId() == null || menu.getId() == 0) {
			menuDao.save(menu);
		} else {
			menuDao.update(menu);
		}
	}

	@Override
	public List<Core_Menu> getListByUserId(Long id) {
		return menuDao.getListByUserId(id);
	}

	@Override
	public List<Core_Menu> syslist() {
		return menuDao.sysList();
	}

}
