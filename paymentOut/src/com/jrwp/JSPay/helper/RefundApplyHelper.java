package com.jrwp.JSPay.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.JSPay.entity.RefundApply;

import java.util.List;

public class RefundApplyHelper {
    private List<RefundApply> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<RefundApply> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RefundApply RefundApply;
    public RefundApplyHelper(PageInfo<RefundApply> page){
        this.page = page;
        items = page.getList();
    }
    public RefundApplyHelper( ){

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

    public PageInfo<RefundApply> getPage() {
        return page;
    }

    public void setPage(PageInfo<RefundApply> page) {
        this.page = page;
    }

    public List<RefundApply> getItems() {
        return items;
    }

    public void setItems(List<RefundApply> items) {
        this.items = items;
    }

    public RefundApply getRefundApply() {
        return RefundApply;
    }

    public void setRefundApply(RefundApply RefundApply) {
        this.RefundApply = RefundApply;
    }
}
