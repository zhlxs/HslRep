package com.jrwp.wx.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.Until.ComUntil;
import com.jrwp.wx.until.HttpGetUtil;
import com.jrwp.wx.until.OpenidUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("wxAction")
public class WeChatAction {

    // 获取code
    @RequestMapping("getCode")
    public String getCode(HttpServletRequest request, String redirect_uri) {
        // 这里要将你的授权回调地址处理一下，否则微信识别不了
        StringBuffer url = new StringBuffer("");
        System.out.println(redirect_uri);
        try {
            // System.out.println("deptid=" + deptid);
            // int deptid = (int) request.getSession().getAttribute("deptid");
            String appid = (String) request.getSession().getAttribute("appid");
            String secret = (String) request.getSession().getAttribute("secret");
            System.out.println("进入getCode方法1");
            redirect_uri = "http://" + ComUntil.COM + "/paymentOut/" + redirect_uri;
            redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
            System.out.println(redirect_uri);
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid=" +
                    appid + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
            System.out.println(url.toString());
            // modelAndView.setViewName(url.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:" + url;
    }

    /**
     *
     * @param request
     * @param appid 要获取openid 的appid
     * @param type  snsapi_base和snsapi_userinfo  获取用户信息还是只是openid
     * @param redirect_uri 获取之后回调URL
     * @param secret app秘钥
     * @return
     */
    @RequestMapping("getWxCode")
    public String getWxCode(HttpServletRequest request, String appid, String type, String redirect_uri, String secret) {
        //snsapi_base和snsapi_userinfo
        // 这里要将你的授权回调地址处理一下，否则微信识别不了
        request.getSession().setAttribute("appid", appid);
        request.getSession().setAttribute("secret", secret);
        request.getSession().setAttribute("redirect_uri", redirect_uri);
        StringBuffer url = new StringBuffer("");
        System.out.println(redirect_uri);
        try {
            System.out.println("getWxCode=================");
            System.out.println("appid========"+appid);
            String wxredirect_uri = "http://" + ComUntil.COM + "/paymentOut/wxAction/getWxInfo";
            wxredirect_uri = URLEncoder.encode(wxredirect_uri, "UTF-8");
            url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + wxredirect_uri + "&appid="
                    + appid + "&response_type=code&scope=" + type + "&state=1#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:" + url;
    }

    @RequestMapping("getWxInfo")
    public ModelAndView getinfo(String code, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            System.out.println("微信开始回调=====");
            String url = (String) request.getSession().getAttribute("redirect_uri");
            String appid = (String) request.getSession().getAttribute("appid");
            String secret = (String) request.getSession().getAttribute("secret");
            Map params = new HashMap();
            params.put("appid", appid);
            params.put("secret", secret);
            params.put("grant_type", "authorization_code");
            params.put("code", code);
            System.out.println("code====" + code);
            String msg = HttpGetUtil.sendPost("https://api.weixin.qq.com/sns/oauth2/access_token", params);
            System.out.println("msg==============:" + msg);
            System.out.println("回调："+url);
            modelAndView.setViewName("redirect:" + url);
            modelAndView.addObject("info",msg);
        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }
        return modelAndView;
        //return null;
        //return JacksonUtil.toJson(result);
    }

}
