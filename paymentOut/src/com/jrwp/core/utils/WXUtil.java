package com.jrwp.core.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.jrwp.wx.until.MyX509TrustManager;

public class WXUtil {
	public static String sendPost(String url, String params) {
		String returnstr = null;
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL sendurl = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) sendurl
					.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("GET");
			/*
			 * // 当outputStr不为null时向输出流写数据 if (null != outputStr) { OutputStream
			 * outputStream = conn.getOutputStream(); // 注意编码格式
			 * outputStream.write(outputStr.getBytes("UTF-8"));
			 * outputStream.close(); }
			 */
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			// jsonObject = JSONObject.fromObject(buffer.toString());
			System.out.println("c================从微信获取到的信息为“"
					+ buffer.toString());
			returnstr = buffer.toString();
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return returnstr;
	}
}
