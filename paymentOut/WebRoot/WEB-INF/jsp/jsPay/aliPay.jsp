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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <style>
        body {
            background: #F5F5F5;
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
            background: #2f7bc4;
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
<input type="hidden" id="apprseq" value="${parms.apprseq}">
<input type="hidden" id="ispay" value="${ispay}">
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
<script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    function pay() {
        var ispay = $("#ispay").val();
        if (ispay == 1) {
            alert("该订单已支付");
            document.addEventListener('AlipayJSBridgeReady', function () {
                AlipayJSBridge.call('closeWebview');
            })
        }
        $("#pass").hide();
        window.location.href = "${pageContext.request.contextPath }/wx/aliPay/orders";
    }

    function GetDateNow() {
        var vNow = new Date();
        var sNow = "";
        sNow += String(vNow.getFullYear());
        sNow += String(vNow.getMonth() + 1);
        sNow += String(vNow.getDate());
        sNow += String(vNow.getHours());
        sNow += String(vNow.getMinutes());
        sNow += String(vNow.getSeconds());
        sNow += String(vNow.getMilliseconds());
        document.getElementById("WIDout_trade_no").value = sNow;
        document.getElementById("WIDsubject").value = "手机网站支付测试商品";
        document.getElementById("WIDtotal_amount").value = "0.01";
        document.getElementById("WIDbody").value = "购买测试商品0.01元";
    }

    GetDateNow();
</script>
</html>