<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>/wx">

    <title>在线预约</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <style>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div id="loadingToast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">加载中请稍候...</p>
    </div>
</div>
<div style="position: relative">
    <img src="${pageContext.request.contextPath }/wx/img/banner1.png" alt="" style="width: 100%;">
    <div style="position: absolute;top: 25%;left: 25%;">
        <span id="title" class="title">${deptname}</span>
    </div>
</div>
<div class="weui-msg" style="padding-top: 10px;background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto;text-align: inherit">
    <div class="page_hd" style="text-align: center;font: 5.5vw SimHei;color: #01307d;">
        <div>线上预约注意事项</div>
    </div>
    <div class="text text1" style="margin-top: -15px;">
        欢迎使用微信预约申请功能。
    </div>
    <div class="text text1">
       微信预约申请功能是为广大群众提供的“数据多跑路、群众少跑腿”的便民新措举。您可以根据自身的实际情况选择预约办理业务的时间和地点，然后按照约定时间到达约定的办事大厅领取预约号，享受优先办理，可以节约您的办事时间、精力和成本。
    </div>
    <div class="text text1">
        感谢您的理解与支持！
    </div>
    <div class="text text1" style="font-weight: bold;">
        注意事项：
    </div>
    <p class="text text1" style="font-weight: bold;">（一）请先通过线上预审核再进行线上预约，节省办事时间、提高办事效率；</p>
    <p class="text text1" style="font-weight: bold;">（二）选择办事窗口时请根据您的实际情况进行选择，尽量避免高峰期排队人数过多造成的等待；</p>
    <p class="text text1" style="font-weight: bold;">（三）此预约信息仅限所填预约身份证号本人办理业务，并且一天只能成功预约一次；</p>
    <p class="text text1" style="font-weight: bold;">（四）请在预约的时间段内携带相关材料准时到达办事现场，否则预约号过期的作废处理，迟到的将按照现场办事流程处理</p>

    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <c:if test="${sessionScope.openid==null}">

                <a id="start" href="${pageContext.request.contextPath }/wxAction/getCode?redirect_uri=appointmeAction/getOpenId"
                   class="weui-btn weui-btn_primary">开始预约</a>
            </c:if>
            <c:if test="${sessionScope.openid!=null}">
                <a id="start" href="${pageContext.request.contextPath }/appointmeAction/getOpenId"
                   class="weui-btn weui-btn_primary">开始预约</a>
            </c:if>
        </p>
    </div>
    <div class="weui-msg__extra-area" style="position: inherit;margin: 0">
        <p class="weui-footer__links">
            <a href="javascript:;" class="weui-footer__link">版权所有©${deptname}</a>
        </p>
        <p class="weui-footer__text">技术支持 © 江西科泰华软件有限公司</p>
    </div>
</div>
</body>
</html>
