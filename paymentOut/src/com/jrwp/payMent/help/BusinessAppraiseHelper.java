package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.BusinessAppraise;

public class BusinessAppraiseHelper {

	private List<BusinessAppraise> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<BusinessAppraise> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BusinessAppraise businessAppraise;

	public BusinessAppraiseHelper(PageInfo<BusinessAppraise> page) {
		this.page = page;
		items = page.getList();
	}

	public BusinessAppraiseHelper(BusinessAppraise businessAppraise) {
		this.businessAppraise = businessAppraise;
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

	// 其他
	public List<BusinessAppraise> getItems() {
		return items;
	}

	public void setItems(List<BusinessAppraise> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<BusinessAppraise> getPage() {
		return page;
	}

	public void setPage(PageInfo<BusinessAppraise> page) {
		this.page = page;
	}

	public BusinessAppraise getBusinessAppraise() {
		return businessAppraise;
	}

	public void setBusinessAppraise(BusinessAppraise businessAppraise) {
		this.businessAppraise = businessAppraise;
	}

}
