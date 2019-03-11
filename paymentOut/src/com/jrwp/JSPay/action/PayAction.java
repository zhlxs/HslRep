package com.jrwp.JSPay.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.JSPay.until.AmountUtils;
import com.jrwp.JSPay.until.WXPayUtil;
import com.jrwp.core.utils.HttpRequestUntil;
import com.jrwp.follow.Until.ComUntil;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.follow.service.SaveSyncService;

@Controller
@RequestMapping("wx/jsPay")
public class PayAction {

	@Resource
	private SaveSyncService saveSyncService;

	@RequestMapping("test")
	public ModelAndView test(QrCodeParms qrCodeParms) {
		ModelAndView modelAndView = new ModelAndView("jsPay/pay");
		modelAndView.addObject("parms", qrCodeParms);
		return modelAndView;

	}

	@RequestMapping(value = "orders", method = RequestMethod.GET)
	@ResponseBody
	public Map orders(HttpServletRequest request) {
		try {
			System.out.println("进入wx   orders==============");
			QrCodeParms parms = (QrCodeParms) request.getSession()
					.getAttribute("QrParms");
			PayConfig config = (PayConfig) request.getSession().getAttribute(
					"payconfig");
			System.out.println("========================");
			boolean iapay = saveSyncService.ispay(parms.getId());
			// 拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			if (iapay) {
				System.out.println("已经支付了===========");
				paraMap.put("ispay", "1");
				return paraMap;
			}
			String order = saveSyncService.saveSync(parms, config);
			System.out.println(order);
			// 获取请求ip地址
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip.indexOf(",") != -1) {
				String[] ips = ip.split(",");
				ip = ips[0].trim();
			}
			String paykey = "";
			if (parms.getPayType() == 4) {
				paykey = config.getPaykey();
				System.out.println("农行支付==============");
				// paraMap.put("appid", config.getAppid());
				paraMap.put("mch_id", config.getPaymentpartnerid());//
				// paraMap.put("sub_mch_id", config.getPaymentpartnerid());
				paraMap.put("body", parms.getOrderDescribe());//
				paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//
				paraMap.put("sub_openid", parms.getSub_openid());
				paraMap.put("out_trade_no", order);// 订单号//
				paraMap.put("spbill_create_ip", ip);//
				paraMap.put("total_fee",
						AmountUtils.changeY2F(parms.getPayMoney() + ""));//
				paraMap.put("trade_type", "WXPAY.JSAPI");//
				paraMap.put("notify_url", "http://");// 此路径是微信服务器调用支付结果通知路径随意写
				String sign = WXPayUtil.generateSignature(paraMap, paykey);
				paraMap.put("sign", sign);
				String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式
				System.out.println(xml);
				String unifiedorder_url = "http://betaapi.speedpos.snsshop.net/unifiedorder";
				String xmlStr = HttpRequestUntil
						.sendPost(unifiedorder_url, xml);// 发送post请求"统一下单接口"返回预支付id:prepay_id
				System.out.println(xmlStr);
				String paymapStr = "";// 预支付id
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
				if (map.get("retcode").equals("0")) {
					System.out.println("SUCCESS");
					paymapStr = (String) map.get("getBrandWCPayRequest");
					System.out.println(paymapStr);
					Map<String, Object> payMap = WXPayUtil.json2Map(paymapStr);
					System.out.println(payMap.get("appId"));
					return payMap;
				}

			} else {
				if (config.getIsfacilitator() == 1) {
					System.out.println("服务商==============");
					paykey = ComUntil.KTH_PAYKEY;
					paraMap.put("appid", ComUntil.KTH_APPID);
					paraMap.put("mch_id", ComUntil.KTH_MCH_ID);
					paraMap.put("sub_mch_id", config.getPaymentpartnerid());
					paraMap.put("body", parms.getOrderDescribe());
					paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
					paraMap.put("openid", parms.getOpenid());
					paraMap.put("out_trade_no", order);// 订单号
					paraMap.put("spbill_create_ip", ip);
					paraMap.put("total_fee",
							AmountUtils.changeY2F(parms.getPayMoney() + ""));
					paraMap.put("trade_type", "JSAPI");
					paraMap.put("notify_url", "http://");// 此路径是微信服务器调用支付结果通知路径随意写
				} else {
					paykey = config.getPaykey();
					System.out.println("非服务商==============");
					paraMap.put("appid", config.getAppid());
					paraMap.put("mch_id", config.getPaymentpartnerid());
					// paraMap.put("sub_mch_id", config.getPaymentpartnerid());
					paraMap.put("body", parms.getOrderDescribe());
					paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
					paraMap.put("openid", parms.getSub_openid());
					paraMap.put("out_trade_no", order);// 订单号
					paraMap.put("spbill_create_ip", ip);
					paraMap.put("total_fee",
							AmountUtils.changeY2F(parms.getPayMoney() + ""));
					paraMap.put("trade_type", "JSAPI");
					paraMap.put("notify_url", "http://");// 此路径是微信服务器调用支付结果通知路径随意写
				}
				System.out.println("paraMap================"
						+ WXPayUtil.mapToXml(paraMap));
				String sign = WXPayUtil.generateSignature(paraMap, paykey);
				paraMap.put("sign", sign);
				String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式
				System.out.println(xml);
				// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
				// https://api.mch.weixin.qq.com/pay/unifiedorder
				String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String xmlStr = HttpRequestUntil
						.sendPost(unifiedorder_url, xml);// 发送post请求"统一下单接口"返回预支付id:prepay_id
				System.out.println(xmlStr);
				// 以下内容是返回前端页面的json数据
				String prepay_id = "";// 预支付id
				if (xmlStr.indexOf("SUCCESS") != -1) {
					System.out.println("SUCCESS");
					if (xmlStr.indexOf("该订单已支付") != -1) {
						System.out.println("已经支付了===========");
						paraMap.put("ispay", "1");
						return paraMap;
					}
					Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
					prepay_id = (String) map.get("prepay_id");

				}
				Map<String, String> payMap = new HashMap<String, String>();
				if (config.getIsfacilitator() == 1) {
					payMap.put("appId", ComUntil.KTH_APPID);
				} else {
					payMap.put("appId", config.getAppid());
				}
				payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
				payMap.put("nonceStr", WXPayUtil.generateNonceStr());
				payMap.put("signType", "MD5");
				payMap.put("package", "prepay_id=" + prepay_id);
				paraMap.put("total_fee",
						AmountUtils.changeY2F(parms.getPayMoney() + ""));
				String paySign = WXPayUtil.generateSignature(payMap, paykey);
				payMap.put("ispay", "0");
				payMap.put("paySign", paySign);
				return payMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
