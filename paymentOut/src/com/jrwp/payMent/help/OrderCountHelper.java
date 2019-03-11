package com.jrwp.payMent.help;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.OrderCount;
import com.jrwp.payMent.entity.PayOrderDetails;

import java.util.List;

public class OrderCountHelper {

    private List<OrderCount> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<OrderCount> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderCount OrderCount;
    public OrderCountHelper(PageInfo<OrderCount> page){
        this.page = page;
        items = page.getList();

    }
    public OrderCountHelper(){

    }
    public List<OrderCount> getItems() {
        return items;
    }

    public void setItems(List<OrderCount> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return page.getPageNum();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return new Long(page.getTotal()).intValue();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return page.getSize();
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

    public PageInfo<OrderCount> getPage() {
        return page;
    }

    public void setPage(PageInfo<OrderCount> page) {
        this.page = page;
    }


    public com.jrwp.payMent.entity.OrderCount getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(com.jrwp.payMent.entity.OrderCount orderCount) {
        OrderCount = orderCount;
    }
}
