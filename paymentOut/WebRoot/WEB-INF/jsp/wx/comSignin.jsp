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
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>在线预约</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/j-select.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reveal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/style.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/popups.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-nicescroll.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-jSelect.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery.reveal.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        setSize();
        addEventListener('resize', setSize);

        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth / 750 * 100 + 'px';
        }
    </script>
    <style>
        body {
            background: #F8F8F8;
        }

        #yes_btn {
            color: #1AAD19;
        }

        .header {
            margin-top: 1.0em;
            /*padding: 10px 15px;*/
            line-height: 1.41176471;
            font-size: 20px;
            overflow: hidden;
            /*position: relative;*/
            text-indent: 15px;
        }

        .weui-mask_transparent {
            background: #000;
            opacity: 0.5;
        }

        .form {
            width: 192px;
            height: 36px;
            float: right;
            margin-right: 4px;
        }

        .current_select {
            border-radius: 5px;
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

        .content {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="loadingToast" style="display: block;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast" style="top: 225px;">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">加载中请稍候...</p>
    </div>
</div>
<input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
<form action="${pageContext.request.contextPath }/squenceInfoAction/getcommSquence" id="appointemeForm" method="post">
    <input type="hidden" id="openid" value="${sessionScope.openid}" name="openid">
    <article class="r-m301">
        <div class="header">
            个人信息
        </div>
        <div class="weui-cells" style="overflow: visible">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">姓名：</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="name" type="text" placeholder="请输入姓名" value="" name="name">
                </div>
            </div>

        </div>
    </article>
</form>
<div class="weui-btn-area" style="margin: 4.176471em 15px 0.3em;">
    <a data-reveal-id="myModal" data-animation="fade" class="weui-btn weui-btn_primary" href="javascript:void(0);" id="showTooltips">立即取号</a>
</div>

</body>
<script type="text/javascript">
    $(function () {
        $("#loadingToast").css("display", "none");
        function isNull(object) {
            if (object == null || typeof object == "undefined") {
                return true;
            }
            return false;
        }

        $("#showTooltips").click(function () {
            var name = $("#name").val();
            if (name == "") {
                alert("请填写姓名");
            } else {
                $("#appointemeForm").submit();
            }
        });
    })
</script>
</html>
