package com.jrwp.core.utils;

import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.client.ClientResult;


public class HttpClient {
	public static String post(String json, String httpUrl, String requestUrl) {
		try {
			Client client = new Client(httpUrl);
			ClientRequest makecardRequest = new ClientRequest(requestUrl);
			makecardRequest.setConnectTimeOut(1);
			makecardRequest.setReadTimeOut(1);
			makecardRequest.setJsonParam(json);
			ClientResult uploadResult = client.build(makecardRequest).post();
			if (uploadResult.getStatus().getCode() == 200) {
				return uploadResult.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
