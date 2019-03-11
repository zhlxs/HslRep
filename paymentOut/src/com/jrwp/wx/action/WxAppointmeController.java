package com.jrwp.wx.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.payMent.entity.DicDeptaddress;
import com.jrwp.payMent.service.DeptaddressService;
import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.service.WXSquenceInfoService;
import com.jrwp.wx.service.WxAppointmeService;

@Description("微信预约")
@Controller
@RequestMapping("phone/wxAppointmeController")
public class WxAppointmeController {

	private final Logger logger = Logger.getLogger(WxAppointmeController.class);

	@Resource
	private WxAppointmeService wxAppointmeService;
	@Resource
	private WXSquenceInfoService wXSquenceInfoService;
	@Resource
	private DeptaddressService deptaddressService;

	@Description("取消预约")
	@RequestMapping("cancelAppointme")
	@ResponseBody
	public DoResult cancelAppointme(HttpServletRequest request,
			HttpServletResponse response, Long appointmeId) {
		DoResult result = new DoResult();
		try {
			if (appointmeId != null && !"".equals(appointmeId)) {
				wxAppointmeService.cancelAppointme(appointmeId);
				// wxAppointmeService.insertAppointmeSync(appointmeId,0,0);
				result.setStateType(DoResultType.success);
				result.setStateMsg("取消预约成功");
			} else {
				result.setStateType(DoResultType.validFail);
				result.setStateMsg("参数有误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("取消预约失败");
			logger.error("在取消预约的时候发生异常：" + e);
		}
		return result;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param deptid
	 * @param businessType
	 * @param day
	 *            yyyy-MM-dd格式
	 * @return
	 */
	@Description("各时间段可预约人数")
	@RequestMapping("appointmeCount")
	@ResponseBody
	public String appointmeCount(HttpServletRequest request,
			HttpServletResponse response, Long deptid, Integer businessType,
			String day) {
		System.out.println("=====参数部门id:" + deptid);
		System.out.println("=====参数业务类型:" + businessType);
		System.out.println("=====参数日期:" + day);
		DoResult result = new DoResult();
		String str = null;
		try {
			// deptid = (long) 47;
			// businessType = 2;
			// day = "2018-11-12";
			if (!("").equals(day)) {
				day = day.replaceAll("/", "-");
			}
			// 获取配置的时间段模板
			DicDeptaddress deptaddress = deptaddressService
					.getObjectByDeptId(deptid);
			Long modelId = deptaddress.getTmodelId();
			if (businessType == 1) {
				modelId = 0l;
			} else if (businessType == 4) {
				modelId = 1l;
			}
			// if (modelId == null) {
			// modelId = 0l;
			// }
			// 各时间段所有可预约数
			List<OrderCount> orderCount = wxAppointmeService.getOrderCount(
					businessType, modelId);
			// 所选当天各时间段已经预约的人数
			List<OrderCount> appointmeCount = wxAppointmeService
					.getAppointmeCount(day, deptid, businessType);
			for (int i = 0; i < appointmeCount.size(); i++) {
				int appointmeOrderCount = appointmeCount.get(i).getOrderCount();// 该时间段已经预约的人数
				Long timequantum = appointmeCount.get(i).getId();// 时间段
				for (int x = 0; x < orderCount.size(); x++) {
					if (orderCount.get(x).getId().equals(timequantum)) {
						int count = orderCount.get(x).getOrderCount();
						orderCount.get(x).setOrderCount(
								count - appointmeOrderCount);// 可预约总数减已经预约数
					}
				}
			}
			result.setStateValue(orderCount);
			result.setStateType(DoResultType.success);
			result.setStateMsg("获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在获取各时间段可预约人数的时候发生异常：" + e);
		}
		str = JSON.toJSONString(result);
		// return JacksonUtil.toJson(result);
		return str;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param cardNumber
	 * @param name
	 * @param deptid
	 * @param appointmenttime
	 *            yyyy-mm-dd形式
	 * @param phone
	 * @param timequantum
	 * @param ser_code
	 *            一级code
	 * @return
	 */
	@Description("保存预约信息")
	@RequestMapping("saveAppointmeInfo")
	@ResponseBody
	public DoResult saveAppointmeInfo(HttpServletRequest request,
			HttpServletResponse response, String cardNumber, String name,
			Long deptid, String appointmenttime, String phone,
			Long timequantum, String ser_code, int businessType) {
		DoResult result = new DoResult();
		// String cardNumber = "360122199309154232";
		// String name = "熊琪";
		// Long deptid = (long) 47;
		// String appointmenttime = "2018-11-12";
		// String phone = "18770914039";
		// Long timequantum = (long) 47;
		// String ser_code = "00001";// 乱编的
		// int businessType = 2;
		try {
			/*
			 * SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); Date
			 * date = sf.parse(appointmenttime);
			 */
			AppointmeInfoJson oldAppointmeInfoJson = wxAppointmeService
					.getAppointmeByCardnumber(appointmenttime, cardNumber,
							deptid, businessType, timequantum);
			if (oldAppointmeInfoJson == null) {
				String openid = (String) request.getSession().getAttribute(
						"openId");
				// openid = "ceshideopenid";//
				// ///////////////////////////////////////////
				AppointmeInfoJson appointmeInfoJson = new AppointmeInfoJson();
				appointmeInfoJson.setCardNumber(cardNumber);
				appointmeInfoJson.setName(name);
				appointmeInfoJson.setDeptid(deptid);
				appointmeInfoJson.setOpenid(openid);
				appointmeInfoJson.setAppointmenttime(appointmenttime);
				appointmeInfoJson.setStatus(1);
				appointmeInfoJson.setPhone(phone);
				appointmeInfoJson.setTimequantum(timequantum);
				appointmeInfoJson.setIsexport(0);
				appointmeInfoJson.setSer_code(ser_code);
				appointmeInfoJson.setIsproxy(0);
				appointmeInfoJson.setBusinessType(businessType);
				wxAppointmeService.insertAppointmeInfo(appointmeInfoJson);
				result.setStateType(DoResultType.success);
				result.setStateMsg("预约成功");
			} else {
				result.setStateType(DoResultType.validFail);
				result.setStateMsg("已经有预约信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存预约失败");
			logger.error("在保存预约的时候发生异常：", e);
		}
		return result;
	}

	/*
	 * @Description("微信提醒")
	 * 
	 * @RequestMapping("wxremind")
	 * 
	 * @ResponseBody public void wxremind() { try { List<CallSquenceJson>
	 * callSquenceJson = wxAppointmeService.getCallSquenceJson();
	 * if(callSquenceJson != null){ for(int i=0;i<callSquenceJson.size();i++){
	 * int isappoint = callSquenceJson.get(i).getIsappointment(); int iscall =
	 * callSquenceJson.get(i).getIscall(); if(isappoint == 1){//是预约的 if(iscall
	 * == 0){//还没有提醒 //公众号推送一条消息 // access_token问题 // String token =
	 * OpenidUtil.getToken(request.getSession(), // accessTokenDao); String
	 * groupUrl
	 * ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" +
	 * token;
	 * 
	 * String content = ""; String openid1data = "{\"touser\":\"" +
	 * callSquenceJson.get(i).getOpenid() +
	 * "\",\"msgtype\": \"text\",\"text\": {\"content\": " + "\"" + content +
	 * "\"}}"; String sr2 = OpenidUtil.sendPost(groupUrl, openid1data);
	 * wxAppointmeService.updateCallStatue(callSquenceJson.get(i).getId()); } }
	 * } } } catch (Exception e) { e.printStackTrace();
	 * logger.error("在取消预约的时候发生异常："+e); } }
	 */

}
