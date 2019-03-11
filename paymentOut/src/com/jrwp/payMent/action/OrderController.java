package com.jrwp.payMent.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.OrderCount;
import com.jrwp.payMent.entity.PayOrderDetails;
import com.jrwp.payMent.entity.PayOrders;
import com.jrwp.payMent.help.OrderCountHelper;
import com.jrwp.payMent.help.OrderDetailsHelper;
import com.jrwp.payMent.help.OrdersHelper;
import com.jrwp.payMent.service.OrdersService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Description("订单管理")
@Controller
@RequestMapping("orderController")
public class OrderController {
    @Resource
    private OrdersService ordersService;
    @Resource
    private IUserService userService;

    @Description("订单列表")
    @RequestMapping("orderList")
    public ModelAndView orderList(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("order/orderList");

        return model;
    }

    @Description("订单列表JSON")
    @RequestMapping("orderListJson")
    @ResponseBody
    public OrdersHelper orderListJson(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String paystatus, String
                                                  begintime, String endtime, String workprocessnumber,String deptcode) {
        OrdersHelper ordersHelper = null;
        List<PayOrders> list = null;
        try {
            Core_User user = (Core_User) session.getAttribute("user");
            user = userService.getByUserName(user.getUserName());
            Long userid = user.getId();
            PageHelper.startPage(pageNum, pageSize);
            list = ordersService.getOrderListCondiction(userid, paystatus, workprocessnumber, begintime, endtime, user);
            PageInfo<PayOrders> pageInfo = new PageInfo<PayOrders>(list);
            pageInfo.setTotal(list.size());
            ordersHelper = new OrdersHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordersHelper;
    }

    @Description("订单详情页")
    @RequestMapping("orderDetails")
    @ResponseBody
    public ModelAndView orderDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/orderdetails");
        try {

            PayOrders payOrders = ordersService.getOrderByid(id);
            modelAndView.addObject("payOrders", payOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /*@Description("订单列表条件查询")
    @RequestMapping("orderQueryByTime")
    @ResponseBody
    public OrderDetailsHelper orderQueryByTime(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
    @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String paystatus, String begintime, String endtime, String
    workprocessnumber) {
        OrderDetailsHelper orderDetailsHelper = null;
        List<PayOrders> list = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            Core_User user = (Core_User) session.getAttribute("user");
            user = userService.getByUserName(user.getUserName());
            Long userid = user.getId();
            list = ordersService.getDetailsListByTime(userid, paystatus, workprocessnumber, begintime, endtime);
            PageInfo<PayOrders> pageInfo = new PageInfo<PayOrders>();
            orderDetailsHelper = new OrderDetailsHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderDetailsHelper;
    }
*/
    @Description("订单数据统计页面")
    @RequestMapping("orderCount")
    public ModelAndView orderCount() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sjtj/orderCount");
        return modelAndView;
    }



	
	
	/*@Description("订单信息打印")
	@RequestMapping("printModel")
	@ResponseBody
	public DoResult printModel(HttpSession session,@RequestParam(value = "page", defaultValue = "1") int pageNum,
			   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			HttpServletRequest request,String paystatus, String begintime, String endtime,
			String workprocessnumber) {
		DoResult result = new DoResult();
		List<PayOrders> list = null;
		PageHelper.startPage(pageNum, pageSize);
		Core_User user = (Core_User) session.getAttribute("user");
		user=userService.getByUserName(user.getUserName());
		Long userid = user.getId();
		//list = ordersService.getOrderListCondiction(userid,paystatus,workprocessnumber,begintime,endtime,user);
		try {
			if (list.size() != 0) {
				// 选择路径
				String strDirPath = request.getSession().getServletContext()
						.getRealPath("/");
				String fileName = strDirPath + "\\Down\\excel\\";
				// 开始导出，往表插数据
			//	String now_date = com.jrwp.payMent.help.EXCELHelper.printExcel(fileName, list);
				String url = "http://" + request.getLocalAddr() + ":"
						+ request.getLocalPort() + "/payment/Down/excel/"
						+ now_date + ".xls";
				url = new String(url.getBytes("utf-8"), "utf-8");
				LogUtil.debug("导出完成:{}", fileName);
				result.setStateType(DoResultType.success);
				result.setStateValue(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/

    @Description("订单数据统计Json")
    @RequestMapping("orderCountJson")
    @ResponseBody
    public OrderCountHelper orderCountJson(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String paystatus,
                                           String begintime, String endtime) {
        OrderCountHelper orderCountHelper = null;
        List<OrderCount> list = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            Core_User user = (Core_User) session.getAttribute("user");
            user = userService.getByUserName(user.getUserName());
            list = ordersService.getOrderCount(user, paystatus, begintime, endtime);
            Integer totolcount = 0;
            BigDecimal totalMoney = new BigDecimal("0");
            for (OrderCount orderCount : list) {
                totolcount += orderCount.getBizCount();
                totalMoney = totalMoney.add(orderCount.getTotal());
            }
            OrderCount result = new OrderCount();
            result.setTotal(totalMoney);
            result.setBizCount(totolcount);
            result.setBizName("总计");
            list.add(result);
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<OrderCount> pageInfo = new PageInfo<OrderCount>(list);
            orderCountHelper = new OrderCountHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderCountHelper;
    }

//	@Description("数据统计打印")
//	@RequestMapping("sjtjprint")
//	@ResponseBody
//	public DoResult printsjtjModel(HttpSession session ,HttpServletRequest request,
//			   @RequestParam(value = "page", defaultValue = "1") int pageNum,
//			   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,String paystatus, String begintime, String
// endtime) {
//		DoResult result = new DoResult();
//		List<OrderCount> list = null;
//
//		Core_User user = (Core_User) session.getAttribute("user");
//		user=userService.getByUserName(user.getUserName());
//		list = ordersService.getOrderCount(user,paystatus,begintime,endtime);
//		try {
//			if (list.size() != 0) {
//				// 选择路径
//				String strDirPath = request.getSession().getServletContext()
//						.getRealPath("/");
//				String fileName = strDirPath + "\\Down\\excel\\";
//				// 开始导出，往表插数据
//				String now_date = com.jrwp.payMent.help.EXCELHelper.printsjtjExcel(fileName, list);
//				String url = "http://" + request.getLocalAddr() + ":"
//						+ request.getLocalPort() + "/payment/Down/excel/"
//						+ now_date + ".xls";
//				url = new String(url.getBytes("utf-8"), "utf-8");
//				LogUtil.debug("导出完成:{}", fileName);
//				result.setStateType(DoResultType.success);
//				result.setStateValue(url);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//	}
}
