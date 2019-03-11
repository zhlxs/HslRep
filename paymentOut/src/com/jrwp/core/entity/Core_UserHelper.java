package com.jrwp.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;

public class Core_UserHelper {

	private List<User> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;

	@JsonIgnore
	private PageInfo<Core_User> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private User user;

	public Core_UserHelper(PageInfo<Core_User> page) {
		this.page = page;
		List<Core_User> list = page.getList();
		items = new ArrayList<Core_UserHelper.User>();
		for (Core_User core_User : list) {
			items.add(new User(core_User));
		}
	}

	public Core_UserHelper(Core_User user) {
		this.user = new User(user);
	}

	public List<User> getItems() {
		return items;
	}

	public void setItems(List<User> items) {
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

	public PageInfo<Core_User> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_User> page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public class User {
		@JsonProperty("ID")
		private Long id;
		private String deptName;
		private String deptCode;
		private Long toeDeptID;
		private String roleIDs;
		private String roleNames;
		private Long adminRegionID;
		private String userName;
		private String passWord;
		private String fullName;
		private String payCode;
		private String createBy;
		private Long payType;
		private Long deptID;
		private String phone;
		private boolean isStart;
		private boolean isSys;
		@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
		private Date createTime;
		private boolean isDel;
		@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
		private Date lastLoginTime;
		private String lastLoginIP;
		private String goodsremark;



		private Core_User user;

		public String getGoodsremark() {
			return user.getGoodsremark();
		}

		public void setGoodsremark(String goodsremark) {
			this.goodsremark = goodsremark;
		}

		public User(Core_User user) {
			this.user = user;
		}

		public Long getId() {
			return user.getId();
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDeptName() {
			return user.getDept().getDeptName();
		}

		public Long getToeDeptID() {
			return toeDeptID;
		}

		public void setToeDeptID(Long toeDeptID) {
			this.toeDeptID = toeDeptID;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public String getDeptCode() {
			return deptCode;
		}

		public void setDeptCode(String deptCode) {
			this.deptCode = deptCode;
		}

		public String getRoleIDs() {
			List<Core_Role> roleList = user.getRoles();
			if (roleList != null && roleList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				for (Core_Role core_Role : roleList) {
					Long roleId = core_Role.getId();
					sb.append(roleId);
					sb.append(",");
				}
				return sb.substring(0, sb.length() - 1);
			}
			return "";

		}

		

		public void setRoleIDs(String roleIDs) {
			this.roleIDs = roleIDs;
		}

		public String getRoleNames() {
			List<Core_Role> list = user.getRoles();
			StringBuffer sb = new StringBuffer();
			if (list != null && list.size() > 0) {
				for (Core_Role core_Role : list) {
					String rolename = core_Role.getRoleName();
					sb.append(rolename);
					sb.append(",");
				}
			}
			return sb.substring(0, sb.length() - 1);
		}

		public void setRoleNames(String roleNames) {
			this.roleNames = roleNames;
		}

		public Long getAdminRegionID() {
			return adminRegionID;
		}

		public void setAdminRegionID(Long adminRegionID) {
			this.adminRegionID = adminRegionID;
		}

		public String getUserName() {
			return user.getUserName();
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassWord() {
			return user.getPassword();
		}

		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}

		public String getFullName() {
			return user.getFullName();
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public Long getDeptID() {
			return user.getDept().getId();
		}

		public void setDeptID(Long deptID) {
			this.deptID = deptID;
		}

		public String getPhone() {
			return user.getPhone();
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public boolean getIsStart() {
			return user.getIsStart();
		}

		public void setIsStart(boolean isStart) {
			this.isStart = isStart;
		}

		public boolean getIsSys() {
			return user.getIsSys();
		}

		public void setIsSys(boolean isSys) {
			this.isSys = isSys;
		}

		public boolean getIsDel() {
			return user.getIsDel();
		}

		public void setIsDel(boolean isDel) {
			this.isDel = isDel;
		}

		public Date getCreateTime() {
			return user.getCreateTime();
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getLastLoginTime() {
			return user.getLastLoginTime();
		}

		public void setLastLoginTime(Date lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}

		public String getLastLoginIP() {
			return user.getLastLoginIP();
		}

		public void setLastLoginIP(String lastLoginIP) {
			this.lastLoginIP = lastLoginIP;
		}
		
		public String getPayCode() {
			return user.getPayCode();
		}

		public void setPayCode(String payCode) {
			this.payCode = payCode;
		}

		public String getCreateBy() {
			return user.getCreateBy();
		}

		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}

		public Long getPayType() {
			return user.getPayType();
		}

		public void setPayType(Long payType) {
			this.payType = payType;
		}

		public Core_User getUser() {
			return user;
		}

		public void setUser(Core_User user) {
			this.user = user;
		}

	}
}
