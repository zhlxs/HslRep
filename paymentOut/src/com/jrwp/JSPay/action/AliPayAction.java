package com.jrwp.JSPay.action;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jrwp.JSPay.entity.AlipayConfig;
import com.jrwp.JSPay.until.AmountUtils;
import com.jrwp.follow.dao.UserPayParmsDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.follow.service.SaveSyncService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("wx/aliPay")
public class AliPayAction {
    @Resource
    private SaveSyncService saveSyncService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("jsPay/aliPay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping("orders")
    public void pay(HttpServletRequest request, HttpServletResponse response) {
        try {
            QrCodeParms parms = (QrCodeParms) request.getSession().getAttribute("QrParms");
            PayConfig config = (PayConfig) request.getSession().getAttribute("payconfig");

            // 商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = saveSyncService.saveSync(parms, config);
            // 订单名称，必填
            String subject = parms.getOrderDescribe();
            System.out.println(subject);
            // 付款金额，必填
            String total_amount = parms.getPayMoney() + "";
            // 商品描述，可空
            String body = parms.getOrderDescribe();
            // 超时时间 可空
            String timeout_express = "2m";
            // 销售产品码 必填
            String product_code = "QUICK_WAP_WAY";
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, config.getAppid(), config.getMerchantprivatekey_java(),
                    AlipayConfig.FORMAT, AlipayConfig.CHARSET);
            AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(out_trade_no);
            model.setSubject(subject);
            model.setTotalAmount(total_amount);
            model.setBody(body);
            model.setTimeoutExpress(timeout_express);
            model.setProductCode(product_code);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(AlipayConfig.notify_url);
            // 设置同步地址
            alipay_request.setReturnUrl(AlipayConfig.return_url);

            // form表单生产
            String form = "";

            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("refund")
    public void refund(HttpServletRequest request, HttpServletResponse response, String ordernumber) {
        try {

            //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
            //商户订单号，和支付宝交易号二选一
            // String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号，和商户订单号二选一
            String trade_no = ordernumber;
            //退款金额，不能大于订单总金额
            String refund_amount = new String("0.1".getBytes("ISO-8859-1"), "UTF-8");
            //退款的原因说明
            String refund_reason = new String("不想办了".getBytes("ISO-8859-1"), "UTF-8");
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
            String out_request_no = new String("TK4240".getBytes("ISO-8859-1"), "UTF-8");
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
                    AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();

            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            // model.setOutTradeNo(out_trade_no);
            model.setTradeNo(trade_no);
            model.setRefundAmount(refund_amount);
            model.setRefundReason(refund_reason);
            model.setOutRequestNo(out_request_no);
            alipay_request.setBizModel(model);

            AlipayTradeRefundResponse alipay_response = client.execute(alipay_request);
            System.out.println(alipay_response.getBody());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
