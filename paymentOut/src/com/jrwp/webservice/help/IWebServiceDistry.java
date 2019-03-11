package com.jrwp.webservice.help;

import com.alibaba.fastjson.JSON;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C10;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C10;

/**
 * 接口名称字典类，需同步修改Constant类中的接口名称定义
 * 
 * @author: ShenHaijie
 * @date: 2018年10月14日
 */
public interface IWebServiceDistry {
	
	/**
	 * 读取驾驶人照片信息
	 * 
	 * @param paramsJson
	 *            参数的json对象
	 * @return
	 */
	String getDriverPhoto02C05(Request25C10 request25C01);

}
