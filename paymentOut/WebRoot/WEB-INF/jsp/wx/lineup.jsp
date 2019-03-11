<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <title>排队进度</title>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/fastclick.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-weui.js"></script>
    <style>
        #pc, #time {
            color: red;
        }

        .weui-form-preview__item {
            font-size: 18px;
        }

        .demos-header {
            padding: 35px 0;
        }

        .demos-title {
            text-align: center;
            font-size: 34px;
            color: #3cc51f;
            font-weight: 400;
            margin: 0 15%;
        }
    </style>
</head>
<body>
<header class="demos-header">
    <h1 class="demos-title">当前排队进度</h1>
</header>
<c:if test="${sta==0}">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">查询失败</h2>
            <p class="weui-msg__desc">抱歉！没有查询到您的排队信息</p>
        </div>
    </div>
</c:if>
<c:if test="${sta==1}">
    <div class="weui-form-preview">
        <div class="weui-form-preview__hd">
            <label class="weui-form-preview__label">您的号码</label>
            <em class="weui-form-preview__value">${squence}</em>
        </div>
        <div class="weui-form-preview__bd">
            <div class="weui-form-preview__item">
                <label class="weui-form-preview__label">当前进度</label>
                <span class="weui-form-preview__value">您前面还有<span id="pc">${count}</span>人在排队</span>
            </div>
            <div class="weui-form-preview__item">
                <label class="weui-form-preview__label">预计等待时间</label>
                <span class="weui-form-preview__value"><span id="time">${time}</span>分钟</span>
            </div>
        </div>
        <!--<div class="weui-form-preview__ft">-->
        <!--<button type="submit" class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:">刷新</button>-->
        <!--</div>-->
    </div>
</c:if>
<div class="weui-msg__opr-area" style="margin-top: 188px">
    <p class="weui-btn-area">
        <a id="show-loading" href="javascript:void(0);" class="weui-btn weui-btn_primary">刷新</a>
        <!--<a href="javascript:;" class="weui-btn weui-btn_default">辅助操作</a>-->
    </p>
</div>
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">正在刷新</p>
    </div>
</div>
</body>
<script>
    function loadingToast() {
        var $loadingToast = $('#loadingToast');
        if ($loadingToast.css('display') != 'none') return;
        $loadingToast.fadeIn(100);
    }

    $("#show-loading").click(function () {

        window.location.href="${pageContext.request.contextPath }/squenceInfoAction/querysquenceInfo";
        $("#loadingToast").fadeOut(100);
    })
</script>
</html>
