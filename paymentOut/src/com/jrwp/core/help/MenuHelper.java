package com.jrwp.core.help;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.entity.Core_Menu;
import com.jrwp.core.help.ActionHelper.Action;
import com.jrwp.core.utils.ControlManager;

/**
 * 用于生成页面菜单布局
 * 
 * @author USER
 * 
 */
public class MenuHelper {
	public static final int length = 5;
	private static List<Core_Menu> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;

	@JsonIgnore
	private PageInfo<Core_Menu> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Menu menu;
	@JsonIgnore
	private static List<Core_Menu> temp;

	public MenuHelper(PageInfo<Core_Menu> page) {
		this(page.getList());
		this.page = page;
	}

	public MenuHelper(List<Core_Menu> menuList) {
		List<Core_Menu> group = MenuHelper.group(menuList);
		temp = menuList;
		Core_Menu menu = new Core_Menu();
		menu.setId(0l);
		menu.setParentId(0l);
		menu.setMenuName("菜单");
		menu.setChildren(group);
		items = new ArrayList<Core_Menu>();
		items.add(menu);
	}

	public MenuHelper(Core_Menu menu) {
		this.menu = new Menu(menu);
	}

	// 组合菜单
	public static List<Core_Menu> group(List<Core_Menu> menuList) {
		List<Core_Menu> list = getChilden(menuList, "");
		return list;
	}

	public static List<Core_Menu> getChilden(List<Core_Menu> menuList,
			String parent) {
		List<Core_Menu> list = new ArrayList<Core_Menu>();
		for (int i = 0; i < menuList.size(); i++) {
			Core_Menu core_Menu = menuList.get(i);
			if (core_Menu.getOrderCode().startsWith(parent)
					&& core_Menu.getOrderCode().length() == parent.length()
							+ length) {
				list.add(core_Menu);
				core_Menu.setChildren(getChilden(menuList,
						core_Menu.getOrderCode()));
			}
		}
		if (list.size() == 0) {
			list = null;
		}
		return list;
	}

	// 获得下一个编码
	public static String getInsertOrderCode(String code) {
		String str = code.substring(code.length() - length, code.length());
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(Integer.parseInt(str) + 1));
		while (sb.length() < length) {
			sb.insert(0, "0");
		}
		sb.insert(0, code.substring(0, code.length() - length));
		return sb.toString();
	}

	// 获得动作名字
	public static String getActionNames(List<ActionHelper.Action> action,
			String actionList) {
		StringBuilder sb = new StringBuilder();
		String[] actions = actionList.split(",");
		for (String str : actions) {
			for (Action action2 : action) {
				if (str.equalsIgnoreCase(action2.getControllerAction())) {
					sb.append(action2.getDisplay() + ",");
				}
				if (action2.getChildren() != null) {
					sb.append(getActionNames(action2.getChildren(), actionList));
				}
			}
		}
		if (sb.length() > 0) {
			if (String.valueOf(sb.charAt(sb.length() - 1)).equals(",")) {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		return sb.toString();
	}

	// 判断是否有子对象
	public static boolean isHaveChlidren(long id) {
		for (Core_Menu menu : temp) {
			if (menu.getId().equals(id)) {
				String orderCode = menu.getOrderCode();
				for (Core_Menu m : temp) {
					if (m.getOrderCode().startsWith(orderCode)
							&& !m.getOrderCode().equals(orderCode)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<Core_Menu> getItems() {
		return items;
	}

	public void setItems(List<Core_Menu> items) {
		this.items = items;
	}

	public int getCurrentPage() {
		return 1;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return 1;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return items.get(0).getChildren().size();
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getItemsPerpage() {
		return page.getPageSize();
	}

	public void setItemsPerpage(int itemsPerpage) {
		this.itemsPerpage = itemsPerpage;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<Core_Menu> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_Menu> page) {
		this.page = page;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public static List<Core_Menu> getTemp() {
		return temp;
	}

	public static void setTemp(List<Core_Menu> temp) {
		MenuHelper.temp = temp;
	}

	public class Menu extends Core_Menu {
		private String actionNames;
		private String area;

		private String parentMenuName;
		@JsonIgnore
		private Core_Menu menu;

		public Menu(Core_Menu menu) {
			this.menu = menu;
		}

		public String getActionNames() {
			return MenuHelper.getActionNames(
					new ActionHelper(ControlManager.getControllers())
							.getItems(), menu.getActionList());
		}

		public void setActionNames(String actionNames) {
			this.actionNames = actionNames;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getParentMenuName() {
			for (Core_Menu menu : temp) {
				if (menu.getId().equals(this.menu.getParentId())) {
					return menu.getMenuName();
				}
			}
			return "菜单";
		}

		public void setParentMenuName(String parentMenuName) {
			this.parentMenuName = parentMenuName;
		}

		public Core_Menu getMenu() {
			return menu;
		}

		public void setMenu(Core_Menu menu) {
			this.menu = menu;
		}

		@Override
		public String getActionList() {
			return menu.getActionList();
		}

		@Override
		public List<Core_Menu> getChildren() {
			return menu.getChildren();
		}

		@Override
		public String getIconCssClass() {
			return menu.getIconCssClass();
		}

		@Override
		public Long getId() {
			return menu.getId();
		}

		@Override
		public Boolean getIsShow() {
			return menu.getIsShow();
		}

		@Override
		public Boolean getIsSys() {
			return menu.getIsSys();
		}

		@Override
		public String getMenuName() {
			return menu.getMenuName();
		}

		@Override
		public String getMenuUrl() {
			return menu.getMenuUrl();
		}

		@Override
		public String getOrderCode() {
			return menu.getOrderCode();
		}

		@Override
		public Long getParentId() {
			return menu.getParentId();
		}

	}

	public static List<Core_Menu> move(long id, int movetype) throws Exception {
		String startOrderCode = getStartOrderCode(id);
		String endOrderCode = getEndOrderCode(id);
		String orderCode = getOrderCodeById(id);
		Core_Menu menu = null;
		if (movetype == 1) {
			if (orderCode.equals(startOrderCode)) {
				throw new Exception("已经排在此分类第一了！");
			}
			menu = getUpMenu(id);
			String code = menu.getOrderCode();
			changeMenuOrderCode(menu, orderCode);
			changeMenuOrderCode(getMenuById(id), code);
		} else {
			if (orderCode.equals(endOrderCode)) {
				throw new Exception("已经排在此分类末尾了！");
			}
			menu = getDownMenu(id);
			String code = menu.getOrderCode();
			changeMenuOrderCode(menu, orderCode);
			changeMenuOrderCode(getMenuById(id), code);
		}
		return temp;
	}

	public static void changeMenuOrderCode(Core_Menu core_menu, String code) {
		StringBuilder replace = new StringBuilder(core_menu.getOrderCode())
				.replace(0, code.length(), code);
		core_menu.setOrderCode(replace.toString());
		List<Core_Menu> children = core_menu.getChildren();
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				changeMenuOrderCode(children.get(i), code);
			}
		}
	}

	public static Core_Menu getUpMenu(long id) {
		String orderCodeById = getParentOrderCode(id);
		String orderCode = getOrderCodeById(id);
		for (int i = 0; i < temp.size(); i++) {
			Core_Menu core_menu = temp.get(i);
			if (core_menu.getOrderCode().startsWith(orderCodeById)
					&& !core_menu.getOrderCode().equals(orderCodeById)) {
				if (orderCode.compareTo(core_menu.getOrderCode()) == 1) {
					return core_menu;
				}
			}
		}
		return null;
	}

	public static Core_Menu getDownMenu(long id) {
		String orderCodeById = getParentOrderCode(id);
		String orderCode = getOrderCodeById(id);
		for (int i = 0; i < temp.size(); i++) {
			Core_Menu core_menu = temp.get(i);
			if (core_menu.getOrderCode().startsWith(orderCodeById)
					&& !core_menu.getOrderCode().equals(orderCodeById)) {
				if (orderCode.compareTo(core_menu.getOrderCode()) == -1) {
					return core_menu;
				}
			}
		}
		return null;
	}

	public static String getStartOrderCode(long id) {
		String parentOrderCodeById = getParentOrderCode(id);
		String orderCodeById = getOrderCodeById(id);
		String orderCode = orderCodeById;
		for (Core_Menu core_menu : temp) {
			if (core_menu.getOrderCode().startsWith(parentOrderCodeById)
					&& core_menu.getOrderCode().length() == orderCodeById
							.length()) {
				if (orderCode.compareTo(core_menu.getOrderCode()) > 0) {
					orderCode = core_menu.getOrderCode();
				}
			}
		}
		return orderCode;
	}

	public static String getEndOrderCode(long id) {
		String parentOrderCodeById = getParentOrderCode(id);
		String orderCodeById = getOrderCodeById(id);
		String orderCode = orderCodeById;
		for (Core_Menu core_menu : temp) {
			if (core_menu.getOrderCode().startsWith(parentOrderCodeById)
					&& core_menu.getOrderCode().length() == orderCodeById
							.length()) {
				if (orderCode.compareTo(core_menu.getOrderCode()) < 0) {
					orderCode = core_menu.getOrderCode();
				}
			}
		}
		return orderCode;
	}

	public static String getParentOrderCode(long id) {
		String orderCodeById = getOrderCodeById(id);
		for (Core_Menu core_menu : temp) {
			if (orderCodeById.startsWith(core_menu.getOrderCode())
					&& !orderCodeById.equals(core_menu.getOrderCode())
					&& orderCodeById.length() - length == core_menu
							.getOrderCode().length()) {
				return core_menu.getOrderCode();
			}
		}
		return "";
	}

	public static String getOrderCodeById(long id) {
		for (Core_Menu core_menu : temp) {
			if (core_menu.getId().equals(id)) {
				return core_menu.getOrderCode();
			}
		}
		return null;
	}

	public static Core_Menu getMenuById(long id) {
		for (Core_Menu core_menu : temp) {
			if (core_menu.getId().equals(id)) {
				return core_menu;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("00001".compareTo("00002"));
	}
}
