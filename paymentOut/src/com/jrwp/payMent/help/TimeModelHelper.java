package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.Time_Model;

public class TimeModelHelper {

	private List<Time_Model> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<Time_Model> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Time_Model time_Model;

	public TimeModelHelper(PageInfo<Time_Model> page) {
		this.page = page;
		items = page.getList();
	}

	public TimeModelHelper(Time_Model time_Model) {
		this.time_Model = time_Model;
	}

	// 分页
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

	public int getItemsPerPage() {
		return page.getPageSize();
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	// Others
	public List<Time_Model> getItems() {
		return items;
	}

	public void setItems(List<Time_Model> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<Time_Model> getPage() {
		return page;
	}

	public void setPage(PageInfo<Time_Model> page) {
		this.page = page;
	}

	public Time_Model getTime_Model() {
		return time_Model;
	}

	public void setTime_Model(Time_Model time_Model) {
		this.time_Model = time_Model;
	}

}
