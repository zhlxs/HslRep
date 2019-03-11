package com.jrwp.payMent.service;

import com.jrwp.core.entity.Core_User;
import com.jrwp.payMent.entity.OrderCount;
import com.jrwp.payMent.entity.PayOrderDetails;

import java.math.BigDecimal;
import java.util.List;

import com.jrwp.payMent.entity.PayOrders;
import org.apache.ibatis.annotations.Param;

public interface OrdersService {

    PayOrderDetails getDetailsById(BigDecimal id);

    List<PayOrderDetails> getDetailsList(Long userid);
    
    List<PayOrders> getDetailsListByTime(Long userid,String paystatus,String workprocessnumber,String begintime,
			String endtime);
    List<OrderCount> getOrderCount( Core_User user,String paystatus,String begintime,
                                    String endtime);

    List<PayOrders> getOrderList(Long userid,String paystatus,String workprocessnumber,String begintime,
                                 String endtime);

    List<PayOrders> getOrderListCondiction(Long userid,String paystatus,String workprocessnumber,String begintime,
                                 String endtime,Core_User user);

    PayOrders getOrderByid( Long id);
}
