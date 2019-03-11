package com.jrwp.webservice.help.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C10;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C10;
import com.jrwp.webservice.help.IWebServiceDistry;
import com.jrwp.webservice.help.TmriOutAccessJSZApi;
import com.jrwp.webservice.utils.JsonUtil;

/**
 * 接口名称字典类，需同步修改Constant类中的接口名称定义
 * 
 * @author: ShenHaijie
 * @date: 2018年10月16日
 */
@Component(value = "webServiceDistry")
public class WebServiceDistry implements IWebServiceDistry {
	@Resource
	private TmriOutAccessJSZApi tmriOutAccessJSZApi;


	@Override
	public String getDriverPhoto02C05(Request25C10 request25C01) {
		//return JsonUtil.toJSON(tmriOutAccessJSZApi.getDriverPhoto((Response25C01) response25C01));
		return "";
	}

}
