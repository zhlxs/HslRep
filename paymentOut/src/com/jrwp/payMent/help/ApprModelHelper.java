package com.jrwp.payMent.help;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.AppraiseModel;
import com.jrwp.payMent.entity.MachineInfo;
import com.jrwp.payMent.entity.AppraiseModel;

import java.util.List;

public class ApprModelHelper {
	private List<AppraiseModel> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;

	@JsonIgnore
	private PageInfo<AppraiseModel> page;

	public ApprModelHelper(PageInfo<AppraiseModel> page) {
		this.page = page;
		items = page.getList();
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

	public int getItemsPerPage() {
		return page.getPageSize();
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerpage = itemsPerPage;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<AppraiseModel> getPage() {
		return page;
	}

	public void setPage(PageInfo<AppraiseModel> page) {
		this.page = page;
	}

	public List<AppraiseModel> getItems() {
		return items;
	}

	public void setItems(List<AppraiseModel> items) {
		this.items = items;
	}
	
}
