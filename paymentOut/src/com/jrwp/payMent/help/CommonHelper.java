package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;

public class CommonHelper<H> {

	private List<H> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<H> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private H object;

	public CommonHelper(PageInfo<H> page) {
		this.page = page;
		items = page.getList();
	}

	public CommonHelper(H object) {
		this.object = object;
	}

	// page Constructors
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

	// Other Constructors
	public List<H> getItems() {
		return items;
	}

	public void setItems(List<H> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<H> getPage() {
		return page;
	}

	public void setPage(PageInfo<H> page) {
		this.page = page;
	}

	public H getObject() {
		return object;
	}

	public void setObject(H object) {
		this.object = object;
	}

}
