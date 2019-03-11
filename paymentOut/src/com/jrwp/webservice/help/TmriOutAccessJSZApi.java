package com.jrwp.webservice.help;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jrwp.webservice.entity.tmrioutaccess.QueryHead;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHead;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C10;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C11;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C12;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C10;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C11;


/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public interface TmriOutAccessJSZApi {

	/**
	 * 读取驾驶人照片信息
	 * 
	 * @return
	 */
	List<Response25C10> getDriverPhoto(Request25C10 request25C01);

	WriteHead writeApprInfo(Request25C12 request25C12);
	Response25C11 writeBCInfo(Request25C11 request25C11);
}
