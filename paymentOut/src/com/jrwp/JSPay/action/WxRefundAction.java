package com.jrwp.JSPay.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.RefundDetail;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jrwp.JSPay.entity.AlipayConfig;
import com.jrwp.JSPay.entity.RefundDetails;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.wx.until.HttpGetUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.JSPay.dao.PayRefundDao;
import com.jrwp.JSPay.entity.PayRefund;
import com.jrwp.JSPay.entity.RefundApply;
import com.jrwp.JSPay.helper.RefundApplyHelper;
import com.jrwp.JSPay.until.AmountUtils;
import com.jrwp.JSPay.until.CertHttpUtil;
import com.jrwp.JSPay.until.WXPayUtil;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.HttpRequestUntil;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.Until.ComUntil;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.payMent.entity.PayWan;
import com.jrwp.payMent.service.PayWanService;

@Description("退款控制器")
@Controller
@RequestMapping("refund")
public class WxRefundAction {

    @Resource
    private PayWanService payWanService;

    @Resource
    private DeptPayConfigDao deptPayConfigDao;

    @Resource
    private PayRefundDao payRefundDao;

    @Resource
    private IUserService userService;
    @Resource
    private IDeptService deptService;
    @Resource
    private IPoliceClassService policeClassService;

    @Description("待审核退款")
    @RequestMapping("waitrefund")
    public ModelAndView waitrefund(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("wan/waitrefund");
            Core_User user = (Core_User) request.getSession().getAttribute("user");
            Long deptid = userService.getdeptid(user.getUserName());
            List<Integer> ids = deptService.getchildsid(deptid.intValue());
            StringBuffer stringBuffer = new StringBuffer(deptid + "");
            for (Integer id : ids) {
                stringBuffer.append("," + id);
            }
            modelAndView.addObject("deptid", stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Description("待审核退款JSon")
    @ResponseBody
    @RequestMapping("waitrefundListJson")
    public RefundApplyHelper waitrefundListJson(HttpServletRequest request, HttpSession session, @RequestParam(value = "page",
            defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String queryinfo) {
        RefundApplyHelper refundApplyHelper = null;
        try {
            Core_User user = (Core_User) request.getSession().getAttribute("user");
            QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
            String whereSql = "where 1=1";
            if (queryinfo == null) {
                if (user.getId() != 1) {
                    Long deptid = userService.getdeptid(user.getUserName());
                    String nowdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    whereSql = QueryHelper.getWhereSql("where  d.id in (select id from core_dept start with id =" + deptid + " " +
                            "connect by prior id=parentid) and  ra.createtime >= to_date(CONCAT('" + nowdate + "',' 00:00:00')," +
                            "'yyyy-mm-dd hh24:mi:ss') and ra.createtime <= to_date(CONCAT('" + nowdate + "',' 23:59:59')," +
                            "'yyyy-mm-dd " + "hh24:mi:ss')", queryInfo.getWhereInfos());
                }
            } else {
                whereSql = QueryHelper.getWhereSql("", queryInfo.getWhereInfos());
            }
            PageHelper.startPage(pageNum, pageSize);
            List<RefundApply> refundApplies = payRefundDao.selectWaitApply(user.getId(), whereSql);
            PageInfo pageInfo = new PageInfo(refundApplies);
            refundApplyHelper = new RefundApplyHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refundApplyHelper;
    }

    @Description("退款信息页面")
    @RequestMapping("refundinfo")
    public ModelAndView refundinfo(HttpServletRequest request, Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            RefundApply refundApply = payRefundDao.getRefundInfo(id);
            modelAndView.setViewName("wan/refundinfo");
            modelAndView.addObject("refund", refundApply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Description("我的退款")
    @RequestMapping("myrefund")
    public ModelAndView myrefund(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("wan/myrefund");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Description("我的退款JSon")
    @ResponseBody
    @RequestMapping("myrefundListJson")
    public RefundApplyHelper myrefundJson(HttpServletRequest request, HttpSession session, @RequestParam(value = "page",
            defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String queryinfo) {
        RefundApplyHelper refundApplyHelper = null;
        try {
            Core_User user = (Core_User) request.getSession().getAttribute("user");
            PageHelper.startPage(pageNum, pageSize);
            if (queryinfo == null) {
                String nowdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                queryinfo = "{\"whereInfos\":[{\"fieldName\":\"ra.createtime\",\"whereField\":\">=\"," +
                        "\"fieldType\":\"DateTime\"," + "\"whereType\":\"where\",\"andOr\":\"and\"," + "\"queryValue\":" + "\"" +
                        nowdate + "\"}," + "{\"fieldName\":\"ra.createtime\",\"whereField\":\"<=\",\"fieldType\":\"DateTime\"," +
                        "\"whereType\":\"where\"," + "\"andOr\":\"and\",\"queryValue\":\"" + nowdate + "\"}]}";
            }
            QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
            String whereSql = QueryHelper.getWhereSql("", queryInfo.getWhereInfos());
            List<RefundApply> refundApplies = payRefundDao.selectApplyByUserId(user.getId(), whereSql);
            PageInfo pageInfo = new PageInfo(refundApplies);
            refundApplyHelper = new RefundApplyHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refundApplyHelper;
    }

    @Description("发起退款")
    @RequestMapping("refundApply")
    public ModelAndView refundApply(HttpServletRequest request, String ordernumber) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Core_User user = (Core_User) request.getSession().getAttribute("user");
            user = userService.getByUserName(user.getUserName());
            String pccode = user.getPcCode();
            List<PoliceClassService> policeClassServices = policeClassService.getObjectByPcode(pccode);
            List<PoliceClassService> list = new ArrayList<>();
            for (PoliceClassService classService : policeClassServices) {
                if (classService.getOrderCode().length() == 10) {
                    List<PoliceClassService> childs = new ArrayList<>();
                    for (PoliceClassService service : policeClassServices) {
                        if (service.getParentCode().equals(classService.getSerCode())) {
                            childs.add(service);
                        }
                    }
                    classService.setChildren(childs);
                    list.add(classService);
                }
            }
            PayWan payWan = payWanService.getByOrderNumber(ordernumber);
            modelAndView.addObject("paymoney", payWan.getPaymoney());
            modelAndView.addObject("services", JacksonUtil.toJson(list));
            System.out.println(JacksonUtil.toJson(list));
            modelAndView.setViewName("wan/refuninfo");
            modelAndView.addObject("ordernumber", ordernumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Description("保存退款请求")
    @RequestMapping("saveApply")
    @ResponseBody
    public DoResult saveapply(RefundApply refundApply, HttpServletRequest request, String[] workcode, Double[] refundMoney, String[]
            worknumber, boolean isAll, Double total) {
        DoResult result = new DoResult();
        try {

            Core_User user = (Core_User) request.getSession().getAttribute("user");
            refundApply.setApplyuser(user.getId().intValue());
            //RefundApply apply = payRefundDao.selectApplyByordernumber(refundApply.getOrdernumber());
            PayWan payWan = payWanService.getByOrderNumber(refundApply.getOrdernumber());
            if (isAll) {
                total = payWan.getPaymoney().doubleValue();
            }
            if (total > payWan.getPaymoney().doubleValue()) {
                result.setStateType(DoResultType.fail);
                result.setStateMsg("退款金额大于支付金额！");
                return result;
            }
            payRefundDao.savaApplySelective(refundApply);
            PayRefund payRefund = new PayRefund();
            payRefund.setRefundtype(payWan.getPaytype());
            payRefund.setUserid(user.getId());
            payRefund.setOrdernumber(refundApply.getOrdernumber());
            payRefund.setRefundmoney(total);
            payRefund.setIsexport(0);
            payRefund.setOutrefundno("KTHTK" + refundApply.getId());
            payRefundDao.saveRefund(payRefund);
            if (!isAll) {
                for (int i = 0; i < workcode.length; i++) {
                    RefundDetails refundDetails = new RefundDetails();
                    refundDetails.setRefundid(payRefund.getId());
                    refundDetails.setRefundMoney(refundMoney[i]);
                    refundDetails.setWorkCode(workcode[i]);
                    refundDetails.setWorkNumber(worknumber[i]);
                    payRefundDao.saveRefundDetails(refundDetails);
                }
            } else {
                RefundDetails refundDetails = new RefundDetails();
                refundDetails.setRefundid(payRefund.getId());
                refundDetails.setRefundMoney(payWan.getPaymoney().doubleValue());
                refundDetails.setWorkCode("0");
                refundDetails.setWorkNumber("0");
                payRefundDao.saveRefundDetails(refundDetails);
            }
            result.setStateType(DoResultType.success);
            result.setStateMsg("提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStateType(DoResultType.fail);
            result.setStateMsg("提交失败");
        }
        return result;
    }

    @Description("取消退款")
    @RequestMapping("cancleApply")
    @ResponseBody
    public DoResult cancleApply(RefundApply refundApply, HttpServletRequest request) {
        DoResult result = new DoResult();
        try {
            refundApply.setCheckstatus(3);
            payRefundDao.updateApplyByid(refundApply);
            result.setStateType(DoResultType.success);
            result.setStateMsg("取消成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStateType(DoResultType.fail);
            result.setStateMsg("取消失败");
        }
        return result;
    }

    @Description("驳回退款")
    @RequestMapping("checkApply")
    @ResponseBody
    public DoResult checkApply(RefundApply refundApply, HttpServletRequest request, Integer type) {
        DoResult result = new DoResult();
        try {
            Core_User user = (Core_User) request.getSession().getAttribute("user");
            refundApply.setCheckstatus(2);
            refundApply.setCheckuser(user.getId().intValue());
            refundApply.setChecktime(new Date());
            payRefundDao.updateApplyByid(refundApply);
            result.setStateType(DoResultType.success);
            result.setStateMsg("取消成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStateType(DoResultType.fail);
            result.setStateMsg("取消失败");
        }
        return result;
    }

    @Description("退款操作")
    @RequestMapping("orders")
    @ResponseBody
    public DoResult refund(Integer id, HttpServletRequest request) {
        // StringBuffer xml = new StringBuffer();
        // String data = null;
        DoResult result = new DoResult();
        try {
            RefundApply refundApply = payRefundDao.selectApplyById(id);
            String ordernumber = refundApply.getOrdernumber();
            if (refundApply.getCheckstatus() == 0) {
                PayWan payWan = payWanService.getByOrderNumber(refundApply.getOrdernumber());
                String deptcode = (String) payWan.getDeptcode();
                if (payWan.getPaytype() == 1) {//支付宝退款
                    //支付宝交易号，和商户订单号二选一
                    PayConfig config = deptPayConfigDao.getdeptConfig(deptcode, 0);
                    String trade_no = refundApply.getOrdernumber();
                    String tknumber = "KTHTK" + refundApply.getId();
                    String refund_amount = new String(payWan.getPaymoney().toString().getBytes("ISO-8859-1"), "UTF-8");
                    String out_request_no = new String(( "TK" + payWan.getId() ).getBytes("ISO-8859-1"), "UTF-8");
                    AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, config.getAppid(), config
                            .getMerchantprivatekey_java(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
                    AlipayTradeRefundRequest ali_request = new AlipayTradeRefundRequest();
                    AlipayTradeRefundModel model = new AlipayTradeRefundModel();
                    // model.setOutTradeNo(out_trade_no);
                    model.setTradeNo(trade_no);
                    model.setRefundAmount(refund_amount);
                    model.setRefundReason(refundApply.getRefundreson());
                    model.setOutRequestNo(out_request_no);
                    ali_request.setBizModel(model);
                    AlipayTradeRefundResponse response = alipayClient.execute(ali_request);
                    String strResponse = response.getCode();
                    if ("10000".equals(response.getCode())) {
                        strResponse = "退款成功";
                    } else {
                        strResponse = response.getSubMsg();
                    }
//                    AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, config.getAppid(), config
//                            .getMerchantprivatekey_java(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
//                    AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
//                    String refund_amount = new String(payWan.getPaymoney().toString().getBytes("ISO-8859-1"), "UTF-8");
//                    //退款的原因说明
//                    String refund_reason = new String(refundApply.getRefundreson().getBytes("ISO-8859-1"), "UTF-8");
//                    //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
                    // String tknumber = "TK" + payWan.getId();
                    //String out_request_no = new String(( "TK" + payWan.getId() ).getBytes("ISO-8859-1"), "UTF-8");
//                    AlipayTradeRefundModel model = new AlipayTradeRefundModel();
//                    // model.setOutTradeNo(out_trade_no);
//                    model.setTradeNo(trade_no);
//                    model.setRefundAmount(refund_amount);
//                    model.setRefundReason(refund_reason);
//                    model.setOutRequestNo(out_request_no);
//                    alipay_request.setBizModel(model);
//                    AlipayTradeRefundResponse ob = client.execute(alipay_request);
//                    System.out.println(JacksonUtil.toJson(ob));
                    //System.out.println(alipay_response.getBody());
                } else if (payWan.getPaytype() == 2) {//微信退款
                    PayConfig config = deptPayConfigDao.getdeptConfig(deptcode, 1);
                    String nonceStr = WXPayUtil.generateNonceStr();
                    String appid = config.getAppid();
                    String mch_id = config.getPaymentpartnerid();
                    String apikey = config.getPaykey();
                    String tknumber = "KTHTK" + refundApply.getId();
                    // xml.append("</xml>");
                    SortedMap<String, String> parameters = new TreeMap<String, String>();
                    if (config.getIsfacilitator() == 1) {
                        System.out.println("服务商退款");
                        //服务商
                        parameters.put("appid", ComUntil.KTH_APPID);
                        parameters.put("sub_appid", appid);
                        parameters.put("mch_id", ComUntil.KTH_MCH_ID);
                        parameters.put("sub_mch_id", mch_id);
                        parameters.put("nonce_str", nonceStr);
                        // parameters.put("out_trade_no", "LH1244");
                        parameters.put("transaction_id", ordernumber);
                        parameters.put("out_refund_no", tknumber);
                        parameters.put("fee_type", "CNY");
                        parameters.put("total_fee", AmountUtils.changeY2F(payWan.getPaymoney() + ""));
                        parameters.put("refund_fee", AmountUtils.changeY2F(payWan.getPaymoney() + ""));
                        // parameters.put("op_user_id", mch_id);
                        parameters.put("notify_url", "http://" + ComUntil.COM + "/paymentOut/refund/wx/callback");// 回调通知的URL
                        System.out.println("http://" + ComUntil.COM + "/paymentOut/refund/wx/callback");
                        String sign = WXPayUtil.generateSignature(parameters, ComUntil.KTH_PAYKEY);
                        parameters.put("sign", sign);
                    } else {
                        System.out.println("非服务商退款");
                        parameters.put("appid", appid);
                        parameters.put("mch_id", mch_id);
                        parameters.put("nonce_str", nonceStr);
                        // parameters.put("out_trade_no", "LH1244");
                        parameters.put("transaction_id", ordernumber);
                        parameters.put("out_refund_no", tknumber);
                        parameters.put("fee_type", "CNY");
                        parameters.put("total_fee", AmountUtils.changeY2F(payWan.getPaymoney() + ""));
                        parameters.put("refund_fee", AmountUtils.changeY2F(payWan.getPaymoney() + ""));
                        // parameters.put("op_user_id", mch_id);
                        parameters.put("notify_url", "http://" + ComUntil.COM + "/paymentOut/refund/wx/callback");// 回调通知的URL
                        System.out.println("http://" + ComUntil.COM + "/paymentOut/refund/wx/callback");
                        String sign = WXPayUtil.generateSignature(parameters, apikey);
                        parameters.put("sign", sign);
                    }
                    String xml = WXPayUtil.mapToXml(parameters);
                    // String requestXML = PayCommonUtil.getRequestXml(m);
                    // 带证书的post
                    String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
                    String path = "";
                    String resXml = "";
                    if (config.getIsfacilitator() == 1) {
                        path = WxRefundAction.class.getClassLoader().getResource("cert/jxkth.p12").getPath();
                        resXml = CertHttpUtil.postData(url, xml, ComUntil.KTH_MCH_ID, path);
                    } else {
                        path = WxRefundAction.class.getClassLoader().getResource("cert/" + deptcode + ".p12").getPath();
                        resXml = CertHttpUtil.postData(url, xml, mch_id, path);
                    }
                    System.out.println(path);

                    // //解析xml为集合，请打断点查看resXml详细信息
                    Map<String, String> map = WXPayUtil.xmlToMap(resXml);
                    // 查看申请退款状态
                    if (resXml.indexOf("SUCCESS") != -1) {
                        System.out.println("SUCCESS");
                        // 存储到退款信息表
                        result.setStateMsg("审核成功~");
                        result.setStateType(DoResultType.success);
                        result.setStateValue(resXml);
                        try {
                            Core_User user = (Core_User) request.getSession().getAttribute("user");
                            // RefundApply refundApply = payRefundDao.selectApplyByordernumber(ordernumber);
                            // refundApply.setOrdernumber(ordernumber);
                            refundApply.setCheckstatus(1);
                            refundApply.setChecktime(new Date());
                            refundApply.setCheckuser(user.getId().intValue());
                            payRefundDao.updateApplyByid(refundApply);
                            Integer userid = refundApply.getApplyuser();
                            // Core_User user =
                            // userService.getObjectById(userid.longValue());

                        } catch (Exception e) {
                            MsgManager.writeError(request, e);
                            e.printStackTrace();
                        }

                        // Map<String, String> map1 = WXPayUtil.xmlToMap(resXml);
                        // String prepay_id = (String) map.get("prepay_id");
                    }
                    System.out.println(resXml);
                } else {
                    result.setStateMsg("该记录已经退款了,或者取消了~");
                    result.setStateType(DoResultType.warning);
                }
            }
        } catch (Exception e) {
            result.setStateMsg("发起退款出现异常");
            result.setStateValue(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("wx/callback")
    @ResponseBody
    public String getBack(HttpServletRequest request) {
        System.out.println("微信开始退款通知 开始 ========================");
        try {

            // 接收微信退款通知的数据流
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (( len = inStream.read(buffer) ) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            // 微信退款通知返回串
            String resultStr = new String(outSteam.toByteArray(), "utf-8");
            System.out.println("返回的字符串=======================");
            System.out.println(resultStr);
            // 将返回串转换成 Map
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(resultStr);
            // 在return_code为SUCCESS的时候有返回 req_info
            if ("SUCCESS".equals(xmlToMap.get("return_code"))) {
                String reqInfo = xmlToMap.get("req_info");// 退款返回加密信息
                // 根据返回的商户号和appid 获取秘钥
                String mchid = xmlToMap.get("sub_mch_id");
                String appid = xmlToMap.get("sub_appid");
                String aipKey = ComUntil.KTH_PAYKEY;
                if (mchid == null) {
                    mchid = xmlToMap.get("mch_id");
                    appid = xmlToMap.get("appid");
                    PayConfig config = deptPayConfigDao.getByAppidAndMchId(appid, mchid);
                    aipKey = config.getPaykey();
                }
                // 对商户key做md5
                String keyMd5 = WXPayUtil.MD5(aipKey).toLowerCase();
                // 对加密串req_info做base64解码
                byte[] decodeReqInfo = decode(reqInfo);
                // PKCS7Padding填充方式
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyMd5.getBytes(), "AES"));
                byte[] decoded = cipher.doFinal(decodeReqInfo);
                // 获取解密后的返回串，这个才是真正需要的数据
                String decryptInfo = new String(decoded, "UTF-8");
                System.out.println("加密的数据是=-==================");
                System.out.println(decryptInfo);
                // 将返回串转换成 Map
                Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(decryptInfo);
                // 后面的逻辑自己处理
                System.out.println(decryptInfo);
                String result = reqInfoMap.get("refund_status");
                String out_refund_no = reqInfoMap.get("out_refund_no");// 商户退款单号
                String refund_id = reqInfoMap.get("refund_id");// 退款订单号
                String ordernumber = reqInfoMap.get("transaction_id");// 退款订单号
                // String out_refund_no=reqInfoMap.get("out_refund_no");
                System.out.println("返回的状态是：" + reqInfoMap.get("refund_status"));
                if (result.equals("SUCCESS")) {
                    // 更新退款表的状态
                    PayRefund payRefund = new PayRefund();
                    payRefund.setOutrefundno(out_refund_no);
                    payRefund.setRefundnumber(refund_id);
                    payRefundDao.updateStatus(payRefund);
                    RefundApply refundApply = new RefundApply();
                    refundApply.setOrdernumber(ordernumber);
                    refundApply.setCheckstatus(4);
                    Pattern pattern = Pattern.compile("[^0-9]");
                    Matcher matcher = pattern.matcher(out_refund_no);
                    String all = matcher.replaceAll("");
                    refundApply.setId(Integer.parseInt(all));
                    payRefundDao.updateApplyByid(refundApply);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
            return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[接口异常]]></return_msg></xml>";
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

    }

//    @Scheduled(cron = "0 0/5 * * * ?")
    public void refundcheck() {
        try {
            System.out.println("退款查询，工作开始,开始时间：" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 1);// 让日期加1
            // System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(calendar.getTime()));
            List<PayRefund> needs = payRefundDao.getPayRefund(calendar.getTime());// 每次只查询一个小时以前没有修改状态的数据
            for (PayRefund need : needs) {
                String ordernumber = need.getOrdernumber();
                PayWan payWan = payWanService.getByOrderNumber(ordernumber);
                String deptcode = (String) payWan.getDeptcode();
                PayConfig config = deptPayConfigDao.getdeptConfig(deptcode, 1);
                String nonceStr = WXPayUtil.generateNonceStr();
                String appid = config.getAppid();
                String mch_id = config.getPaymentpartnerid();
                String apikey = config.getPaykey();
                String refundUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
                String tknumber = "KTHTK" + need.getId();
                SortedMap<String, String> parameters = new TreeMap<String, String>();
                if (config.getIsfacilitator() == 1) {
                    parameters.put("appid", ComUntil.KTH_APPID);
                    parameters.put("mch_id", ComUntil.KTH_MCH_ID);
                    parameters.put("sub_mch_id", mch_id);
                    parameters.put("nonce_str", nonceStr);
                    // parameters.put("out_trade_no", "LH1244");
                    parameters.put("out_refund_no", tknumber);
                    String sign = WXPayUtil.generateSignature(parameters, ComUntil.KTH_PAYKEY);
                    parameters.put("sign", sign);
                } else {
                    parameters.put("appid", appid);
                    parameters.put("mch_id", mch_id);
                    parameters.put("nonce_str", nonceStr);
                    // parameters.put("out_trade_no", "LH1244");
                    parameters.put("out_refund_no", tknumber);
                    String sign = WXPayUtil.generateSignature(parameters, apikey);
                    parameters.put("sign", sign);
                }

                String xml = WXPayUtil.mapToXml(parameters);
                String xmlStr = HttpRequestUntil.sendPost(refundUrl, xml);
                System.out.println(xmlStr);
                if (xmlStr.indexOf("SUCCESS") != -1) {
                    Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(xmlStr);
                    String out_refund_no = reqInfoMap.get("out_refund_no_0");
                    String refund_id = reqInfoMap.get("refund_id_0");
                    // 更新退款表的状态
                    if (refund_id != null) {
                        PayRefund payRefund = new PayRefund();
                        payRefund.setOutrefundno(out_refund_no);
                        payRefund.setRefundnumber(refund_id);
                        payRefundDao.updateStatus(payRefund);
                        RefundApply refundApply = new RefundApply();
                        refundApply.setOrdernumber(ordernumber);
                        refundApply.setCheckstatus(4);
                        Pattern pattern = Pattern.compile("[^0-9]");
                        Matcher matcher = pattern.matcher(out_refund_no);
                        String all = matcher.replaceAll("");
                        refundApply.setId(Integer.parseInt(all));
                        payRefundDao.updateApplyByid(refundApply);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @Scheduled(cron = "0 12 14 * * ?")
//    @Scheduled(cron = "0 30 23 * * ?")
    public void getWXBill() {
        try {
            System.out.println("工作开始,开始时间：" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            String billURl = "https://api.mch.weixin.qq.com/pay/downloadbill";
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            String nonceStr = WXPayUtil.generateNonceStr();
            String appid = "wx6d4d594ef75edec4";
            String mch_id = "1333513601";
            String apikey = "1765jkk9lx3prpogktzn1wmxlffsqsnk";
            Date date = new Date();// 取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dateString = formatter.format(date);
            parameters.put("appid", appid);
            parameters.put("mch_id", mch_id);
            // parameters.put("device_info", "");//微信支付分配的终端设备号，填写此字段，只下载该设备号
            // 的对账单
            parameters.put("nonce_str", nonceStr);
            parameters.put("bill_date", dateString);// 下载对账单的日期，格式：20140603，日期不可为当天。
            // bill_type:ALL返回当日所有订单信息,默认值SUCCESS返回当日成功支付的订单。REFUND，返回当日退款订单
            parameters.put("bill_type", "REFUND");
            String sign = WXPayUtil.generateSignature(parameters, apikey);
            parameters.put("sign", sign);
            String xml = WXPayUtil.mapToXml(parameters);
            String xmlStr = HttpRequestUntil.sendPost(billURl, xml);
            LogUtil.getLogger().info("工作开始,开始时间：" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            LogUtil.getLogger().info(xmlStr);
            System.out.println(xmlStr);
            String str = xmlStr;// 获取对账报文
            String newStr = str.replaceAll(",", " "); // 去空格
            String[] tempStr = newStr.split("`"); // 数据分组
            String[] t = tempStr[0].split(" ");// 分组标题
            int k = 1; // 纪录数组下标
            int j = tempStr.length / 24; // 计算循环次数
            for (int i = 0; i < j; i++) {
                System.out.println(t[5] + ":" + tempStr[k + 5]);// 微信订单号
                String ordernumber = tempStr[k + 5].trim();
                System.out.println(t[14] + ":" + tempStr[k + 14]);// 退款申请时间
                Date createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tempStr[k + 14].trim());
                System.out.println(t[15] + ":" + tempStr[k + 15]);// 退款成功时间
                Date refundtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tempStr[k + 15].trim());
                System.out.println(t[16] + ":" + tempStr[k + 16]);// 微信退款单号
                String refundnumber = tempStr[k + 16].trim();
                System.out.println(t[17] + ":" + tempStr[k + 17]);// 商户退款单号
                String outrefundno = tempStr[k + 17].trim();
                System.out.println(t[18] + ":" + tempStr[k + 18]);// 退款金额
                Double refundmoney = Double.parseDouble(tempStr[k + 18].trim());
                System.out.println(t[21] + ":" + tempStr[k + 21]);// 退款状态
                int refundstatus = tempStr[k + 21].trim().equals("SUCCESS") ? 1 : 2;
                System.out.println("---------");// 摘取有用数据存入数据库
                PayRefund there = payRefundDao.getPayRefundByRefundNumber(refundnumber);
                if (there == null) {// 记录表中没有 增加这条记录
                    there = new PayRefund();
                    there.setRefundstatus(refundstatus);
                    there.setOutrefundno(outrefundno);
                    there.setRefundnumber(refundnumber);
                    there.setRefundmoney(refundmoney);
                    there.setCreatetime(createtime);
                    there.setRefundtime(refundtime);
                    there.setOrdernumber(ordernumber);
                    there.setIsbill(1);
                    there.setIsexport(0);
                    payRefundDao.saveBillRefund(there);
                }
                k = k + 26;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] decode(String key) throws Exception {
        System.out.println(new BASE64Decoder().decodeBuffer(key));
        return new BASE64Decoder().decodeBuffer(key);
    }
}