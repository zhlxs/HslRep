package com.jrwp.core.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.entity.Core_Dept;

/**
 * 生成部门布局页面
 * 
 * @author Administrator
 * 
 */
public class DeptHelper {

	public static final int length = 5;

	private static List<Core_Dept> items;

	private static List<Dept> item;

	private int currentPage;

	private int totalPages;

	private int totalItems;

	private int itemsPerPage;

	private String context;

	@JsonIgnore
	private PageInfo<Core_Dept> page;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Dept dept;

	@JsonIgnore
	private static List<Core_Dept> temp;

	// public Core_DeptHelper(PageInfo<Core_Dept> page) {
	// this.page = page;
	// List<Core_Dept> list = page.getList();
	// items = new ArrayList<Core_DeptHelper.Dept>();
	// for (Core_Dept core_Dept : list) {
	// items.add(new Dept(core_Dept));
	// }
	// }

	public DeptHelper(PageInfo<Core_Dept> page) {
		this(page.getList());
		this.page = page;
	}

	public DeptHelper(List<Core_Dept> deptlist) {
		List<Core_Dept> group = DeptHelper.group(deptlist);
		temp = deptlist;
		Core_Dept dept = new Core_Dept();
		dept.setId(0l);
		dept.setParentId(0l);
		dept.setDeptName("部门");
		dept.setChildren(group);
		items = new ArrayList<Core_Dept>();
		items.add(dept);
	}

	public DeptHelper(Core_Dept dept) {
		this.dept = new Dept(dept);
	}

	public DeptHelper() {

	}

	// 组合部门
	public static List<Core_Dept> group(List<Core_Dept> deptlist) {
		List<Core_Dept> list = getChildren(deptlist, "");
		return list;
	}

	public static List<Core_Dept> getChildren(List<Core_Dept> deptlist,
			String parent) {
		List<Core_Dept> list = new ArrayList<Core_Dept>();
		for (int i = 0; i < deptlist.size(); i++) {
			Core_Dept core_Dept = deptlist.get(i);
			if (core_Dept.getOrderCode().startsWith(parent)
					&& core_Dept.getOrderCode().length() == parent.length()
							+ length) {
				core_Dept.setChildren(getChildren(deptlist,
						core_Dept.getOrderCode()));
				list.add(core_Dept);
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

	// 判断是否有子对象
	public static boolean isHaveChlidren(long id) {
		for (Core_Dept dept : temp) {
			if (dept.getId().equals(id)) {
				String orderCode = dept.getOrderCode();
				for (Core_Dept d : temp) {
					if (d.getOrderCode().startsWith(orderCode)
							&& !d.getOrderCode().equals(orderCode)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static List<Dept> getItem() {
		return item;
	}

	public static void setItem(List<Dept> item) {
		DeptHelper.item = item;
	}

	public List<Core_Dept> getItems() {
		return items;
	}

	public void setItems(List<Core_Dept> items) {
		this.items = items;
	}



	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<Core_Dept> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_Dept> page) {
		this.page = page;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public static List<Core_Dept> getTemp() {
		return temp;
	}

	public static void setTemp(List<Core_Dept> temp) {
		DeptHelper.temp = temp;
	}

	public static int getLength() {
		return length;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public class Dept extends Core_Dept {

		private String area;

		private String parentDeptName;

		@JsonIgnore
		private Core_Dept dept;

		public Dept(Core_Dept dept) {
			this.dept = dept;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getParentDeptName() {
			for (Core_Dept dept : temp) {
				if (dept.getId().equals(this.dept.getParentId())) {
					return dept.getDeptName();
				}
			}
			return "部门";
		}

		public void setParentDeptName(String parentDeptName) {
			this.parentDeptName = parentDeptName;
		}

		public Core_Dept getDept() {
			return dept;
		}

		public void setDept(Core_Dept dept) {
			this.dept = dept;
		}

		@Override
		public List<Core_Dept> getChildren() {
			return dept.getChildren();
		}

		@Override
		public Long getId() {
			return dept.getId();
		}

		@Override
		public Boolean getIsShow() {
			return dept.getIsShow();
		}

		@Override
		public Boolean getIsSys() {
			return dept.getIsSys();
		}

		@Override
		public String getDeptName() {
			return dept.getDeptName();
		}

		@Override
		public String getOrderCode() {
			return dept.getOrderCode();
		}

		@Override
		public Long getParentId() {
			return dept.getParentId();
		}

		@Override
		public String getRemark() {
			return dept.getRemark();
		}
	}

	public static List<Core_Dept> move(long id, int movetype) throws Exception {
		String startOrderCode = getStartOrderCode(id);
		String endOrderCode = getEndOrderCode(id);
		String orderCode = getOrderCodeById(id);
		Core_Dept dept = null;
		if (movetype == 1) {
			if (orderCode.equals(startOrderCode)) {
				throw new Exception("已经排在此分类第一了！");
			}
			dept = getUpMenu(id);
			String code = dept.getOrderCode();
			changeMenuOrderCode(dept, orderCode);
			changeMenuOrderCode(getDeptById(id), code);
		} else {
			if (orderCode.equals(endOrderCode)) {
				throw new Exception("已经排在此分类末尾了！");
			}
			dept = getDownMenu(id);
			String code = dept.getOrderCode();
			changeMenuOrderCode(dept, orderCode);
			changeMenuOrderCode(getDeptById(id), code);
		}
		return temp;
	}

	public static void changeMenuOrderCode(Core_Dept core_dept, String code) {
		StringBuilder replace = new StringBuilder(core_dept.getOrderCode())
				.replace(0, code.length(), code);
		core_dept.setOrderCode(replace.toString());
		List<Core_Dept> children = core_dept.getChildren();
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				changeMenuOrderCode(children.get(i), code);
			}
		}
	}

	public static Core_Dept getUpMenu(long id) {
		String orderCodeById = getParentOrderCode(id);
		String orderCode = getOrderCodeById(id);
		for (int i = 0; i < temp.size(); i++) {
			Core_Dept core_dept = temp.get(i);
			if (core_dept.getOrderCode().startsWith(orderCodeById)
					&& !core_dept.getOrderCode().equals(orderCodeById)) {
				if (orderCode.compareTo(core_dept.getOrderCode()) == 1) {
					return core_dept;
				}
			}
		}
		return null;
	}

	public static Core_Dept getDownMenu(long id) {
		String orderCodeById = getParentOrderCode(id);
		String orderCode = getOrderCodeById(id);
		for (int i = 0; i < temp.size(); i++) {
			Core_Dept core_dept = temp.get(i);
			if (core_dept.getOrderCode().startsWith(orderCodeById)
					&& !core_dept.getOrderCode().equals(orderCodeById)) {
				if (orderCode.compareTo(core_dept.getOrderCode()) == -1) {
					return core_dept;
				}
			}
		}
		return null;
	}

	public static String getStartOrderCode(long id) {
		String parentOrderCodeById = getParentOrderCode(id);
		String orderCodeById = getOrderCodeById(id);
		String orderCode = orderCodeById;
		for (Core_Dept core_Dept : temp) {
			if (core_Dept.getOrderCode().startsWith(parentOrderCodeById)
					&& core_Dept.getOrderCode().length() == orderCodeById
							.length()) {
				if (orderCode.compareTo(core_Dept.getOrderCode()) > 0) {
					orderCode = core_Dept.getOrderCode();
				}
			}
		}
		return orderCode;
	}

	public static String getEndOrderCode(long id) {
		String parentOrderCodeById = getParentOrderCode(id);
		String orderCodeById = getOrderCodeById(id);
		String orderCode = orderCodeById;
		for (Core_Dept core_Dept : temp) {
			if (core_Dept.getOrderCode().startsWith(parentOrderCodeById)
					&& core_Dept.getOrderCode().length() == orderCodeById
							.length()) {
				if (orderCode.compareTo(core_Dept.getOrderCode()) < 0) {
					orderCode = core_Dept.getOrderCode();
				}
			}
		}
		return orderCode;
	}

	public static String getParentOrderCode(long id) {
		String orderCodeById = getOrderCodeById(id);
		for (Core_Dept core_dept : temp) {
			if (orderCodeById.startsWith(core_dept.getOrderCode())
					&& !orderCodeById.equals(core_dept.getOrderCode())
					&& orderCodeById.length() - length == core_dept
							.getOrderCode().length()) {
				return core_dept.getOrderCode();
			}
		}
		return "";
	}

	public static String getOrderCodeById(long id) {
		for (Core_Dept core_dept : temp) {
			if (core_dept.getId().equals(id)) {
				return core_dept.getOrderCode();
			}
		}
		return null;
	}

	public static Core_Dept getDeptById(long id) {
		for (Core_Dept core_dept : temp) {
			if (core_dept.getId().equals(id)) {
				return core_dept;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("00001".compareTo("00002"));
	}
}
