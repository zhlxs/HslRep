package com.jrwp.wx.entity;

public class CallNumParm {
    private String apiauthtoken ;
    private Integer deptid ;
    private int pageSize=1;
    private int pageNum= 10;

    public String getApiauthtoken() {
        return apiauthtoken;
    }

    public void setApiauthtoken(String apiauthtoken) {
        this.apiauthtoken = apiauthtoken;
    }



    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}
