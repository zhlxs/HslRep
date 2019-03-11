package com.jrwp.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;

public class RoleHelper {
	private List<Role> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<Core_Role> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Role role;

	public RoleHelper(PageInfo<Core_Role> page) {
		this.page = page;
		List<Core_Role> list = page.getList();
		items = new ArrayList<RoleHelper.Role>();
		for (Core_Role role : list) {
			items.add(new Role(role));
		}
	}

	public RoleHelper(Core_Role role) {
		this.role = new Role(role);
	}

	public List<Role> getItems() {
		return items;
	}

	public void setItems(List<Role> items) {
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

	public int getItemsPerPage() {
		return page.getPageSize();
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<Core_Role> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_Role> page) {
		this.page = page;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public class Role {
		private String authIDs;
		private String authNames;
		@JsonProperty("ID")
		private long id;
		private String roleName;
		private String iconCssClass;
		@JsonProperty("isSys")
		private boolean isSys;
		private List<Core_Auth> auths;

		@JsonIgnore
		private Core_Role role;

		public Role(Core_Role role) {
			this.role = role;
		}

		public String getAuthIDs() {
			List<Core_Auth> authList = role.getAuths();
			if (authList != null && authList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				for (Core_Auth core_Auth : authList) {
					long authId = core_Auth.getId();
					sb.append(authId);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
			return "";
		}

		public void setAuthIDs(String authIDs) {
			this.authIDs = authIDs;
		}

		public String getAuthNames() {
			List<Core_Auth> authList = role.getAuths();
			if (authList != null && authList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				for (Core_Auth core_Auth : authList) {
					String authName = core_Auth.getAuthName();
					sb.append(authName);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
			return "";
		}

		public void setAuthNames(String authNames) {
			this.authNames = authNames;
		}

		public long getId() {
			return role.getId();
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getRoleName() {
			return role.getRoleName();
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getIconCssClass() {
			return role.getIconCssClass();
		}

		public void setIconCssClass(String iconCssClass) {
			this.iconCssClass = iconCssClass;
		}

		public boolean getIsSys() {
			return role.getIsSys() == 0 ? false : true;
		}

		public void setIsSys(boolean isSys) {
			this.isSys = isSys;
		}

		public List<Core_Auth> getAuths() {
			return role.getAuths();
		}

		public void setAuths(List<Core_Auth> auths) {
			this.auths = auths;
		}

		public Core_Role getRole() {
			return role;
		}

		public void setRole(Core_Role role) {
			this.role = role;
		}

	}
}
