package com.jrwp.payMent.service.impl;

import com.jrwp.core.entity.Core_User;
import com.jrwp.payMent.dao.PayOrderDetailsMapper;
import com.jrwp.payMent.dao.PayOrdersMapper;
import com.jrwp.payMent.entity.OrderCount;
import com.jrwp.payMent.entity.PayOrderDetails;
import com.jrwp.payMent.entity.PayOrders;
import com.jrwp.payMent.service.OrdersService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {


    @Resource
    private PayOrdersMapper ordersMapper;
    @Resource
    private PayOrderDetailsMapper orderDetailsMapper;

    @Override
    public PayOrderDetails getDetailsById(BigDecimal id) {
        return orderDetailsMapper.getDetailsById(id);
    }

    @Override
    public List<PayOrderDetails> getDetailsList(Long userid) {
        return orderDetailsMapper.getDetailsList(userid);
    }

	@Override
	public List<PayOrders> getDetailsListByTime(Long userid,
			String paystatus,String workprocessnumber, String begintime, String endtime) {
		
		return ordersMapper.getDetailsListByTime(userid, paystatus,workprocessnumber, begintime, endtime);
	}

    @Override
    public List<OrderCount> getOrderCount(Core_User user,String paystatus, String begintime, String endtime) {
        List<OrderCount> list = new ArrayList<OrderCount>();
        Long userid = user.getId();
        Integer isAdmin = user.getIsAdmin();
        if (isAdmin==0){
            //普通用户
            list = orderDetailsMapper.getOrderCount(userid, paystatus, begintime, endtime);
        }else if (isAdmin==1){
            //部门管理员
            Long deptid = user.getDept().getId();
            list = orderDetailsMapper.getOrderCountBydeptid(deptid, paystatus, begintime, endtime);

        }else if (isAdmin==2){
            //超级管理员
            list = orderDetailsMapper.getAllOrderCount( paystatus, begintime, endtime);
        }
        return  list;
    }

    @Override
    public List<PayOrders> getOrderList(Long userid, String paystatus, String workprocessnumber, String begintime, String endtime) {
        return ordersMapper.getOrderList(userid, paystatus,workprocessnumber, begintime, endtime);
    }

    @Override
    public List<PayOrders> getOrderListCondiction(Long userid, String paystatus, String workprocessnumber, String begintime, String endtime, Core_User user) {
       List<PayOrders> list = new ArrayList<PayOrders>();
       Integer isAdmin = user.getIsAdmin();
        if (isAdmin==0){
            //普通用户
            list= ordersMapper.getOrderList(userid, paystatus,workprocessnumber, begintime, endtime);
        }else if (isAdmin==1){
            //部门管理员
            Long deptid = user.getDept().getId();
            list= ordersMapper.getOrderListBydeptid(deptid, paystatus,workprocessnumber, begintime, endtime);

        }else if (isAdmin==2){
            //超级管理员
            list = ordersMapper.getAllOrderList(paystatus,workprocessnumber, begintime, endtime);
        }
        return  list;
    }



    @Override
    public PayOrders getOrderByid(Long id) {
        return ordersMapper.getOrderByid(id);
    }
}
