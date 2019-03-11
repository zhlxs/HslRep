package com.jrwp.webservice.enter;

import javax.jws.WebService;

@WebService(targetNamespace = "webservice.jrep.com")
public interface WSService {
	/**
	 * 入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param params
	 *            执行指定方法所需参数的json字符串
	 * @return
	 */
	public String enterWS(String methodName, String params);
}
