package com.jrwp.wx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class DeptAddress {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.ID
     *
     * @mbggenerated
     */
    private Short id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.DEPTID
     *
     * @mbggenerated
     */
    private Short deptid;
    private String deptname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.ADDRESS
     *
     * @mbggenerated
     */
    private Object address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.LONGITUDE
     *
     * @mbggenerated
     */
    private Object longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.LATITUDE
     *
     * @mbggenerated
     */
    private Object latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column DEPT_ADDRESS.CREATETIME
     *
     * @mbggenerated
     */
    @JsonIgnore
    private Date createtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.ID
     *
     * @return the value of DEPT_ADDRESS.ID
     * @mbggenerated
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.ID
     *
     * @param id the value for DEPT_ADDRESS.ID
     * @mbggenerated
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.DEPTID
     *
     * @return the value of DEPT_ADDRESS.DEPTID
     * @mbggenerated
     */
    public Short getDeptid() {
        return deptid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.DEPTID
     *
     * @param deptid the value for DEPT_ADDRESS.DEPTID
     * @mbggenerated
     */
    public void setDeptid(Short deptid) {
        this.deptid = deptid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.ADDRESS
     *
     * @return the value of DEPT_ADDRESS.ADDRESS
     * @mbggenerated
     */
    public Object getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.ADDRESS
     *
     * @param address the value for DEPT_ADDRESS.ADDRESS
     * @mbggenerated
     */
    public void setAddress(Object address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.LONGITUDE
     *
     * @return the value of DEPT_ADDRESS.LONGITUDE
     * @mbggenerated
     */
    public Object getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.LONGITUDE
     *
     * @param longitude the value for DEPT_ADDRESS.LONGITUDE
     * @mbggenerated
     */
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.LATITUDE
     *
     * @return the value of DEPT_ADDRESS.LATITUDE
     * @mbggenerated
     */
    public Object getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.LATITUDE
     *
     * @param latitude the value for DEPT_ADDRESS.LATITUDE
     * @mbggenerated
     */
    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEPT_ADDRESS.CREATETIME
     *
     * @return the value of DEPT_ADDRESS.CREATETIME
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEPT_ADDRESS.CREATETIME
     *
     * @param createtime the value for DEPT_ADDRESS.CREATETIME
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}