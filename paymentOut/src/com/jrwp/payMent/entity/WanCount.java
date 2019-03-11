package com.jrwp.payMent.entity;

public class WanCount {
    //设备类型
    private String machinetype;
    //收款方ID
    private int conid;
    //收款方名称
    private String name;
    //数量
    private Integer count;
    //金额
    private double total;

    public String getMachinetype() {
        //设备类型 0自助拍照机，1自助领证机，2自助申请机 3 签注机 5 交警拍照机 6 警银通
        if (machinetype.equals("0")) {
            return "自助拍照机";
        } else if (machinetype.equals("1")) {
            return "自助领证机";
        } else if (machinetype.equals("2")) {
            return "自助申请机";
        } else if (machinetype.equals("3")) {
            return "签注机";
        } else if (machinetype.equals("5")) {
            return "交警拍照机";
        } else if (machinetype.equals("6")) {
            return "警银通";
        }
        return "";
    }

    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }

    public int getConid() {
        return conid;
    }

    public void setConid(int conid) {
        this.conid = conid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
