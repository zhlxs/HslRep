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
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <style>
        .text1 {
            text-indent: 75px;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/transit.css">
</head>
<body>

<div>
    <img src="${pageContext.request.contextPath }/wx/img/banner.jpg" alt="" style="width: 100%;">
</div>
<div style="background: url('${pageContext.request.contextPath }/wx/img/background.jpg');background-size: 100% auto">
    <div class="page_hd" style="text-align: center;font: 6vw SimHei;color: #01307d;">
        <div>线上预约注意事项</div>
    </div>
    <div class="text text1" style="margin-top: -15px;">
        欢迎使用红谷滩户政便民微信预约申请功能。
    </div>
    <div class="text text1">
        红谷滩户政便民微信预约申请功能是红谷滩公安机关为广大群众提供的“数据多跑路、群众少跑腿”的便民新措举。您可以根据自身的实际情况选择预约办理业务的时间和地点，然后按照约定时间到达约定的办事大厅领取预约号，享受优先办理，可以节约您的办事时间、精力和成本。
    </div>
    <div class="text text1">
        感谢您对红谷滩公安的理解与支持！
    </div>
    <div class="text text1" style="font-weight: bold;">
        注意事项：
    </div>
    <p class="text text1" style="font-weight: bold;">（一）请先通过线上预审核再进行线上预约，节省办事时间、提高办事效率；</p>
    <p class="text text1" style="font-weight: bold;">（二）选择办事窗口时请根据您的实际情况进行选择，尽量避免高峰期排队人数过多造成的等待；</p>
    <p class="text text1" style="font-weight: bold;">（三）此预约信息仅限所填预约身份证号本人办理业务，并且一天只能成功预约一次；</p>
    <p class="text text1" style="font-weight: bold;">（四）请在预约的时间段内携带相关材料准时到达办事现场，否则预约号过期的作废处理，迟到的将按照现场办事流程处理</p>


    <div class="page__bd page__bd_spacing" style="margin-top: 40px;">
        <%--<a href="newmap.html" class="weui-btn weui-btn_primary art">开始预约</a>--%>
        <c:if test="${sessionScope.openid==null}">

            <a href="${pageContext.request.contextPath }/wxAction/getCode?deptid=47&redirect_uri=appointmeAction/getOpenId&appid=wx6d4d594ef75edec4&secret=5827020e5fd8d8a037afb09232a29813"
               class="weui-btn weui-btn_primary art">开始预约</a>
        </c:if>
        <c:if test="${sessionScope.openid!=null}">
            <a href="${pageContext.request.contextPath }/appointmeAction/getOpenId"
               class="weui-btn weui-btn_primary art">开始预约</a>
        </c:if>
    </div>
    <div style="height:20px;"></div>
</div>
<div class="weui-footer">
    <p style="color: #736666;font-size: 35px;height: 30px;text-align: center;">
        版权所有©红谷滩公安局<br>
        技术支持 &nbsp;&nbsp;江西科泰华软件有限公司
    </p>
</div>
</body>
</html>
