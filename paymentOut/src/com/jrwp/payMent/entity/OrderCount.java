package com.jrwp.payMent.entity;

import java.math.BigDecimal;

public class OrderCount {
    private String bizName;
    private Integer bizCount=0;
    private BigDecimal price=new BigDecimal("0");
    private BigDecimal total=new BigDecimal("0");


    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }



    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        if(this.price.compareTo(new BigDecimal("0")) == 0){
            return  total;
        }else{

            return new BigDecimal(this.price.toString()).multiply(new BigDecimal(this.bizCount.toString()));
        }
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getBizCount() {
        return bizCount;
    }

    public void setBizCount(Integer bizCount) {
        this.bizCount = bizCount;
    }
}

