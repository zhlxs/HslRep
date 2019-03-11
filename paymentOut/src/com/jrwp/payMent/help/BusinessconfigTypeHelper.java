package com.jrwp.payMent.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.BusinessconfigType;
import com.jrwp.payMent.entity.PoliceClassService;

public class BusinessconfigTypeHelper {
	public static final int length = 5;
	private static List<BusinessconfigType> items;
	@JsonIgnore
	private static List<BusinessconfigType> temp;
	private static List<ApplyType> item;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;
	@JsonIgnore
	private PageInfo<BusinessconfigType> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ApplyType applyType;

	public BusinessconfigTypeHelper(PageInfo<BusinessconfigType> page) {
		this.page = page;
		List<BusinessconfigType> list = page.getList();
		items = new ArrayList<BusinessconfigType>();
		for (BusinessconfigType applyType : list) {
			items.add(new ApplyType(applyType));
			// items.add(applyType);
		}
	}

	public BusinessconfigTypeHelper(BusinessconfigType applyType) {
		this.applyType = new ApplyType(applyType);
	}

	public List<BusinessconfigType> getItems() {
		return items;
	}

	public void setItems(List<BusinessconfigType> items) {
		this.items = items;
	}

	public int getCurrentPage() {
		return page.getPageNum();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		if (totalItems == 0) {
			return 1;
		} else {
			return totalItems / getItemsPerpage() + 1;
		}
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return new Long(page.getTotal()).intValue();
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

	public PageInfo<BusinessconfigType> getPage() {
		return page;
	}

	public void setPage(PageInfo<BusinessconfigType> page) {
		this.page = page;
	}

	public BusinessconfigType getApplyType() {
		return applyType;
	}

	public void setApplyType(ApplyType applyType) {
		this.applyType = applyType;
	}

	public static List<ApplyType> getItem() {
		return item;
	}

	public static void setItem(List<ApplyType> item) {
		BusinessconfigTypeHelper.item = item;
	}

	// 移动
	public static List<BusinessconfigType> move(long id, int movetype)
			throws Exception {
		String startOrderCode = getStartOrderCode(id);
		String endOrderCode = getEndOrderCode(id);
		String orderCode = getOrderCodeById(id);
		BusinessconfigType applyType = null;
		if (movetype == 1) {
			if (orderCode.equals(startOrderCode)) {
				throw new Exception("已经排在此分类第一了！");
			}
			applyType = getUpMenu(id);
			String code = applyType.getOrderCode();
			changeMenuOrderCode(applyType, orderCode);
			changeMenuOrderCode(getDrupById(id), code);
		} else {
			if (orderCode.equals(endOrderCode)) {
				throw new Exception("已经排在此分类末尾了！");
			}
			applyType = getDownMenu(id);
			String code = applyType.getOrderCode();
			changeMenuOrderCode(applyType, orderCode);
			changeMenuOrderCode(getDrupById(id), code);
		}
		return items;
	}

	private static void changeMenuOrderCode(BusinessconfigType applyType,
			String code) {
		StringBuilder replace = new StringBuilder(applyType.getOrderCode())
				.replace(0, code.length(), code);
		applyType.setOrderCode(replace.toString());
	}

	private static BusinessconfigType getUpMenu(long id) {
		String orderCode = getOrderCodeById(id);
		for (int i = items.size() - 1; i >= 0; i--) {
			BusinessconfigType weApplytype = items.get(i);
			if (!weApplytype.getOrderCode().equals(orderCode)) {
				if (Integer.parseInt(orderCode)
						- Integer.parseInt(weApplytype.getOrderCode()) >= 1) {
					return weApplytype;
				}
			}
		}
		return null;
	}

	private static BusinessconfigType getDownMenu(long id) {
		String orderCode = getOrderCodeById(id);
		for (int i = 0; i < items.size(); i++) {
			BusinessconfigType weApplytype = items.get(i);
			if (!weApplytype.getOrderCode().equals(orderCode)) {
				if (Integer.parseInt(weApplytype.getOrderCode())
						- Integer.parseInt(orderCode) >= 1) {
					return weApplytype;
				}
			}
		}
		return null;
	}

	// 获取最大排序码
	private static String getEndOrderCode(long id) {
		String orderCode = getOrderCodeById(id);
		for (BusinessconfigType weApplytype : items) {
			if (orderCode.compareTo(weApplytype.getOrderCode()) < 0) {
				orderCode = weApplytype.getOrderCode();
			}
		}
		return orderCode;
	}

	// 获取最小排序码
	private static String getStartOrderCode(long id) {
		String orderCode = getOrderCodeById(id);
		for (BusinessconfigType weApplytype : items) {
			if (orderCode.compareTo(weApplytype.getOrderCode()) > 0) {
				orderCode = weApplytype.getOrderCode();
			}
		}
		return orderCode;
	}

	// 获取操作对象排序码
	public static String getOrderCodeById(long id) {
		for (BusinessconfigType weApplytype : items) {
			if (weApplytype.getId().equals(id)) {
				return weApplytype.getOrderCode();
			}
		}
		return null;
	}

	// 获取当前操作对象
	private static BusinessconfigType getDrupById(long id) {
		for (BusinessconfigType weApplytype : items) {
			if (weApplytype.getId().equals(id)) {
				return weApplytype;
			}
		}
		return null;
	}

	public class ApplyType extends BusinessconfigType {

		private String configName;
		@JsonIgnore
		private BusinessconfigType applyType;

		public ApplyType(BusinessconfigType applyType) {
			this.applyType = applyType;
		}

		public Long getId() {
			return applyType.getId();
		}

		public PoliceClassService getPoliceClassService() {
			return applyType.getPoliceClassService();
		}

		public String getSerCode() {
			return applyType.getSerCode();
		}

		public String getApplyTypeName() {
			return applyType.getApplyTypeName();
		}

		public Boolean getIsValid() {
			return applyType.getIsValid();
		}

		public String getOrderCode() {
			return applyType.getOrderCode();
		}

		public String getConfigName() {
			return applyType.getPoliceClassService().getSerName();
		}

		public void setConfigName(String configName) {
			this.configName = configName;
		}

	}
}
