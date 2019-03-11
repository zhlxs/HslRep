<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <title>重复预约</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.css">
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">${msg}</h2>
        <%--<p class="weui-msg__desc" style="font-size: 16px;margin-top: 20px;">预约号已作废，请您按照现场办事流程处理</p>--%>

    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="${pageContext.request.contextPath }/appointmeAction/getOpenId"
               class="weui-btn weui-btn_primary">返回</a>

        </p>
    </div>
    <!--<div class="weui-msg__extra-area">-->
    <!--<div class="weui-footer">-->
    <!--<p class="weui-footer__links">-->
    <!--<a href="javascript:void(0);" class="weui-footer__link">底部链接文本</a>-->
    <!--</p>-->
    <!--<p class="weui-footer__text">Copyright © 2008-2016 weui.io</p>-->
    <!--</div>-->
    <!--</div>-->
</div>
</body>
</html>
