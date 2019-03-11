<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>满意度调查</title>
    <link rel="stylesheet" href="${ctx}/css/weui.css">
    <script src="${ctx}/js/jquery-1.8.3.min.js"></script>
    <style>
        body {
            background: #f2f2f2;
        }

        td {
            display: block;
        }

        form {
            margin-left: 10px;
        }

        table {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        td {
            margin-top: 5px;
        }
    </style>
</head>
<body>
<form id="form1" name="form1" method="post" action="${ctx}/wx/userAppraiseAction/saveAnswer">
    <c:forEach items="${list}" var="question" varStatus="i">
        <input type="hidden" name="testid" class="testid" value="${question.id}">
        ${question.title}
        <table>
            <tr>
                <c:forEach items="${question.answers}" var="item" varStatus="sta">
                    <td>
                        <label>
                            <input type="radio" class="answers" name="answers${i.index+1}" value="${item}"
                                   id="${sta.index+1}"/>${item}
                        </label>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </c:forEach>
</form>
<div class="weui-msg__opr-area">
    <p class="weui-btn-area">
        <a href="javaScript:;" class="weui-btn weui-btn_primary" id="gosub" style="margin: 4.176471em 15px 0.3em;">提交</a>
    </p>
</div>
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent" style="background: #000; opacity: 0.5"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>
</body>
<script>
    $("#gosub").click(function () {
        $("#loadingToast").css("display", "block");
       var testid = [];
        var list = document.getElementsByName("testid");
        for (var k = 0; k < list.length; k++) {
            testid.push(list[k].value);
        }
        var  obj = $('input[type=radio]:checked');
         var checkVal = [];
        for (var i = 0; i < obj.length; i++) {
            checkVal.push(obj[i].value);
        }
        //表单异步提交
        var ajaxData = "testid=" + testid;
        ajaxData += "&answers=" + checkVal;
        console.log(ajaxData);
        $.ajax({
            type: "post",
            url: "${ctx}/wx/userAppraiseAction/saveAnswer",
            data: ajaxData,
            dataType: "json",
            success: function (data) {
                if (data.stateType == 0) {
                    window.location.href = "${ctx}/wx/appraiseDetailAction/finish";
                }
            }
        })
    });
</script>
</html>