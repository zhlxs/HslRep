<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <title>签到成功</title>
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area" style="text-align: center">
        <h2 class="weui-msg__title" style="text-align: center">签到成功</h2>
        <hr style="width: 100%;margin: 15px auto;">
        <p class="weui-msg__desc">你的编号是：</p>
        <h2 class="weui-msg__title" style="text-align: center">A001</h2>
    </div>
    <hr style="width: 90%;margin: 0 auto;">
    <div class="weui-msg__text-area" style="margin-top: 25px;">
        <p class="weui-msg__desc">
            你已签到成功！请耐心等待叫号~ 请勿离开现场，耐心等待窗口叫号，呼叫超时将需重新取号！
        </p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=squenceInfoAction/querysquenceInfo"
               class="weui-btn weui-btn_primary">排队进度查询</a>
        </p>
    </div>
    <!--<div class="weui-msg__extra-area">-->
    <!--<div class="weui-footer">-->
    <!--<p class="weui-footer__links">-->
    <!--<a href="javascript:void(0);" class="weui-footer__link">版权所有©XXX公安局</a>-->
    <!--</p>-->
    <!--<p class="weui-footer__text">技术支持  江西科泰华软件有限公司</p>-->
    <!--</div>-->
    <!--</div>-->
</div>
</body>
</html>
