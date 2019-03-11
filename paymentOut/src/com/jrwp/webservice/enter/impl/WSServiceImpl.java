package com.jrwp.webservice.enter.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.jrwp.core.service.ILogService;
import com.jrwp.webservice.enter.WSService;
import com.jrwp.webservice.help.IWebServiceDistry;
import com.jrwp.webservice.help.WSResult;
import com.jrwp.webservice.utils.IPUtil;
import com.jrwp.webservice.utils.JsonUtil;
import com.jrwp.webservice.utils.WSLogUtil;

/**
 * webServcie统一入口
 * 
 * @author: ShenHaijie
 * @date: 2018年10月16日
 */
@Component
public class WSServiceImpl implements WSService {
	@Autowired
	private IWebServiceDistry webServiceDistry;

	@Autowired
	private ILogService logService;

	@Override
	public String enterWS(String methodName, String params) {
		WSLogUtil.info("调用接口--> 方法名：{}；参数：{}", methodName, params);
		String result = null;
		try {
			// 获取调用方的IP地址
			/*String ip = getRequestIP();
			// List<WS_IP_ALLOW_WS> ipList = wsIpAllowWSService.findByIP(ip);
			// ip="10.179.145.230";
			List<ZZJ_ZCXX_WS> ipList = wsIpAllowWSService.findzcxxByIP(ip);
			if (ipList.size() == 0) {
				WSLogUtil.info("该IP不允许访问");
				// 设置没有权限时的响应数据
				Core_Log log = new Core_Log();
				Core_User user = new Core_User();
				user.setId("30DC30120BCC457BAE0584099DE8AE61");
				log.setUser(user);
				log.setAreaName("Webservice");
				log.setControlName("enter");
				log.setControlDisplay("非授权终端");
				log.setRecordIP(ip);
				log.setRecordTime(new Date());
				log.setActionName("enter-wsfail");
				log.setParameterJson(AESEncodeUtil.encode(params));
				log.setActionDisplay("自助终端[" + ip + "]没有访问接口的权限");
				logService.save(log);
				return JsonUtil.toJSON(new WSResult<Object>().fail().setStateMsg("接口[" + methodName + "]调用失败，没有权限"));
			}

			// 判断参数值是否为空
			if (params == null || params.length() == 0) {
				WSLogUtil.info("参数不能为空");
				// 设置参数为空时的响应数据
				return JsonUtil.toJSON(new WSResult<Object>().fail().setStateMsg("接口[" + methodName + "]调用失败，参数不能为空"));
			}*/

			// 通过反射获取指定方法
			Class<? extends Object> clazz = webServiceDistry.getClass();
			Method method = clazz.getDeclaredMethod(methodName, JSON.class);
			// 解析参数
			Object jsonObject = JSON.parse(params);
			// 通过反射执行指定方法
			result = String.valueOf(method.invoke(webServiceDistry, jsonObject));
		} catch (NoSuchMethodException e) {
			WSLogUtil.error("接口[" + methodName + "]调用失败，非法方法名:{}", e);
			// 设置没用指定方法时的响应数据
			result = JsonUtil.toJSON(new WSResult<Object>().fail().setStateMsg("接口[" + methodName + "]调用失败，非法方法名"));
		} catch (JSONException e) {
			WSLogUtil.error("接口[" + methodName + "]调用失败，参数解析失败:{}", e);
			// 设置所传参数不是json字符串时的响应数据
			result = JsonUtil.toJSON(new WSResult<Object>().fail().setStateMsg("接口[" + methodName + "]调用失败，参数解析失败"));
		} catch (Exception e) {
			WSLogUtil.error("接口[" + methodName + "]调用失败，系统错误:{}", e);
			// 设置异常时的响应数据
			result = JsonUtil.toJSON(new WSResult<Object>().fail().setStateMsg("接口[" + methodName + "]调用失败，系统错误"));
		} finally {
			WSLogUtil.info("调用接口【" + methodName + "]结束");
		}

		return result;
	}

	/**
	 * 获取调用方IP
	 */
	public static String getRequestIP() {
		MessageContext ctx = new WebServiceContextImpl().getMessageContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
		String remoteAddr = IPUtil.getRemoteAddr(request);
		WSLogUtil.info("调用方IP：{}", remoteAddr);
		return remoteAddr;
	}
}
