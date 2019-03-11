package com.jrwp.payMent.help;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.PayOrderDetails;
import com.jrwp.payMent.entity.PayOrders;
import com.jrwp.payMent.entity.PoliceClassService;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsHelper {

    private List<PayOrders> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<PayOrders> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PayOrderDetails PayOrderDetails;
    public OrderDetailsHelper(PageInfo<PayOrders> pageInfo){
        this.page = pageInfo;
        items = pageInfo.getList();

    }
    public OrderDetailsHelper(){

    }
    public List<PayOrders> getItems() {
        return items;
    }

    public void setItems(List<PayOrders> items) {
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

    public PageInfo<PayOrders> getPage() {
        return page;
    }

    public void setPage(PageInfo<PayOrders> page) {
        this.page = page;
    }


    public com.jrwp.payMent.entity.PayOrderDetails getPayOrderDetails() {
        return PayOrderDetails;
    }

    public void setPayOrderDetails(com.jrwp.payMent.entity.PayOrderDetails payOrderDetails) {
        PayOrderDetails = payOrderDetails;
    }
}
