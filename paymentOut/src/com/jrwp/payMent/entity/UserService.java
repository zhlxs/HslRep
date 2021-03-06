package com.jrwp.payMent.entity;

import java.util.Date;

public class UserService {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.US_CODE
     *
     * @mbggenerated
     */
    private String usCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.UI_CODE
     *
     * @mbggenerated
     */
    private String uiCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.SER_CODE
     *
     * @mbggenerated
     */
    private String serCode;

    private String serName;



    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.CHARGE_MODE
     *
     * @mbggenerated
     */
    private String chargeMode;



    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.CREATE_USER
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USERSERVICE.CREATE_DATE
     *
     * @mbggenerated
     */
    private Date createDate;
    
    //授权类型（0：用户，1：部门）
    private int authType;
    
    public String getWriteBackUrl() {
		return writeBackUrl;
	}

	public void setWriteBackUrl(String writeBackUrl) {
		this.writeBackUrl = writeBackUrl;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	//是否删除
    private String isDel;
    
    //回写地址
    private String writeBackUrl;
    
    //支付编码
    private String payCode;
    
    //商品描述
    private String orderDescribe;

    public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.US_CODE
     *
     * @return the value of USERSERVICE.US_CODE
     *
     * @mbggenerated
     */
    public String getUsCode() {
        return usCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.US_CODE
     *
     * @param usCode the value for USERSERVICE.US_CODE
     *
     * @mbggenerated
     */
    public void setUsCode(String usCode) {
        this.usCode = usCode == null ? null : usCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.UI_CODE
     *
     * @return the value of USERSERVICE.UI_CODE
     *
     * @mbggenerated
     */
    public String getUiCode() {
        return uiCode;
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.UI_CODE
     *
     * @param uiCode the value for USERSERVICE.UI_CODE
     *
     * @mbggenerated
     */
    public void setUiCode(String uiCode) {
        this.uiCode = uiCode == null ? null : uiCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.SER_CODE
     *
     * @return the value of USERSERVICE.SER_CODE
     *
     * @mbggenerated
     */
    public String getSerCode() {
        return serCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.SER_CODE
     *
     * @param serCode the value for USERSERVICE.SER_CODE
     *
     * @mbggenerated
     */
    public void setSerCode(String serCode) {
        this.serCode = serCode == null ? null : serCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.CHARGE_MODE
     *
     * @return the value of USERSERVICE.CHARGE_MODE
     *
     * @mbggenerated
     */
    public String getChargeMode() {
        return chargeMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.CHARGE_MODE
     *
     * @param chargeMode the value for USERSERVICE.CHARGE_MODE
     *
     * @mbggenerated
     */
    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode == null ? null : chargeMode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.CREATE_USER
     *
     * @return the value of USERSERVICE.CREATE_USER
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.CREATE_USER
     *
     * @param createUser the value for USERSERVICE.CREATE_USER
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USERSERVICE.CREATE_DATE
     *
     * @return the value of USERSERVICE.CREATE_DATE
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USERSERVICE.CREATE_DATE
     *
     * @param createDate the value for USERSERVICE.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }
}