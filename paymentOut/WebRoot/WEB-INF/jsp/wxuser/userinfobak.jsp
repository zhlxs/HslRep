<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>个人信息</title>
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/register.css">
    <link rel="stylesheet" href="${ctx}/css/css/weui.css">
    <link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        body {
            background: #F8F8F8;
        }

        .pinch-zoom, .pinch-zoom img {
            width: 100%;
            -webkit-user-drag: none;
        }
    </style>
</head>
<body>
<div>
    <div class="hzdb-left" style="margin-top: .5rem">
        <span style="color: #8D8D8D;">个人信息</span>
    </div>
    <ul class="wrap_fix" onclick="getHtml('${ctx}/wxUserAction/usercenter')">
            <li style="height: inherit">
                <img src="${ctx}/img/aback.png" alt="" style="transform: scale(0.5);margin-left: 10px">
            </li>
            <li>
                <span style="color: #fff;vertical-align: -webkit-baseline-middle;margin-right: 15px">返回</span>
            </li>
        </ul>
</div>

<div>
    <div class="weui-cells weui-cells_form" style="margin-top: .5rem">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">姓名</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="username" type="text" readonly="readonly" value="">
            </div>
            <!--<div class="weui-cell__ft">-->
                <!--<img src="img/look.png" alt="" style="height: 20px;width: 20px;">-->
            <!--</div>-->
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">身份证号</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="userIdCard" type="text" readonly="readonly" value="">
            </div>
            <div class="weui-cell__ft" id="look">
                <img src="${ctx}/img/look.jpg" alt="" style="transform: scale(0.5)">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">联系电话</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="telNumber" type="text" readonly="readonly" value="">
            </div>
            <div class="weui-cell__ft" id="look1">
                <img src="${ctx}/img/look.jpg" alt="" style="transform: scale(0.5)">
            </div>
        </div>
    </div>

    <!--<div class="weui-btn-area">-->
        <!--<a style="background: #118EEA;: #118EEA" class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">确定</a>-->
    <!--</div>-->

</div>
</body>
<script type="text/javascript">
    function substring(str){
        if(typeof str == 'string'){
            var ruten = str.substring(3,7);
            return str.replace(ruten,'*****');
        }
    }
    function substring1(str){
        if(typeof str == 'string'){
            var ruten = str.substring(3,14);
            return str.replace(ruten,'*************');
        }
    }
    <%
	RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
	%>
    var $userId = "<%=weUserinfo.getId()%>";
    var ajaxData = 'userid=' + $userId;
    $.ajax({
        url:'${pageContext.request.contextPath}/wxUserAction/userInfoJson',
        type:'post',
        data:ajaxData,
        dataType:'json',
        success:function (result) {
            //console.log(result.stateValue);
            $("#username").val(result.stateValue.username);
            $("#userIdCard").val(substring1(result.stateValue.idCardNumber));
            $("#telNumber").val(substring(result.stateValue.phoneNumber));
            $("#look").click(function () {
                var bool = $("#userIdCard").val().indexOf("*");
                if(bool > 0){
                    $("#userIdCard").val(result.stateValue.idCardNumber);
                }else{
                    $("#userIdCard").val(substring1(result.stateValue.idCardNumber));
                }
            });
            $("#look1").click(function () {
                var bool = $("#telNumber").val().indexOf("*");
                if(bool > 0){
                    $("#telNumber").val(result.stateValue.phoneNumber);
                }else{
                    $("#telNumber").val(substring(result.stateValue.phoneNumber));
                }
            });
        }
    });
</script>
</html>