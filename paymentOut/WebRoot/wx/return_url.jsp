<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <title>支付完成</title>
    <!-- 主文件 -->
    <link rel="stylesheet" href="https://gw.alipayobjects.com/as/g/antui/antui/10.1.10/dpl/antui.css"/>

    <!-- 组件 -->
    <link rel="stylesheet"
          href="https://gw.alipayobjects.com/as/g/antui/antui/10.1.10/??dpl/widget/message.css,dpl/icon/message.css,dpl/widget/search.css"/>

</head>
<body>
<div class="am-message result" role="alert">
    <i class="am-icon result pay" aria-hidden="true"></i>
    <div class="am-message-main">支付成功</div>
    <%--<div class="am-message-sub">￥20.00元</div>--%>
</div>
<div class="am-button-wrap">
    <button class="am-button blue" onclick="done()">完成</button>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    function done() {
        document.addEventListener('AlipayJSBridgeReady', function () {
            AlipayJSBridge.call('closeWebview');
        })
    }
</script>
</html>