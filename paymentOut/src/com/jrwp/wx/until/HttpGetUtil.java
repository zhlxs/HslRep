package com.jrwp.wx.until;

import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpGetUtil {

    public static String sendPost(String url, Map<String, String> params) {
        String returnstr = null;
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            String parameters = "";
            boolean hasParams = false;
            for (String key : params.keySet()) {
                String value = params.get(key);
                parameters += key + "=" + value + "&";
                hasParams = true;
            }
            if (hasParams) {
                parameters = parameters.substring(0, parameters.length() - 1);
            }
            url += "?" + parameters;
            URL sendurl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) sendurl.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod("GET");
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while (( str = bufferedReader.readLine() ) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            //jsonObject = JSONObject.fromObject(buffer.toString());
            System.out.println("================从微信获取到的信息为“" + buffer.toString());
            returnstr = buffer.toString();
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        return returnstr;
    }
}
