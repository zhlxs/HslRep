<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/wx/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/wx/css/weui.css">
    <title>完成</title>
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">评价成功</h2>
        <p class="weui-msg__desc">
            再次感谢您的配合以及您对我们工作的评价！
            <!--<a href="javascript:void(0);">文字链接</a>-->
        </p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="javascript:;" class="weui-btn weui-btn_primary">我已支付</a>
            <a href="javascript:;" class="weui-btn weui-btn_default">我未支付</a>
        </p>
    </div>
    <div class="weui-msg__extra-area">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">版权所有©XXX公安局</a>
            </p>
            <p class="weui-footer__text">技术支持  江西科泰华软件有限公司</p>
        </div>
    </div>
</div>
</body>
</html>