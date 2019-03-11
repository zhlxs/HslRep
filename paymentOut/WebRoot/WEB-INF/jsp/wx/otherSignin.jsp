<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>预约信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <style>
        .page__bd_spacing {
            padding: 0 15px;
        }

        #cancel {
            margin-left: 25px;
            background-color: red;
            color: white;
            border-radius: 5px;
            width: 70px;
            height: 25px;
            display: inline-block;
        }
    </style>
</head>
<body>


<div class="weui-cells" style="margin-top: 0;">


    <div class="weui-cell weui-cell_switch" style="padding: 15px 15px;">
        <div class="weui-cell__bd">
            <img src="${pageContext.request.contextPath }/wx/img/search.jpg" alt="" style="width: 25px;height: 25px;float: left;">
            <span style="margin-left: 12px">他人代办身份证号核验</span>
        </div>
        <div class="weui-cell__ft"></div>
    </div>

    <div class="weui-cell weui-cell_switch" style="padding: 15px 15px;">

        <div class="weui-cell__bd">
            <div>
                <p>
                    <span style="color:#6d6d6d;" class="wbzt">身份证号：</span>
                    <input style="border: none;outline: none;background-color: transparent;font-size: inherit;" id="useridcard"
                           type="text" placeholder="请输入身份证号" value="">
                <p>
            </div>
        </div>
        <div class="weui-cell__ft"></div>
    </div>

    <div class="page__bd page__bd_spacing" style="margin-top: 30px;">
        <a href="javascript:void(0);" class="weui-btn weui-btn_primary art">获取办理号</a>
    </div>
    <div class="weui-footer" style="height: 30px;"></div>
    <div id="info" class="weui-cell weui-cell_switch" style="padding: 15px 15px;display: none">
        <div class="weui-cell__ft"></div>
    </div>
</div>
</body>
<script>
    $(".art").click(function () {
        function isCardNo(card) {
            // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
            let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if (reg.test(card) === false) {
//                alert("身份证输入不合法，请重新输入");
                $("#useridcard").val('');
                return false;
            } else {
                return true;
            }
        }

        if (isCardNo($("#useridcard").val()) == false) {
            alert("请输入正确的身份证号码");
            return false;
        } else {
            window.location.href = "${pageContext.request.contextPath }/squenceInfoAction/getAppSquence?cardnumber=" + $("#useridcard").val() + "&code=${code}";
        }

    })
</script>
</html>
</body>
</html>
