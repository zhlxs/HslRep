<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'test.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script>
    $(function () {
        $("#whatfuck").click(function () {
            var name = $("#name").val();
            var cardnumber = $("#cardnumber").val();
            var phone = $("#phone").val();
            var deptid = $("#deptid").val();
            var appointmenttime = $("#appointmenttime").val();
            var parm = {
                "name": name,
                "cardnumber": cardnumber,
                "phone": phone,
                "deptid": deptid,
                "appointmenttime": appointmenttime,
                "openid": ""
            };
            $.post("wxAction/getCode", {"deptid": 47, "appid": "", "secret": "", "redirect_uri": "wxAction/getOpenId"}, function (data) {
                parm.openid = data.stateValue;
                $.post("appointmeAction/saveAppointmeInfo", parm, function (data) {
                    alert(data.stateMsg);
                    window.location.href = "appointmeSuccess.jsp"
                });
            });
        })
    })
</script>
<body>
This is my test page. <br>
姓名<input type="text" id="name"><br>
身份证<input type="text" id="cardnumber"><br>
电话<input type="text" id="phone"><br>
部门<input type="text" id="deptid"><br>
预约时间<input type="text" id="appointmenttime">
<button id="whatfuck">点我预约</button>
<button id="imin">点我签到</button>
<button id="nextfuck">点我获取下一个</button>
<button id="again">点我重复</button>

</body>
</html>
