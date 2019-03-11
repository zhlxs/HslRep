<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>在线预约</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/transit.css">
</head>
<body>
<div>
    <img src="${pageContext.request.contextPath }/wx/img/banner.jpg" alt="" style="width: 100%;">
</div>
<div style="background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto">
    <div style="height: 150px"></div>
    <div class="page__bd page__bd_spacing" style="margin-top: 50px;">
        <a href="${pageContext.request.contextPath }/wx/appointmeType.jsp" class="weui-btn weui-btn_primary art">我已预约</a>

        <a href="${pageContext.request.contextPath }/wxAction/getCode?deptid=47&redirect_uri=squenceInfoAction/getcommSquence&appid=wx6d4d594ef75edec4&secret=5827020e5fd8d8a037afb09232a29813"
           class="weui-btn weui-btn_primary art">我未预约</a>

    </div>
    <div style="height:20px;"></div>
</div>
<div class="weui-footer" style="position: fixed;bottom: 30%;left: 21%;">
    <p style="color: #736666;font-size: 35px;height: 30px;text-align: center;">
        版权所有©红谷滩公安局<br>
        技术支持 &nbsp;&nbsp;江西科泰华软件有限公司
    </p>
</div>

</body>
</html>