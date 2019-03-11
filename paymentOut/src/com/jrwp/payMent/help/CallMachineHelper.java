package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.CallMachine;

public class CallMachineHelper {

	private List<CallMachine> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<CallMachine> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CallMachine callMachine;

	public CallMachineHelper(PageInfo<CallMachine> page) {
		this.page = page;
		items = page.getList();
	}

	public CallMachineHelper(CallMachine callMachine) {
		this.callMachine = callMachine;
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
	public List<CallMachine> getItems() {
		return items;
	}

	public void setItems(List<CallMachine> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<CallMachine> getPage() {
		return page;
	}

	public void setPage(PageInfo<CallMachine> page) {
		this.page = page;
	}

	public CallMachine getCallMachine() {
		return callMachine;
	}

	public void setCallMachine(CallMachine callMachine) {
		this.callMachine = callMachine;
	}

}
