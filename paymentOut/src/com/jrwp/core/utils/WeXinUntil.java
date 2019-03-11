package com.jrwp.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jrwp.core.entity.Msg;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("wx")
public class WeXinUntil {

	private static final String appId = "wx1dad8021bfbd6215";
	private static final String appSecret = "00694c3033df389f998d71afe752a677";
	private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private static final String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	private static String accessToken;
	private static long getAccessTokenTime;

	public WeXinUntil() {
	}

	/**
	 * 获取token
	 * 
	 * @param id
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getToken(String appid, String secret) {
		if (accessToken != null) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - getAccessTokenTime < 0x36ee80L)
				return accessToken;
		}
		String url = (new StringBuilder(tokenUrl + "&appid=")).append(appid)
				.append("&secret=").append(secret).toString();
		String sr = sendPost(url, "");
		System.out.println((new StringBuilder("获取access_token返回的结果:")).append(
				sr).toString());
		JSONObject jsonObject = JSON.parseObject(sr);
		String accessToken1 = (String) jsonObject.get("access_token");
		System.out.println("获取到的access_token:" + accessToken1);
		if (accessToken1 != null) {
			getAccessTokenTime = System.currentTimeMillis();
			accessToken = accessToken1;
		}
		return accessToken;
	}

	/**
	 * 消息推送
	 * 
	 * @param token
	 * @param id
	 * @param machinename
	 * @param message
	 * @param openid
	 * @return
	 */
	public static String sendMsg(String token, String openid,
			String businessname,int peoplenum) {
		if (businessname == null) {
			businessname = "交警";
		}
		Msg msg = new Msg();
		msg.setTouser(openid);
		msg.setMsgtype("text");
		msg.getText().setContent(
				(new StringBuilder("您好,您预约的" + businessname
						+ "业务排队队列前面还剩"+peoplenum+"人,请作好准备.")).toString());
		System.out.println("推送的消息:" + msg.getText().getContent());
		String msgstr = JacksonUtil.toJson(msg);
		System.out.println("推送的消息json:" + msgstr);
		String str = sendPost((new StringBuilder(sendMsgUrl)).append(token)
				.toString(), msgstr);
		return str;
	}

	/**
	 * Post请求发送方式
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, String param) {
		OutputStreamWriter out;
		BufferedReader in;
		String result;
		out = null;
		in = null;
		result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(param);
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null)
				result = (new StringBuilder(String.valueOf(result))).append(
						line).toString();
		} catch (Exception e) {
			System.out.println((new StringBuilder("发送 POST 请求出现异常！")).append(e)
					.toString());
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 交互接口
	 * 
	 * @param businessname
	 * @param appid
	 * @param secret
	 * @param openid
	 * @return
	 */
	//@RequestMapping("publish")
	//@ResponseBody
	public int post(String businessname, String appid, String secret,
			String openid,int num) {
		String token = getToken(appid, secret);
		String str = sendMsg(token, openid, businessname,num);
		System.out.println("微信返回的数据:" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		String errcode = jsonObject.getString("errcode");
		System.out.println("微信返回的提示代码:" + errcode);
		int zt;
		if (errcode.equals("0"))
			zt = 1;
		else
			zt = 0;
		System.out.println("访问状态:" + zt);
		return zt;
	}

	public static void main(String args[]) {
		String token = getToken(appId, appSecret);
		String str = sendMsg(token, "oJJFXs_By9h93a4qfG2HebGUngsL", null,3);
		System.out.println(str);
		JSONObject jsonObject = JSON.parseObject(str);
		String errcode = jsonObject.getString("errcode");
		int zt;
		if (errcode.equals("0"))
			zt = 1;
		else
			zt = 0;
		System.out.println("访问状态:" + zt);
	}
}