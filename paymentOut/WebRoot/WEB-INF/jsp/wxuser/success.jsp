<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <%-- <link rel="stylesheet" href="${ctx}/css/css/weui.css"> --%>
    <link rel="stylesheet" href="${ctx}/css/css/success.css">
    <title>预约成功</title>
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
</head>
<body>
<div class="head">
    <img src="${ctx}/img/banner.png" alt="" style="max-width: 100%;height: auto">
</div>
<div class="wrap">
    <div class="weui-msg" style="background: #FFFFFF;padding: 16px 0 16px">
        <div class="weui-msg__icon-area" style="margin-bottom: 10px">
            <i class="weui-icon-success weui-icon_msg" style="color: #22AD38"></i>
        </div>
        <h2 style="text-align: center;margin-bottom: 5px;color: #22AD38">预约成功</h2>
        <div id="loadingToast" style="display: none;">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast" style="top: 225px;">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">加载中请稍候...</p>
            </div>
        </div>
    </div>
    <div class="weui-msg__text-area" style="text-align: center;background: #F4FBF4;margin-bottom: 0">
        <ul class="information">
            <li>
                <ul class="clearfix box">
                    <li class="circle_wrap"></li>
                    <li class="info_left info">预约网点</li>
                    <li class="info_right" id="place">${deptName}</li>
                </ul>
            </li>
            <li>
                <ul class="clearfix box">
                    <li class="circle_wrap"></li>
                    <li class="info_left info">预约时间</li>
                    <li class="info_right" id="date">${day}</li>
                </ul>
            </li>
            <li>
                <ul class="clearfix box">
                    <li class="circle_wrap"></li>
                    <li class="info_left info">预约时间段</li>
                    <li class="info_right" id="time">${time}</li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="weui-msg__text-area" style="background: #fff;padding: 15px 25px;">
        <p class="weui-msg__desc" style="color: #E59E35">
            *请牢记预约信息，并按时到达预约网点办理业务，如未按预约时间段办理，需到窗口重新取号。
        </p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a style="background: #22AD38;" id="confirm" href="javascript:;" class="weui-btn weui-btn_primary">确定</a>
        </p>
    </div>
</div>

</body>
<script type="text/javascript">
    $("#confirm").click(function () {
    	//$.showLoading();
    	getHtml('${ctx}/wxUserAction/toIndex');
    	//$.hideLoading();
    })
</script>
</html>