package com.jrwp.payMent.help;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.HotelUpdate;
import com.jrwp.payMent.entity.PayOrders;
import com.jrwp.payMent.entity.HotelUpdate;

import java.util.List;

public class UpdateIndexHelper {

    private List<HotelUpdate> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<HotelUpdate> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PayOrders payOrders;
    public UpdateIndexHelper(PageInfo<HotelUpdate> page){
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
        this.itemsPerPage = itemsPerPage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public PageInfo<HotelUpdate> getPage() {
        return page;
    }

    public void setPage(PageInfo<HotelUpdate> page) {
        this.page = page;
    }

    public List<HotelUpdate> getItems() {
        return items;
    }

    public void setItems(List<HotelUpdate> items) {
        this.items = items;
    }

    public PayOrders getPayOrders() {
        return payOrders;
    }

    public void setPayOrders(PayOrders payOrders) {
        this.payOrders = payOrders;
    }
}
