package com.jrwp.wx.entity;

import java.util.Date;

public class FrequentContacts {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.NAME
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.PHONE
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.CARDNUMBER
     *
     * @mbggenerated
     */
    private String cardnumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.OPENID
     *
     * @mbggenerated
     */
    private String openid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.CREATETIME
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FREQUENT_CONTACTS.UPDATETIME
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.ID
     *
     * @return the value of FREQUENT_CONTACTS.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.ID
     *
     * @param id the value for FREQUENT_CONTACTS.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.NAME
     *
     * @return the value of FREQUENT_CONTACTS.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.NAME
     *
     * @param name the value for FREQUENT_CONTACTS.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.PHONE
     *
     * @return the value of FREQUENT_CONTACTS.PHONE
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.PHONE
     *
     * @param phone the value for FREQUENT_CONTACTS.PHONE
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.CARDNUMBER
     *
     * @return the value of FREQUENT_CONTACTS.CARDNUMBER
     *
     * @mbggenerated
     */
    public String getCardnumber() {
        return cardnumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.CARDNUMBER
     *
     * @param cardnumber the value for FREQUENT_CONTACTS.CARDNUMBER
     *
     * @mbggenerated
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.OPENID
     *
     * @return the value of FREQUENT_CONTACTS.OPENID
     *
     * @mbggenerated
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.OPENID
     *
     * @param openid the value for FREQUENT_CONTACTS.OPENID
     *
     * @mbggenerated
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.CREATETIME
     *
     * @return the value of FREQUENT_CONTACTS.CREATETIME
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.CREATETIME
     *
     * @param createtime the value for FREQUENT_CONTACTS.CREATETIME
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FREQUENT_CONTACTS.UPDATETIME
     *
     * @return the value of FREQUENT_CONTACTS.UPDATETIME
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FREQUENT_CONTACTS.UPDATETIME
     *
     * @param updatetime the value for FREQUENT_CONTACTS.UPDATETIME
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}