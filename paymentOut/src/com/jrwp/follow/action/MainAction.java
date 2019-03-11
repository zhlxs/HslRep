package com.jrwp.follow.action;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.Until.ComUntil;
import com.jrwp.follow.dao.AccessTokenDao;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.dao.UserPayParmsDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.follow.service.SaveSyncService;
import com.jrwp.wx.until.OpenidUtil;

@Controller
@RequestMapping("wx/follow")
public class MainAction {

    @Resource
    private IDeptService deptService;

    @Resource
    private DeptPayConfigDao deptPayConfigDao;

    @Resource
    private UserPayParmsDao payParmsDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private SaveSyncService saveSyncService;

    /**
     * 扫码动作 根据参数不同做出不同响应
     *
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request, QrCodeParms parms) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("进入index=====================");
        try {
            System.out.println(JacksonUtil.toJson(parms));
            request.getSession().setAttribute("QrParms", parms);
            String userAgent = request.getHeader("user-agent");
            Integer paytype = parms.getPayType();
            Integer configtype = paytype;
            if (paytype == 4) {
                configtype = 5;
            }
            PayConfig config = deptPayConfigDao.getdeptConfig(parms.getDeptCode(), configtype - 1);
            System.out.println("confg+++++++++" + JacksonUtil.toJson(parms));
            System.out.println(JacksonUtil.toJson(parms));
            request.getSession().setAttribute("payconfig", config);
            if (paytype != null && paytype == 1) {// 支付宝
                if (userAgent != null && userAgent.contains("AlipayClient")) {// 支付宝浏览器
                    modelAndView.setViewName("forward:/wx/follow/payAndAppr/");
                } else {
                    return new ModelAndView("follow/wrongUC", "msg", "请打开支付宝扫码！");
                }
            } else if (paytype != null && ( paytype == 2 || paytype == 4 )) {// 微信
                if (userAgent != null && userAgent.contains("MicroMessenger")) {// 来自微信浏览器
                    // 微信先判断是否需要强制关注
                    // PayConfig config =
                    // deptPayConfigDao.getdeptConfig(parms.getDeptCode(),
                    // paytype - 1);
                    // 先去获取科泰华的OPenid
                    System.out.println("微信支付==============");
                    if (config.getIsfacilitator() == 1) {
                        System.out.println("服务商模式=====================");
                        String redirect_uri = "http://" + ComUntil.COM + "/paymentOut/wx/follow/isFollow";
                        System.out.println(redirect_uri);
                        // redirect_uri = URLEncoder.encode(redirect_uri,
                        // "UTF-8");
                        modelAndView.addObject("redirect_uri", redirect_uri);
                        modelAndView.addObject("appid", ComUntil.KTH_APPID);
                        modelAndView.addObject("secret", ComUntil.KTH_SECRET);
                        modelAndView.addObject("type", "snsapi_base");
                        modelAndView.setViewName("redirect:" + "http://" + ComUntil.COM + "/paymentOut/wxAction/getWxCode");

                    } else {
                        System.out.println("非服务商模式=====================");
                        String appid = config.getAppid();
                        String secret = config.getAppsecret();
                        if (config.getIssubscibe() == 1) {// 订阅 区分支付appid
                            // 和关注appid
                            appid = config.getTargetappid();
                            secret = config.getTargetappsecret();
                        }
                        request.getSession().removeAttribute("appid");
                        request.getSession().removeAttribute("secret");
                        request.getSession().setAttribute("appid", appid);
                        request.getSession().setAttribute("secret", secret);
                        String redirect_uri = "http://" + ComUntil.COM + "/paymentOut/wx/follow/getQrCode";
                        redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
                        System.out.println(redirect_uri);
                        // 简单获取openid的话参数response_type与scope与state参数固定写死即可
                        StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" +
                                redirect_uri + "&appid=" + appid +
                                "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
                        return new ModelAndView("redirect:" + url.toString());
                    }
                } else {
                    return new ModelAndView("follow/wrongUC", "msg", "请打开微信扫码！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }

        return modelAndView;
    }

    /**
     * 服务商回调地址 获取科泰华openid
     *
     * @param request
     * @return
     */
    @RequestMapping("isFollow")
    public ModelAndView isFollow(HttpServletRequest request, String info) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("进入服务商isFollow=====================");
        try {
            // info = request.getParameter("info");
            System.out.println("info========" + info);
            PayConfig config = (PayConfig) request.getSession().getAttribute("payconfig");
            QrCodeParms parms = (QrCodeParms) request.getSession().getAttribute("QrParms");
            if (info != null && !info.equals("")) {
                JSONObject jsonObject = JSONObject.fromObject(info);
                String openid = jsonObject.get("openid").toString();
                System.out.println("openid====" + openid);
                parms.setOpenid(openid);
                request.getSession().removeAttribute("QrParms");
                request.getSession().setAttribute("QrParms", parms);
            }
            if (config.getIsfollow() == 1) {
                System.out.println("需要强制关注==============");
                // 需要强制关注，跳转关注页面 获取code 重定向到 二维码页面
                String appid = config.getAppid();
                String secret = config.getAppsecret();
                if (config.getIssubscibe() == 1) {// 订阅 区分支付appid 和关注appid
                    appid = config.getTargetappid();
                    secret = config.getTargetappsecret();
                }
                request.getSession().removeAttribute("appid");
                request.getSession().removeAttribute("secret");
                request.getSession().setAttribute("appid", appid);
                request.getSession().setAttribute("secret", secret);

                String redirect_uri = "http://" + ComUntil.COM + "/paymentOut/wx/follow/getQrCode";
                redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
                System.out.println(redirect_uri);
                // 简单获取openid的话参数response_type与scope与state参数固定写死即可
                StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" +
                        redirect_uri + "&appid=" + appid + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
                return new ModelAndView("redirect:" + url.toString());
            } else {
                System.out.println("不需要强制关注==============");
                modelAndView.setViewName("redirect:/wx/follow/payAndAppr/");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }
        return modelAndView;
    }

    /*
     * 需要强制关注 获取用户信息
     */
    @RequestMapping("getQrCode")
    public ModelAndView getQrCode(HttpServletRequest request, String code) {
        System.out.println("微信回调，进入getQrCode==============");
        ModelAndView modelAndView = new ModelAndView();
        try {
            System.out.println(request.getSession().getAttribute("appid"));
            String openid = "";
            String subscribe = "";
            PayConfig config = (PayConfig) request.getSession().getAttribute("payconfig");
            QrCodeParms parms = (QrCodeParms) request.getSession().getAttribute("QrParms");
            if (config.getIsfollow() == 1) {
                JSONObject userinfo = OpenidUtil.getUnionInfo(code, request.getSession(), accessTokenDao);
                System.out.println("userinfo============" + userinfo.toString());
                subscribe = userinfo.get("subscribe").toString();
                openid = userinfo.get("openid").toString();
            } else {
                openid = OpenidUtil.getOpenid(code, request.getSession());
            }
            System.out.println("setSub_openid====" + openid);
            parms.setSub_openid(openid);
            request.getSession().removeAttribute("QrParms");
            request.getSession().setAttribute("QrParms", parms);
            if (config.getIsfollow() == 1) {
                // payParmsDao.save(parms);
                if (subscribe.equals("0")) {
                    System.out.println("没有关注==============");
                    String paycode = parms.getDeptCode();
                    // PayConfig config =
                    // deptPayConfigDao.getdeptConfig(paycode, 1);
                    // 主页方式
                    modelAndView.setViewName("redirect:" + config.getMainUrl());
                } else {
                    System.out.println("已经关注了 ==============");
                    // 已关注 进行支付评价
                    modelAndView.setViewName("redirect:/wx/follow/payAndAppr/");
                }
            } else {
                modelAndView.setViewName("redirect:/wx/follow/payAndAppr/");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }
        return modelAndView;
    }

    /**
     * 支付、评价 s
     *
     * @return
     */
    @RequestMapping("payAndAppr")
    public ModelAndView payAndAppr(HttpServletRequest request, Boolean ispay) {
        System.out.println("进入payAndAppr");
        ModelAndView modelAndView = new ModelAndView();
        try {
            QrCodeParms parms = (QrCodeParms) request.getSession().getAttribute("QrParms");
            modelAndView.addObject("parms", parms);
            System.out.println(JacksonUtil.toJson(parms));
            // PayConfig config = (PayConfig)
            // request.getSession().getAttribute("payconfig");
            Integer paytype = parms.getPayType();
            Integer apprseq = parms.getApprseq();
            if (paytype != null && ( paytype == 2 || paytype == 4 )) {// 微信
                System.out.println("微信支付");
                if (apprseq == null || apprseq == 1) {
                    // 先支付
                    modelAndView.setViewName("jsPay/pay");
                    return modelAndView;
                } else {
                    // 先评价
                    if (ispay != null && ispay == true) {
                        modelAndView.setViewName("jsPay/pay");
                        return modelAndView;
                    }
                    modelAndView.setViewName("forward:/wx/appraiseDetailAction/apprList");
                }
            } else {
                System.out.println("支付宝支付");
                boolean iapay = saveSyncService.ispay(parms.getId());
                if (iapay) {
                    modelAndView.addObject("ispay", 1);
                } else {
                    modelAndView.addObject("ispay", 0);
                }
                // 支付宝
                modelAndView.setViewName("jsPay/aliPay");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }
        return modelAndView;
    }
}
