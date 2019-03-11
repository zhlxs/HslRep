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
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>在线预约</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/j-select.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/jquery-weui.less">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/reveal.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-nicescroll.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery-jSelect.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/jquery.reveal.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/My97DatePicker/WdatePicker.js"></script>
    <style>
        body {
            background: #F8F8F8;
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

        .form {
            width: 200px;
        }
    </style>
</head>
<body>
<input type="hidden" value="${pageContext.request.contextPath }" id="path"/>
<form action="${pageContext.request.contextPath }/appointmeAction/saveAppointmeInfo" id="appointemeForm" method="post">
    <input type="hidden" id="openid" value="" name="openid">
    <article class="r-m301">
        <div class="header">
            申请预约
        </div>
        <div class="weui-cells" style="overflow: visible">
            <a class="weui-cell weui-cell_access" href="javascript:void(0);">
                <div class="weui-cell__bd" style="padding: 5px 3px;">
                    <span>办证网点</span>
                </div>
                <div class="weui-cell__hd">
                    <img src="${pageContext.request.contextPath }/wx/img/position.jpg" alt="" style="width:20px;margin-right:5px">
                    <span>红谷滩派出所户政窗口</span>
                    <input type="hidden" id="deptid" value="47" name="deptid">
                </div>
            </a>

            <!--<a class="weui-cell weui-cell_access" href="javascript:void(0);">-->
            <div class="weui-cell">
                <div class="weui-cell__bd" style="padding: 5px 3px;">
                    <span>办理日期</span>
                </div>
                <div class="weui-cell__hd">
                    <div>
                        <!--<input id="txtEndDate" style="font-size: 16px;width:170px;padding:7px 10px;border:1px solid #ccc;" />-->
                        <input id="txtEndDate" class="Wdate" type="text"
                               onClick="WdatePicker({minDate:'%y-%M-{%d+1}',disabledDays:[0,6]})"
                               style="font-size: 16px;width:170px;padding:7px 10px;border:1px solid #ccc;">
                    </div>
                </div>
            </div>

            <!--</a>-->
        </div>
        <div class="header">
            办理时间段
        </div>
        <div class="weui-cells" style="overflow: visible">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">预约时间段</label>
                </div>
                <div class="weui-cell__bd">
                    <div class="form">
                        <select id="time" class="select" name="timeQuantum">
                            <option value="请选择办理时间段"></option>
                            <option value="1">09:00-10:00</option>
                            <option value="2">10:00-11:00</option>
                            <option value="3">11:00-12:00</option>
                            <option value="4">14:00-15:00</option>
                            <option value="5">15:00-16:00</option>
                            <option value="6">16:00-17:00</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">身份证号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="useridcard" type="text" placeholder="请输入身份证号码" value="" name="cardnumber">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">姓名：</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="name" type="text" placeholder="请输入姓名" value="" name="name">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" id="telnumber" type="text" placeholder="请输入手机号码" value="" name="phone">
                </div>
            </div>
        </div>
    </article>
</form>
<div class="weui-btn-area" style="margin: 4.176471em 15px 0.3em;">
    <a data-reveal-id="myModal" data-animation="fade" class="weui-btn weui-btn_primary" href="javascript:void(0);" id="showTooltips">申请预约</a>
</div>
<div id="myModel" class="reveal-modal" style="opacity: 0.9;">
    <h1>当前时间段预约人数为：<span style="color: red;font-weight: bold;" id="count">8</span>人</h1>
    <h1>还可以预约人数为：<span style="color: red;font-weight: bold;" id="count1">8</span>人</h1>
    <h1>是否继续预约？</h1>
    <p class="weui-btn-area">
        <a href="javascript:;" class="weui-btn weui-btn_primary" id="weui-btn_primary">确认</a>
        <a href="javascript:;" class="weui-btn weui-btn_default">取消</a>
    </p>
    <a class="close-reveal-modal">&#215;</a>
</div>
</body>
<script type="text/javascript">
    $(function () {

        $(".select").jSelect();
        $(".close-reveal-modal").click(function () {
            $("#myModel").css("visibility", "hidden");
        });
        $(".weui-btn_default").click(function () {
            $("#myModel").css("visibility", "hidden");
        });
        $("#showTooltips").click(function () {
                let day = $("#day").find("option:selected").text();
                var time = $("#time").find("option:selected").val();
                var phone = $("#telnumber").val();
                var cardnumber = $("#useridcard").val();
                var deptid = $("#deptid").val();
                var name = $("#name").val();
                var flag = true;
                if (day == "") {
                    alert("请选择办理日期");
                    flag = false;
                } else if (time == "" && day != "") {
                    alert("请选择办理的时间段");
                    flag = false;
                } else if (phone == "") {
                    alert("请填写电话号码");
                    flag = false;
                } else if (cardnumber == "") {
                    alert("请填写身份证号码");
                    flag = false;
                } else if (deptid == "") {
                    alert("请选择网点");
                    flag = false;
                } else if (name == "") {
                    alert("请填写姓名");
                    flag = false;
                } else {
                    var parm = {
                        "day": day,
                        "time": time
                    };
                    $.post("${pageContext.request.contextPath }/appointmeAction/getAppointmeCount", parm, function (data) {
                        // alert(data.stateValue);
                        $("#count").text(data.stateValue);
                        $("#count1").text(30 - Number(data.stateValue));
                    });
                    $("#myModel").css("visibility", "visible");

                }


            }
        );
        $("#weui-btn_primary").click(function () {
            //如果信息都填写了  则发送请求存储预约信息

            $.post("${pageContext.request.contextPath }/wxAction/getCode", {
                "deptid": 47,
                "appid": "",
                "secret": "",
                "redirect_uri": "wxAction/getOpenId"
            }, function (data) {
                alert(data.stateValue);
                $("#openid").val(data.stateValue);
                //请求openid 成功 则发送请求存储数据
                $("#appointemeForm").submit();
            });
        });
    })
</script>
</html>
