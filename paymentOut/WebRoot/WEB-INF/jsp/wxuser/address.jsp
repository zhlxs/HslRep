<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网上车管所</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <%-- <link rel="stylesheet" href="${ctx}/css/css/jquery-weui.css">
    <link rel="stylesheet" href="${ctx}/css/css/weui.css"> --%>
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        body {
            background: #f2f2f2;
        }
    </style>
</head>
<body>
<div>
    <div style="margin-top: 1em;margin-left: 15px">
        <span style="color: #8D8D8D;">网点列表</span>
    </div>
    <ul class="wrap_fix" onclick="getHtml('${ctx}/wxUserAction/toIndex')">
        <li style="height: inherit">
            <img src="${ctx}/img/aback.png" alt="" style="transform: scale(0.5);margin-left: 10px;margin-top: -2px">
        </li>
        <li>
            <span style="color: #fff;vertical-align: -webkit-baseline-middle;margin-right: 15px">返回</span>
        </li>
    </ul>
</div>
<div class="weui-cells" id="place_list">
    <!-- <a class="weui-cell weui-cell_access">
        <div class="weui-cell__bd">
            <p>南昌车管所</p>
        </div>
        <div class="weui-cell__ft"></div>
    </a>
    <a id="2" class="weui-cell weui-cell_access" onclick="sAppoint(this)">
        <div class="weui-cell__bd">
            <p>红谷滩车管所</p>
        </div>
        <div class="weui-cell__ft"></div>
    </a> -->
</div>
</body>
<script type="text/javascript">
    function sAppoint(object) {
        //console.log($(object).attr("id"));
        //console.log($(object).find("p").text());
        //跳转直接预约页面并拼接部门名称和部门ID
        var departmentId = $(object).attr("id");
        var departmentName = $(object).find("p").text();
        getHtml("${ctx}/wx/businessAction/directAccess?deptid=" + departmentId + "&deptname=" + departmentName);
    }
    window.addEventListener("popstate", function (e) {
    //alert("我监听到了浏览器的返回按钮事件啦");
    // 根据自己的需求实现自己的功能
        getHtml('${ctx}/wxUserAction/toIndex');
    });
    //$.showLoading();
    $.ajax({
        url:'${ctx}/deptAddressAction/getDeptAddress',
        type:'post',
        dataType:'json',
        success:function (res) {
            $.each(res,function (index,obj) {
                $("#place_list").append(
                    "<a id='"+obj['deptid']+"' class='weui-cell weui-cell_access' onclick='sAppoint(this)'>" +
                        "<div class='weui-cell__bd'>" +
                            "<p>" +obj['deptname']+ "</p>" +
                        "</div>" +
                        "<div class='weui-cell__ft'></div>" +
                    "</a>"
                )
            });
//            function sAppoint(object) {
//                console.log($(object).attr("id"));
//                console.log($(object).find("p").text());
//            }
        }
    });
</script>
</html>