<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/popups.js"></script>
    <style>
        body {
            background: #f2f2f2;
        }

        .page__bd_spacing {
            padding: 0 15px;
        }

        .weui-cell:before, .weui-cells:before {
            border: none;
        }

        .input_control {
            margin: 0 auto;
            width: 90%;
            position: relative;
            border: 1px solid #dadada;
            /*padding: 15px 0;*/
            height: 50px;
            border-radius: 5px;
        }

        .weui-input {
            color: #666666;
        }

        .show-list {
            width: 80%;
            margin: 0 auto;
        }

        .show-list li {
            height: 1rem;
            font-size: .3rem;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #dcdcdc;
        }

        .weui-mask_transparent {
            background: #000;
            opacity: 0.5;
        }

        .content {
            text-align: center;
        }

        #yes_btn {
            color: #1AAD19;
        }

        .input_control > img {
            width: 25px;
            height: 25px;
            right: 15px;
            top: 15px;
            position: absolute;
        }

        .form_input {
            width: 100%;
            outline: none;
            border: none;
            height: 50px;
            background: #f2f2f2;
            font-size: 20px;
            padding-left: 10px;
        }

        .weui-label {
            font-weight: bold
        }
    </style>
    <script type="text/javascript">
        setSize();
        addEventListener('resize', setSize);

        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth / 750 * 100 + 'px';
        }
    </script>
</head>
<body>


<div class="weui-cells" style="margin-top: 0;">


    <div class="weui-cell weui-cell_switch" style="padding: 15px 15px;">
        <div class="weui-cell__bd">
            <img src="${pageContext.request.contextPath }/wx/img/title.jpg" alt=""
                 style="width: 140px;height: 20px;float: left;margin-left: 5px">
        </div>
        <div class="weui-cell__ft"></div>
    </div>

    <div class="weui-cell weui-cell_switch" style="padding: 0">

        <div class="weui-cell__bd">
            <div class="input_control">
                <input type="text" class="form_input" id="useridcard"/>
                <img id="art" src="${pageContext.request.contextPath }/wx/img/search.png" alt="">
            </div>
            <p style="margin-top: 10px;font-size: 14px;color: #a2a2a2;margin-bottom: 15px">
                <span style="margin-left: 20px">搜索条件：身份证号码</span>
            </p>
        </div>
        <div class="weui-cell__ft"></div>
    </div>
</div>


<c:forEach items="${list}" var="item">
    <div id="info" class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约信息</label>
            </div>
            <input type="hidden" value="${item.id}" class="appid">
            <div class="weui-cell__bd" class="cancle">

                <span style="color: #fe4a49;font-size: 18px;float: right">取消预约</span>
                <img src="${pageContext.request.contextPath }/wx/img/cancel.png" alt=""
                     style="margin-right:3px;width: 22px;height: 22px;float:right;">
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input">${item.name}</span>
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">办理网点：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input">${item.dept.deptName}</span>
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约日期：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input">${item.appointmenttime}</span>
            </div>
        </div>

        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约时段：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input">${item.timeQuan.time}</span>

            </div>
        </div>
    </div>
</c:forEach>
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">加载中请稍候...</p>
    </div>
</div>
</body>
<script>
    function isCardNo(card) {
        // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (reg.test(card) === false) {
            alert("身份证输入不合法，请重新输入");
            $("#useridcard").val('');
            return false;
        } else {
            return true;
        }
    }

    //    console.log(isCardNo($("#useridcard")));
    //    let IDcard = $("#useridcard").val();
    $("#art").click(function () {
//        console.log(IDcard);
//        console.log($("#useridcard").val());
        if ($("#useridcard").val() != "") {
//            console.log(isCardNo($("#useridcard").val()));
            if (isCardNo($("#useridcard").val()) != false) {
                $("#info").css("display", "block");
                window.location.href = "${pageContext.request.contextPath }/appointmeAction/queryAppointmeInfo?cardnumber=" + $("#useridcard").val();
            } else {
//                alert("身份证号码输入错误，请重新输入")
                return false;
            }
        } else {
            alert("请输入身份证号");
        }
    });
    $(".cancle").click(function () {
        var that = $(this);
        jqalert({
            title: '提示',
            content: '是否确定取消此次预约？',
            yesfn: function () {
                $.post("${pageContext.request.contextPath }/appointmeAction/cancleAppointme", {"id": that.prev().val()}, function (data) {
                    jqtoast(
                        data.stateMsg
                    );
                    window.location.reload();
                });
            },
            notext: '取消'
        })
    })
</script>
</html>
