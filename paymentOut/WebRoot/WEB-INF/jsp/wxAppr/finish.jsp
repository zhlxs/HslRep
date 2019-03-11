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
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${ctx}/css/weui.css">
    <title>完成</title>
</head>
<body>
<input type="hidden" name="apprseq" id="apprseq" value="${apprseq}">
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <c:if test="${apprseq==1}">
            <h2 class="weui-msg__title">支付完成</h2>
        </c:if>
        <c:if test="${apprseq==2}">
            <h2 class="weui-msg__title">评价完成</h2>
        </c:if>
        <p class="weui-msg__desc">
            再次感谢您的配合！
            <!--<a href="javascript:void(0);">文字链接</a>-->
        </p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <c:if test="${apprseq==1}">
                <a href="javascript:;" class="weui-btn weui-btn_primary" onclick="done()">完成</a>
            </c:if>
            <c:if test="${apprseq==2}">
                <a href="javascript:;" class="weui-btn weui-btn_primary" onclick="gopay()">开始支付</a>
            </c:if>
        </p>
    </div>
    <div class="weui-msg__extra-area">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">版权所有©XXX公安局</a>
            </p>
            <p class="weui-footer__text">技术支持 江西科泰华软件有限公司</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
<script>
    function done() {
        WeixinJSBridge.call('closeWindow');
    }

    function gopay() {
        window.location.href = "${pageContext.request.contextPath }/wx/follow/payAndAppr/?ispay=true";
    }
</script>
</html>