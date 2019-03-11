<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.min.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <title>排队签到</title>
    <style>
        *{
            margin: 0;
            padding: 0;
            font-family: "Microsoft YaHei";
        }
        a{
            text-decoration: none;
        }
        input {
            -webkit-appearance: none;
            outline: none;
        }
        .page_hd{
            padding: 90px;
        }
        .text {
            font-size: 14px;
            margin-left: 85px;
            margin-right: 85px;
            line-height: 75px;
        }
        .weui-mask_transparent {
            position: fixed;
            z-index: 1000;
            top: 0;
            right: 0;
            left: 0;
            bottom: 0;
            background: #000;
            opacity: 0.5;
        }
        .text1{
            text-indent: 33px;
        }
        .page_hd {
            padding: 25px;
        }
        #title{
            font-weight: bold;
            color: #2063BA;
            font-family: 微软雅黑;
            font-size: 30px;
            text-shadow:5px 5px 5px #FFFFFF;
            /*font-style: italic;*/
            /*position: absolute;*/
            /*top:20%;*/
            /*left:22%;*/
        }
        .text {
            font-size: 16px;
            margin-left: 40px;
            margin-right: 40px;
            line-height: 30px;
        }
        .weui-mask_transparent {
            position: fixed;
            z-index: 1000;
            top: 0;
            right: 0;
            left: 0;
            bottom: 0;
            background: #000;
            opacity: 0.5;
        }
    </style>
</head>
<body>
<div id="loadingToast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast" style="top: 45%;">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content" style="">加载中请稍候...</p>
    </div>
</div>
<div style="position: relative">
    <img src="${pageContext.request.contextPath }/wx/img/banner1.png" alt="" style="width: 100%;">
    <div style="position: absolute;top: 25%;left: 25%;">
        <span id="title" class="title">${deptname}</span>
    </div>
</div>
<!--<div style="background: url('img/background.jpg');background-size: 100% auto">-->

<!--<div style="height: 150px"></div>-->

<div class="weui-msg" style="background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto;height: 155px;padding-top: 45px">
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">

            <c:if test="${sessionScope.openid==null}">
                <a id="order" href="javascript:;" class="weui-btn weui-btn_primary">我预约的</a>
                <a id="search" href="javascript:;" class="weui-btn weui-btn_primary">别人帮我约的</a>
            </c:if>
            <c:if test="${sessionScope.openid!=null}">
                <a id="start" href="${pageContext.request.contextPath }/squenceInfoAction/getAppSquence"
                   class="weui-btn weui-btn_primary">我预约的</a>
                <a id="start" href="${pageContext.request.contextPath }/squenceInfoAction/otherAppointme"
                   class="weui-btn weui-btn_primary">别人帮我约的</a>
            </c:if>
        </p>
    </div>




    <!--<div class="page__bd page__bd_spacing" style="margin-top: 40px;">-->
    <!--<a href="javascript:;" class="weui-btn weui-btn_primary"></a>-->

    <!--<a href="javascript:;" class="weui-btn weui-btn_primary"></a>-->

    <!--</div>-->
    <!--<div style="height:20px;"></div>-->
    <!--</div>-->
    <!--<div class="weui-footer" style="position: fixed;bottom: 30%;left: 21%;">-->
    <!--<p style="color: #736666;font-size: 35px;height: 30px;text-align: center;">-->
    <!--版权所有©红谷滩公安局<br>-->
    <!--技术支持 &nbsp;&nbsp;江西科泰华软件有限公司-->
    <!--</p>-->
    <!--</div>-->
    <div class="weui-msg__extra-area" style="top: 55%;">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">版权所有©${deptname}</a>
            </p>
            <p class="weui-footer__text">技术支持 © 江西科泰华软件有限公司</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#order").click(function () {
        $("#loadingToast").css("display","block");
        window.location.href = '${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=squenceInfoAction/getAppSquence'
    });
    $("#search").click(function () {
        $("#loadingToast").css("display","block");
        window.location.href = '${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=squenceInfoAction/otherAppointme'
    });
</script>
</html>
