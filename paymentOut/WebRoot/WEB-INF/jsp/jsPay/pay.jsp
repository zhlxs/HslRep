<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>开始支付</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <style>
        html {
            font-size: 20px;
        }

        body {
            background: #F5F5F5;
            font-size: 16px;
        }

        .check_photo {
            text-align: center;
            padding: 10px;
        }

        .check_photo > img {
            border: 2px solid #fff;
            width: 125px;
            height: 155px;
            box-shadow: 0 2px 5px #000;
        }

        .info {
            color: #707070;
            font-size: 0.85rem;
            float: left;
            margin-left: 7px;
        }

        .item_wrap {
            overflow: hidden;
            padding: 0.5rem 0.5rem 0.15rem;
            background: #fff;
            margin-top: 10px;
        }

        .item {
            overflow: hidden;
            border-bottom: 1px solid #DADADA;
            line-height: 34px;
            padding: 5px;
        }

        .item > img {
            transform: scale(2);
            -ms-transform: scale(2);
            -webkit-transform: scale(0.8);
            -o-transform: scale(2);
            -moz-transform: scale(2);
        }

        .tru {
            color: #000;
            /*font-weight: 600;*/
        }

        .check_res {
            padding: 0.3rem;
            overflow: hidden;
        }

        #check_resultS {
            color: #51A938;
            margin-top: 5px;
            margin-left: 3px;
        }

        #check_resultF {
            color: #FB4748;
            margin-top: 5px;
            margin-left: 3px;
        }

        .check_pass, .check_no_pass {
            color: #fff;
            font-size: 1rem;
        }

        #pass, #no_pass {
            padding: 0.5rem 0.8rem;
            background: #51A938;
            text-align: center;
            width: 85%;
            margin: 25px auto 0;
            border-radius: 5px;
        }

        .nopass_reason {
            padding: 0.5rem 0.8rem;
            border-radius: 5px;
            text-align: center;
            border: 1px solid #FB4748;
            width: 85%;
            margin: 0 auto;
        }

        #reason_info {
            color: #000;
            font-size: 1rem;
        }
    </style>
</head>
<body>
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent" style="background: #000; opacity: 0.5"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>
<input type="hidden" id="apprseq" value="${parms.apprseq}">
<ul class="item_wrap">
    <li class="item" style="margin-top: 5px">
        <img src="${pageContext.request.contextPath }/wx/img/IDcard.png" alt="" style="float: left">
        <span class="info">商品描述：</span>
        <span id="ID" class="info tru">${parms.orderDescribe}</span>
        <!--<img id="plus" src="img/plus.png" alt="" style="float: right">-->
    </li>
    <li class="item" style="margin-top:5px;border: none">
        <img src="${pageContext.request.contextPath }/wx/img/cost.png" alt="" style="float: left;margin-left: 7px">
        <span class="info" style="margin-left: 14px;">支付费用：</span>
        <span style="color: #F74744;float: left;line-height: 34px">￥</span>
        <span id="cost" class="info" style="color: #F74744;margin: 0">${parms.payMoney}</span>
    </li>
</ul>
<div class="check_res" style="display: block">
    <%-- <div style="overflow: hidden">
         <img src="img/checkS.png" alt="" style="transform: scale(0.7);float: left">
         <span id="check_resultS" style="float: left">该身份证核验成功</span>
     </div>--%>
    <p id="pass" onclick="pay()">
        <span class="check_pass"></span><span class="check_pass">支付￥${parms.payMoney}元</span>
    </p>
</div>

</body>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    var appId, timeStamp, nonceStr, package1, signType, paySign;

    function pay() {
        $("#loadingToast").css("display", "block");
        $("#pass").hide();
        var url = "${pageContext.request.contextPath }/wx/jsPay/orders";
        $.get(url, function (result) {
            //$("#pgq").text(JSON.stringify(result));
            //alert(JSON.stringify(result));
            if (result.ispay == '1') {
                alert("该订单已支付");
                WeixinJSBridge.call('closeWindow');
            }
            appId = result.appId;
            timeStamp = result.timeStamp;
            nonceStr = result.nonceStr;
            package1 = result.package;
            signType = result.signType;
            paySign = result.paySign;

            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady',
                        onBridgeReady, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady',
                        onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady',
                        onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        });

    }

    function onBridgeReady() {
        WeixinJSBridge.invoke('getBrandWCPayRequest', {
                "appId": appId,     //公众号名称,由商户传入
                "timeStamp": timeStamp,         //时间戳,自1970年以来的秒数
                "nonceStr": nonceStr, //随机串
                "package": package1,
                "signType": signType,         //微信签名方式：
                "paySign": paySign //微信签名
            },
            function (res) {
                //alert(JSON.stringify(res));
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    // alert("success");
                    var apprseq = $("#apprseq").val();
                    //支付成功后跳转的页面
                    //先支付后评价
                    if (apprseq == 1) {
                        window.location.href = "${pageContext.request.contextPath }/wx/appraiseDetailAction/apprList";
                    } else {
                        WeixinJSBridge.call('closeWindow');
                    }
                } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                    //alert("cancel");
                    $("#loadingToast").hide();
                    $("#pass").show();
                } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                    //alert("fail");
                    WeixinJSBridge.call('closeWindow');
                } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok,但并不保证它绝对可靠。
            });
    }
</script>
</html>