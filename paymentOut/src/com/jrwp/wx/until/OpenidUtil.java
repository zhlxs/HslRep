package com.jrwp.wx.until;

import com.jrwp.follow.Until.TimeUntil;
import com.jrwp.follow.dao.AccessTokenDao;
import com.jrwp.follow.entity.AccessToken;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OpenidUtil {

    private static final String appId = "wxee10f55b4da5a870";
    private static final String appSecret = "14a79475156194dbc693f074a6dbd03f";

    /**
     * 获取openid
     *
     * @param code
     * @param session
     * @return
     */
    public static String getOpenid(String code, HttpSession session) {
        Map params = new HashMap();
        params.put("appid", session.getAttribute("appid"));
        params.put("secret", session.getAttribute("secret"));
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        System.out.println("code====" + code);
        String msg = HttpGetUtil.sendPost("https://api.weixin.qq.com/sns/oauth2/access_token", params);
        JSONObject jsonObject = JSONObject.fromObject(msg);
        String openid = jsonObject.get("openid").toString();
        return openid;
    }

    /**
     * 拉取用户信息 UnionID 机制
     *
     * @param code
     * @param session
     * @return
     */
    public static JSONObject getUnionInfo(String code, HttpSession session, AccessTokenDao accessTokenDao) {
        Map params = new HashMap();
        String appid = (String) session.getAttribute("appid");
        String secret = (String) session.getAttribute("secret");
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        System.out.println("code====" + code);
        String msg = HttpGetUtil.sendPost("https://api.weixin.qq.com/sns/oauth2/access_token", params);
        JSONObject jsonObject = JSONObject.fromObject(msg);
        String openid = jsonObject.get("openid").toString();
        //查询token

        Map params1 = new HashMap();
        params1.put("openid", openid);
        params1.put("access_token",getToken(accessTokenDao));
        params1.put("lang", "zh_CN");
        //https://api.weixin.qq.com/cgi-bin/user/info  还可以获取是否关注公众号
        //https://api.weixin.qq.com/sns/userinfo  单纯获取用户信息
        //根据openid 和token  拉取用户信息
        String msg1 = HttpGetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/user/info", params1);
        JSONObject userinfo = JSONObject.fromObject(msg1);
        return userinfo;
    }

    public static String getToken(AccessTokenDao accessTokenDao) {
        AccessToken accessToken = accessTokenDao.getToken(appId);
        //String appid = (String) session.getAttribute("appid");
        //String secret = (String) session.getAttribute("secret");
        String access_token = "";
        //如果是空  获取并保存
        if (accessToken == null) {
            accessToken = new AccessToken();
            Map params = new HashMap();
            params.put("appid", appId);
            params.put("secret", appSecret);
            params.put("grant_type", "client_credential");
            String msg = HttpGetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/token", params);
            JSONObject jsonObject = JSONObject.fromObject(msg);
            access_token = jsonObject.get("access_token").toString();
            //String access_token = OpenidUtil.getToken(session);
            accessToken.setAccesstoken(access_token);
            accessToken.setAppid(appId);
            accessToken.setSecret(appSecret);
            accessToken.setUpdatetime(new Date());
            accessTokenDao.save(accessToken);
        } else {
            Date tokeDate = accessToken.getUpdatetime();
            Date nowDate = new Date();
            long min = TimeUntil.getMin(nowDate, tokeDate);
            if (min > 110) {
                Map params = new HashMap();
                params.put("appid", appId);
                params.put("secret", appSecret);
                params.put("grant_type", "client_credential");
                String msg = HttpGetUtil.sendPost("https://api.weixin.qq.com/cgi-bin/token", params);
                JSONObject jsonObject = JSONObject.fromObject(msg);
                access_token = jsonObject.get("access_token").toString();
                accessToken.setAccesstoken(access_token);
                accessTokenDao.update(accessToken);
            }else{
                access_token=accessToken.getAccesstoken();
            }
        }
        return access_token;
    }

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
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while (( line = in.readLine() ) != null) result = ( new StringBuilder(String.valueOf(result)) ).append(line).toString();
        } catch (Exception e) {
            System.out.println(( new StringBuilder("发送 POST 请求出现异常！") ).append(e).toString());
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
     *
     * 进内网的POST
     * @param url
     * @param param
     * @return
     */
    public static String sendPostIn(String url, String param) {
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
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while (( line = in.readLine() ) != null) result = ( new StringBuilder(String.valueOf(result)) ).append(line).toString();
        } catch (Exception e) {
            System.out.println(( new StringBuilder("发送 POST 请求出现异常！") ).append(e).toString());
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
}
