package com.jrwp.payMent.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.PhotoSample;

/**
 * 样图辅助类
 * 
 * @author hsl
 * 
 */
public class PhotoSampleHelper {
	private List<PhotoSample> items;
	private int currentPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerpage;
	private String context;
	@JsonIgnore
	private PageInfo<PhotoSample> page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PhotoSample photoSample;

	public PhotoSampleHelper(PageInfo<PhotoSample> page) {
		this.page = page;
		List<PhotoSample> list = page.getList();
		items = new ArrayList<PhotoSample>();
		for (PhotoSample photoSample : list) {
			items.add(photoSample);
		}
	}

	public PhotoSampleHelper(PhotoSample photoSample) {
		this.photoSample = photoSample;
	}

	public List<PhotoSample> getItems() {
		return items;
	}

	public void setItems(List<PhotoSample> items) {
		this.items = items;
	}

	public int getCurrentPage() {
		return page.getPageNum();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		if (totalItems == 0) {
			return 1;
		} else {
			return totalItems / getItemsPerpage() + 1;
		}
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

	public PageInfo<PhotoSample> getPage() {
		return page;
	}

	public void setPage(PageInfo<PhotoSample> page) {
		this.page = page;
	}

	public PhotoSample getPhotoSample() {
		return photoSample;
	}

	public void setPhotoSample(PhotoSample photoSample) {
		this.photoSample = photoSample;
	}

}
