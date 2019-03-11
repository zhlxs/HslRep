package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.DicDeptaddress;

public class DicDeptaddressHelper {

	private List<DicDeptaddress> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<DicDeptaddress> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DicDeptaddress dicDeptaddress;

	public DicDeptaddressHelper(PageInfo<DicDeptaddress> page) {
		this.page = page;
		items = page.getList();
	}

	public DicDeptaddressHelper(DicDeptaddress dicDeptaddress) {
		this.dicDeptaddress = dicDeptaddress;
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
	public List<DicDeptaddress> getItems() {
		return items;
	}

	public void setItems(List<DicDeptaddress> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<DicDeptaddress> getPage() {
		return page;
	}

	public void setPage(PageInfo<DicDeptaddress> page) {
		this.page = page;
	}

	public DicDeptaddress getDicDeptaddress() {
		return dicDeptaddress;
	}

	public void setDicDeptaddress(DicDeptaddress dicDeptaddress) {
		this.dicDeptaddress = dicDeptaddress;
	}

}