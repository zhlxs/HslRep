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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/transit.css">
    <style>
        #title{
            font-weight: bold;
            color: #2063BA;
            font-family: 微软雅黑;
            font-size: 85px;
            text-shadow:5px 5px 5px #FFFFFF;
            /*font-style: italic;*/
            /*position: absolute;*/
            /*top:20%;*/
            /*left:22%;*/
        }
    </style>
</head>
<body>
<div style="position: relative">
    <img src="${pageContext.request.contextPath }/wx/img/banner1.png" alt="" style="width: 100%;">
    <div style="position: absolute;top: 25%;left: 25%;">
        <span id="title" class="title">${deptname}</span>
    </div>
</div>
<div style="background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto">
    <div style="height: 150px"></div>
    <div class="page__bd page__bd_spacing" style="margin-top: 50px;">
        <a href="${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=squenceInfoAction/getAppSquence"
           class="weui-btn weui-btn_primary art">本人预约</a>

        <a href="${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=squenceInfoAction/otherAppointme"
           class="weui-btn weui-btn_primary art">他人代约</a>

    </div>
    <div style="height:20px;"></div>
</div>
<div class="weui-footer" style="position: fixed;bottom: 30%;left: 21%;">
    <p style="color: #736666;font-size: 35px;height: 30px;text-align: center;">
        版权所有©${deptname}<br>
        技术支持 &nbsp;&nbsp;江西科泰华软件有限公司
    </p>
</div>
</body>
</html>
