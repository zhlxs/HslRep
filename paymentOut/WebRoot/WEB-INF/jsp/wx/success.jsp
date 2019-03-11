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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <title>预约成功</title>
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area" style="text-align: center">
        <h2 class="weui-msg__title" style="text-align: center">预约成功</h2>
        <hr style="width: 100%;margin: 15px auto;">
        <p class="weui-msg__desc" style="overflow: hidden">
            <span style="float: left">预约网点</span>
            <span id="place" style="float: right">${dept.deptName}</span>
        </p>
        <p class="weui-msg__desc" style="overflow: hidden">
            <span style="float: left">预约时间</span>
            <span id="date" style="float: right">${appointmeInfo.appointmenttime}</span>
        </p>
        <p class="weui-msg__desc" style="overflow: hidden">
            <span style="float: left">预约时间段</span>
            <span id="time" style="float: right">${appointmeInfo.timeQuan.time}</span>
        </p>
    </div>
    <hr style="width: 90%;margin: 0 auto;">
    <div class="weui-msg__text-area" style="margin-top: 25px;">
        <p class="weui-msg__desc">
            请牢记预约信息，并按时到达预约网点办理业务，如未按预约时间段办理，需到窗口重新取号。
        </p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="${pageContext.request.contextPath }/appointmeAction/appointmeIndex?deptid=${sessionScope.deptid}"
               class="weui-btn weui-btn_primary">确定</a>
        </p>
    </div>
    <!--<div class="weui-msg__extra-area">-->
    <!--<div class="weui-footer">-->
    <!--<p class="weui-footer__links">-->
    <!--<a href="javascript:void(0);" class="weui-footer__link">版权所有©XXX公安局</a>-->
    <!--</p>-->
    <!--<p class="weui-footer__text">技术支持  江西科泰华软件有限公司</p>-->
    <!--</div>-->
    <!--</div>-->
</div>
</body>
</html>
