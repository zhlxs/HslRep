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
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <title>关注我~</title>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        ul,ol,li{
            list-style: none;
        }
        a{
            text-decoration: none;
        }
        img{
            border: none;
        }
        body{
            background: linear-gradient(#030C35,#0BC6E7 ) no-repeat;
            height: 1000px;
        }
        header{
            text-align: center;
        }
        header>img{
            margin-top: 50px;
            width: 100px;
            height: 90px;
        }
        header>p{
            color: #fff;
            font-size: 18px;
            margin-top: 10px;
        }
        .middle{
            margin: 30px auto 0;
            text-align: center;
        }
        .bottom{
            font-size: 25px;
            color: #000;
            text-align: center;
            margin: 20px auto;
        }
    </style>
</head>
<body>
<div class="wrap">
    <header>
        <img src="${pageContext.request.contextPath }/images/follow/logo1.png" alt="">
        <p>科泰华电子支付便民服务平台</p>
    </header>
    <div class="middle">
        <img src="${pageContext.request.contextPath }/qrCode/${qrName}" alt="二维码"  style=" width:200px;height: 200px;">
    </div>
    <p class="bottom">长按识别二维码加关注</p>
</div>
</body>
</html>
