package com.jrwp.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;

public class LogHelper {
	private List<Log> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;
	@JsonIgnore
	private PageInfo<Core_Log> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Log log;

	public LogHelper(PageInfo<Core_Log> page) {
		this.page = page;
		List<Core_Log> list = page.getList();
		items = new ArrayList<Log>();
		for (Core_Log core_Log : list) {
			items.add(new Log(core_Log));
		}
	}

	public LogHelper(Core_Log log) {
		this.log = new Log(log);
	}

	public List<Log> getItems() {
		return items;
	}

	public void setItems(List<Log> items) {
		this.items = items;
	}

	public int getCurrentPage() {
		return page.getPageNum();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return page.getPages();
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

	public PageInfo<Core_Log> getPage() {
		return page;
	}

	public void setPage(PageInfo<Core_Log> page) {
		this.page = page;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public class Log extends Core_Log {
		private String fullName;
		private String deptName;
		@JsonProperty("userID")
		private Long userId;

		@JsonIgnore
		private Core_Log log;

		public Log(Core_Log log) {
			this.log = log;
		}

		public String getFullName() {
			return this.log.getUser().getFullName();
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getDeptName() {
			//return this.log.getUser().getDept().getDeptName();
			if(log.getUser().getDept()==null){
				return "";
			}else{
				return log.getUser().getDept().getDeptName();
			}
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public Long getUserId() {
			return this.log.getUser().getId();
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Core_Log getLog() {
			return log;
		}

		public void setLog(Core_Log log) {
			this.log = log;
		}

		@Override
		public String getActionDisplay() {
			return this.getLog().getActionDisplay();
		}

		@Override
		public String getActionName() {
			return this.getLog().getActionName();
		}

		@Override
		public String getAreaName() {
			return this.getLog().getAreaName();
		}

		@Override
		public String getControlDisplay() {
			return this.getLog().getControlDisplay();
		}

		@Override
		public String getControlName() {
			return this.getLog().getControlName();
		}

		@Override
		public Long getId() {
			return this.getLog().getId();
		}

		@Override
		public String getParameterJson() {
			return this.getLog().getParameterJson();
		}

		@Override
		public String getRecordIP() {
			return this.getLog().getRecordIP();
		}

		public Date getRecordTime() {
			return this.getLog().getRecordTime();
		}

	}
}
