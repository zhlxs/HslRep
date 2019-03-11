package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.BusinessconfigStuff;

public class BusinessconfigStuffHelper {

	private List<BusinessconfigStuff> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<BusinessconfigStuff> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BusinessconfigStuff businessconfigStuff;

	public BusinessconfigStuffHelper(PageInfo<BusinessconfigStuff> page) {
		this.page = page;
		items = page.getList();
	}

	public BusinessconfigStuffHelper(BusinessconfigStuff businessconfigStuff) {
		this.businessconfigStuff = businessconfigStuff;
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

	public List<BusinessconfigStuff> getItems() {
		return items;
	}

	public void setItems(List<BusinessconfigStuff> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<BusinessconfigStuff> getPage() {
		return page;
	}

	public void setPage(PageInfo<BusinessconfigStuff> page) {
		this.page = page;
	}

	public BusinessconfigStuff getBusinessconfigStuff() {
		return businessconfigStuff;
	}

	public void setBusinessconfigStuff(BusinessconfigStuff businessconfigStuff) {
		this.businessconfigStuff = businessconfigStuff;
	}

}
