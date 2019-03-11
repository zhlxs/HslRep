package com.jrwp.payMent.help;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.WanCount;

import java.util.List;

public class WanCountHelper {

    private List<WanCount> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;
    @JsonIgnore
    private PageInfo<WanCount> page;

    public PageInfo<WanCount> getPage() {
        return page;
    }

    public void setPage(PageInfo<WanCount> page) {
        this.page = page;
    }

    public WanCount getWanCount() {
        return wanCount;
    }

    public void setWanCount(WanCount wanCount) {
        this.wanCount = wanCount;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private WanCount wanCount;
    public WanCountHelper(PageInfo<WanCount> page){
        this.page = page;
        items = page.getList();

    }
    public WanCountHelper(){

    }
    public List<WanCount> getItems() {
        return items;
    }

    public void setItems(List<WanCount> items) {
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

    public int getItemsPerPage() {
        return page.getPageSize();
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
