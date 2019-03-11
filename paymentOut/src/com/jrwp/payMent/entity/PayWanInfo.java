package com.jrwp.payMent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class PayWanInfo {
    //主键
    private Integer id;
    //业务ID
    private String workId;
    //身份证号码
    private String idNumber;
    //机器码
    private String machineId;
    //设备类型 0自助拍照机，1自助领证机，2自助申请机 3 签注机 5 交警拍照机 6 警银通
    private String machineType;
    //支付金额
    private BigDecimal payMoney;
    //支付二维码
   // private bayte[]

    //支付类型0未知 1支付宝 2微信
    private String payType;
    //支付状态0待生成 1待支付 2成功 3失败
    private String payStatus;
    //创建时间
    private String createTime;
    //支付时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    //是否导出 0未导出 1已导出
//    private String isExport;
    //支付ID
    private Integer payId;

    //二维码类型 0图片， 1Url

   // private String qrcodeType;
   // 订单号
    private String orderNumber;
    //二维码Url
   // private String qrcodeUrl;
    //部门编号
    //private String deptCode;
    //商品描述
    private String orderDescribe;

   // private Integer onlyIn;
    //部门名称
    //private String deptName;
    //配置名称
    private String configName;
    //机器名称
    private String refundStatus;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineType() {
        //设备类型 0自助拍照机，1自助领证机，2自助申请机 3 签注机 5 交警拍照机 6 警银通
        if (machineType.equals("0")){
            return "自助拍照机";
        }else if(machineType.equals("1")){
            return "自助领证机";
        }else  if(machineType.equals("2")){
            return "自助申请机";
        }else  if(machineType.equals("3")){
            return "签注机";
        }else  if(machineType.equals("5")){
            return "交警拍照机";
        }else  if(machineType.equals("6")){
            return "警银通";
        }
        return "";
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayType() {
        if (payType.equals("0")){
            return "未知";
        }else if(payType.equals("1")){
            return "支付宝";
        }else  if(payType.equals("2")){
            return "微信";
        }
        return "";
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayStatus() {
        if (payStatus.equals("0")){
            return "待生成";
        }else if(payStatus.equals("1")){
            return "待支付";
        }else  if(payStatus.equals("2")){
            return "成功";
        }else  if(payStatus.equals("3")){
            return "失败";
        }
        return "";
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



//    public String getIsExport() {
//        if (isExport.equals("0")){
//            return "未导出";
//        }else if(isExport.equals("1")){
//            return "已导出";
//        }
//        return "";
//    }

//    public void setIsExport(String isExport) {
//        this.isExport = isExport;
//    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

//    public String getQrcodeType() {
//        if (qrcodeType.equals("0")){
//            return "图片";
//        }else if(qrcodeType.equals("1")){
//            return "url";
//        }
//        return "";
//    }

//    public void setQrcodeType(String qrcodeType) {
//        this.qrcodeType = qrcodeType;
//    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

//    public String getQrcodeUrl() {
//        return qrcodeUrl;
//    }
//
//    public void setQrcodeUrl(String qrcodeUrl) {
//        this.qrcodeUrl = qrcodeUrl;
//    }
//
//    public String getDeptCode() {
//        return deptCode;
//    }
//
//    public void setDeptCode(String deptCode) {
//        this.deptCode = deptCode;
//    }

    public String getOrderDescribe() {
        return orderDescribe;
    }

    public void setOrderDescribe(String orderDescribe) {
        this.orderDescribe = orderDescribe;
    }

   // public Integer getOnlyIn() {
//        return onlyIn;
//    }

//    //public void setOnlyIn(Integer onlyIn) {
//        this.onlyIn = onlyIn;
//    } //public void setOnlyIn(Integer onlyIn) {
////        this.onlyIn = onlyIn;
////    }

//    public String getDeptName() {
//        return deptName;
//    }
//
//    public void setDeptName(String deptName) {
//        this.deptName = deptName;
//    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

//    public String getMachineName() {
//        return machineName;
//    }
//
//    public void setMachineName(String machineName) {
//        this.machineName = machineName;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }
}
