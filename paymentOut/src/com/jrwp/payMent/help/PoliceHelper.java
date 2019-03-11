package com.jrwp.payMent.help;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.Core_Police;

import java.util.ArrayList;
import java.util.List;

public class PoliceHelper {

    private List<Core_Police> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<Core_Police> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Core_Police police;

    public PoliceHelper(PageInfo<Core_Police> page) {
        this.page = page;
        List<Core_Police> list = page.getList();
        items = new ArrayList<Core_Police>();
        for (Core_Police core_Police : list) {
            items.add(core_Police);
        }
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

    public PageInfo<Core_Police> getPage() {
        return page;
    }

    public void setPage(PageInfo<Core_Police> page) {
        this.page = page;
    }

    public List<Core_Police> getItems() {
        return items;
    }

    public void setItems(List<Core_Police> items) {
        this.items = items;
    }

    public Core_Police getPolice() {
        return police;
    }

    public void setPolice(Core_Police police) {
        this.police = police;
    }
}
