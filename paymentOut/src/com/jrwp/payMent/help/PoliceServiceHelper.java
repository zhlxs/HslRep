package com.jrwp.payMent.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.jrwp.payMent.entity.PoliceClassService;

public class PoliceServiceHelper {
    public static final int length = 5;
    private List<PoliceClassService> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage;
    private String context;

    @JsonIgnore
    private PageInfo<PoliceClassService> page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PoliceClassService policeClassService;
    @JsonIgnore
    private static List<PoliceClassService> temp;

    /*public PoliceServiceHelper(PageInfo<PoliceClassService> page){
        this.page = page;
        List<PoliceClassService> list = page.getList();
        items = new ArrayList<PoliceClassService>();
        for (PoliceClassService policeClassService : list){
            items.add(policeClassService);
        }
    }*/
    public PoliceServiceHelper(){

    }
    public PoliceServiceHelper(PageInfo<PoliceClassService> page) {
        this(page.getList());
        this.page = page;

    }

    public PoliceServiceHelper(List<PoliceClassService> list) {
        List<PoliceClassService> group = PoliceServiceHelper.group(list);
        temp = list;
        items = group;
    }

    // 组合部门
    public static List<PoliceClassService> group(List<PoliceClassService> policeClassServices) {
        List<PoliceClassService> list = getChildren(policeClassServices, "");
        return list;
    }


    public static List<PoliceClassService> getChildren(List<PoliceClassService> policeClassServices, String orderCode) {
        List<PoliceClassService> list = new ArrayList<PoliceClassService>();
        for (int i = 0; i < policeClassServices.size(); i++) {
            PoliceClassService policeClassService = policeClassServices.get(i);


            if (policeClassService.getOrderCode().startsWith(orderCode) && policeClassService.getOrderCode().length()
                    == orderCode.length() + length) {

                policeClassService.setChildren(getChildren(policeClassServices, policeClassService.getOrderCode()));
                list.add(policeClassService);
            }
        }
        if (list.size() == 0) {
            list = null;
        }
        return list;
    }





    // 获得下一个编码
    public static String getInsertOrderCode(String code) {
        String str = code.substring(code.length() - length, code.length());
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(Integer.parseInt(str) + 1));
        while (sb.length() < length) {
            sb.insert(0, "0");
        }
        sb.insert(0, code.substring(0, code.length() - length));
        return sb.toString();
    }

    // 判断是否有子对象
    public static boolean isHaveChlidren(String serCode) {
        for (PoliceClassService service : temp) {
            if (service.getSerCode().equals(serCode)) {
                String orderCode = service.getOrderCode();
                for (PoliceClassService d : temp) {
                    if (d.getOrderCode().startsWith(orderCode) && !d.getOrderCode().equals(orderCode)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<PoliceClassService> getTemp() {
        return temp;
    }

    public static void setTemp(List<PoliceClassService> temp) {
        PoliceServiceHelper.temp = temp;
    }

    public List<PoliceClassService> getItems() {
        return items;
    }

    public void setItems(List<PoliceClassService> items) {
        this.items = items;
    }

    /*public int getCurrentPage() {
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
    }*/

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public PageInfo<PoliceClassService> getPage() {
        return page;
    }

    public void setPage(PageInfo<PoliceClassService> page) {
        this.page = page;
    }

    public PoliceClassService getPoliceClassService() {
        return policeClassService;
    }

    public void setPoliceClassService(PoliceClassService policeClassService) {
        this.policeClassService = policeClassService;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
