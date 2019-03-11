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
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <title>扫码关注</title>
    <style>
        html{
            font-size: 20px;
        }
        body{
            background:#f2f2f2 url("${pageContext.request.contextPath }/wx/images/badge2.png") fixed center 30% no-repeat;
            line-height: 1.6;
            font-family: -apple-system-font, "Helvetica Neue", sans-serif;
            -webkit-tap-highlight-color: transparent;
        }
        .wrap{
            padding: 1rem;
        }
        .item_wrap{
            margin: 0 auto;
        }
        .item{
            text-align: center;
            margin-top: 1rem;
            overflow: hidden;
        }
        .item>img{
            width: 28px;
            height: 25px;
            margin-top: 3px;
        }
        .item>*{
            float: left;
        }
        .step{
            color: #3399ff;
            font-size: 1rem;
            font-weight: bold;
            margin-left: 6px;
        }
        .do{
            color: #000;
            font-size: 18px;
            margin-left: 35px;
            text-align: left;
            font-weight: bold;
        }
        #watch{
            margin: 12rem auto 0;
            width: 75%;
            border-radius: 5px;
            border: 1.5px solid #3399ff;
            padding: 5px 15px;
            color: #3399ff;
            font-size: 1rem;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="wrap">
    <ul class="item_wrap">
        <li class="item">
            <img src="${pageContext.request.contextPath }/wx/images/star.png" alt="">
            <span class="step">第一步</span>
            <span class="do">请点击下方关注按钮，关注我们的微信公众号。</span>
        </li>
        <li class="item">
            <img src="${pageContext.request.contextPath }/wx/images/star.png" alt="">
            <span class="step">第二步</span>
            <span class="do">使用微信“扫一扫”功能扫描电子屏幕上的支付二维码进行支付</span>
        </li>
    </ul>
    <p id="watch">点我关注</p>
</div>
</body>
<script>
    $("#watch").click(function(){
        window.location.href="${mainUrl}";
    })
</script>
</html>