package com.jrwp.payMent.help;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.BusinessconfigStuff;
import com.jrwp.payMent.entity.DicBusinessnotice;

public class DicBusinessnoticeHelper {

	private List<DicBusinessnotice> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String context;

	@JsonIgnore
	private PageInfo<DicBusinessnotice> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DicBusinessnotice dicBusinessnotice;

	public DicBusinessnoticeHelper(PageInfo<DicBusinessnotice> page) {
		this.page = page;
		items = page.getList();
	}

	public DicBusinessnoticeHelper(DicBusinessnotice dicBusinessnotice) {
		this.dicBusinessnotice = dicBusinessnotice;
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
	public List<DicBusinessnotice> getItems() {
		return items;
	}

	public void setItems(List<DicBusinessnotice> items) {
		this.items = items;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public PageInfo<DicBusinessnotice> getPage() {
		return page;
	}

	public void setPage(PageInfo<DicBusinessnotice> page) {
		this.page = page;
	}

	public DicBusinessnotice getDicBusinessnotice() {
		return dicBusinessnotice;
	}

	public void setDicBusinessnotice(DicBusinessnotice dicBusinessnotice) {
		this.dicBusinessnotice = dicBusinessnotice;
	}

}
