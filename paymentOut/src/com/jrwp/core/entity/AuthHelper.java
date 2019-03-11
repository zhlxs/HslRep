package com.jrwp.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;

/**
 * 权限辅助类
 * 
 * @author USER
 * 
 */
public class AuthHelper {
	private List<Auth> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;
	
	@JsonIgnore
	private PageInfo<Core_Auth> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Auth auth;

	public AuthHelper(PageInfo<Core_Auth> page) {
		this.page = page;
		List<Core_Auth> list = page.getList();
		items = new ArrayList<AuthHelper.Auth>();
		for (Core_Auth core_Auth : list) {
			items.add(new Auth(core_Auth));
		}
	}

	public AuthHelper(Core_Auth auth) {
		this.auth = new Auth(auth);

	}

	public List<Auth> getItems() {
		return items;
	}

	public void setItems(List<Auth> items) {
		this.items = items;
	}

	public int getCurrentPage() {
		return page.getPageNum();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return new Long(page.getTotal()).intValue();
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalItems() {
		return page.getSize();
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

	public PageInfo<Core_Auth> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_Auth> page) {
		this.page = page;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public class Auth {
		@JsonProperty("ID")
		private Long id;
		private String authName;
		private String actionNames;
		private String actionValues;
		private List<Action> actions;
		@JsonIgnore
		private Core_Auth auth;

		public Auth(Core_Auth auth) {
			this.auth = auth;
		}

		public long getId() {
			return auth.getId();
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getAuthName() {
			return auth.getAuthName();
		}

		public void setAuthName(String authName) {
			this.authName = authName;
		}

		public String getActionNames() {
			List<Core_ActionForAuth> actionList = auth.getActions();
			if (actionList != null && actionList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				for (Core_ActionForAuth core_Auth : actionList) {
					String authName = core_Auth.getDisplay();
					sb.append(authName);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
			return "";
		}

		public void setActionNames(String actionNames) {
			this.actionNames = actionNames;
		}

		public String getActionValues() {
			List<Core_ActionForAuth> actionList = auth.getActions();
			if (actionList != null && actionList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				for (Core_ActionForAuth core_Auth : actionList) {
					String authValue = core_Auth.getControlName() + "|"
							+ core_Auth.getActionName();
					sb.append(authValue);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
			return "";
		}

		public void setActionValues(String actionValues) {
			this.actionValues = actionValues;
		}

		public List<Action> getActions() {
			return actions;
		}

		public void setActions(List<Action> actions) {
			this.actions = actions;
		}

		public Core_Auth getAuth() {
			return auth;
		}

		public void setAuth(Core_Auth auth) {
			this.auth = auth;
		}

	}
}
