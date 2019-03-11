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
    <%-- <link rel="stylesheet" href="${ctx}/css/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/css/weui.min.css"> --%>
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        body{
            background: #f2f2f2;
        }
        .line{
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div>
    <div style="margin-top: 1em;margin-left: 15px">
        <span style="color: #8D8D8D;">队列信息</span>
    </div>
    <ul class="wrap_fix" onclick="getHtml('${ctx}/wxUserAction/toIndex')">
        <li style="height: inherit">
            <img src="${ctx}/img/aback.png" alt="" style="transform: scale(0.5);margin-left: 10px">
        </li>
        <li>
            <span style="color: #fff;vertical-align: -webkit-baseline-middle;margin-right: 15px">返回</span>
        </li>
    </ul>
</div>

<div id="info">
    <!-- <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="userName">张凯</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约业务：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="business">机动车注册登记</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约日期：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="date">2018-07-02</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">预约时段：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input" id="time">09:00-10:00</span>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">当前队列：</label>
            </div>
            <div class="weui-cell__bd">
                <span class="weui-input">
                    <span>面前还有</span>
                    <span id="line" class="line">4</span>
                    <span>人等待</span>
                </span>
            </div>
        </div>
    </div> -->
</div>
</body>
<script type="text/javascript">
    window.addEventListener("popstate", function (e) {
        //alert("我监听到了浏览器的返回按钮事件啦");
        // 根据自己的需求实现自己的功能
        getHtml('${ctx}/wxUserAction/toIndex');
    });
    //$.showLoading();
        <%
		RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
		%>
    var $name = "<%=weUserinfo.getUsername()%>";
    var $card = "<%=weUserinfo.getIdCardNumber()%>";
    //var $phone = "<%=weUserinfo.getPhoneNumber()%>";
    var isReg = false;
    var $openId = "<%=weUserinfo.getWxOpenId()%>";
    var ajaxData = 'openid=' + $openId;
    //判断是否注册
    if($name == 'null'||$card == 'null'){
        isReg = true;
        $.hideLoading();
        $.confirm({
            title:'提示',
            text:'您尚未注册，无法进行查询，点击确定前往注册',
            onOK:function () {
                //跳转到注册页面
                getHtml('${ctx}/wxUserAction/goRegister');
            },
            onCancel:function(){
                return false;
            }
        });
    }else{
        $.ajax({
            url:'${ctx}/wxUserAction/SequenceInfoJson',
            type:'post',
            data : ajaxData,
            dataType:'json',
            success:function(data){
                if(data.length == 0){
                    $("#info").append(
                        "<p style='margin-top: 30px;color: red;text-align: center;font-weight: bold'>暂无队列信息</p>"+
                        "<div style='height: 30px;'></div>"
                    );
                    $.hideLoading();
                }else{
//                console.log(data);
                    $.each(data,function(index,value){
                        if(value.status == 1){
                            $("#info").append(
                                "<div class='weui-cells weui-cells_form'>" +
                                "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>" +
                                "<span class='weui-input' id='userName'>" + value['name'] + "</span>" +
                                "</div>" +
                                "</div>" +
                                "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>预约业务：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>" +
                                "<span class='weui-input' id='business'>" + value['business'] + "</span>" +
                                "</div>" +
                                "</div>" +
                                "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>预约日期：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>"　+
                                "<span class='weui-input' id='date'>" + value['day'] + "</span>" +
                                "</div>" +
                                "</div>" +
                                "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>预约时段：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>" +
                                "<span class='weui-input' id='time'>" + value['timeInter'] + "</span>" +
                                "</div>" +
                                "</div>" +
                                "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>状态：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>" +
                                "<span class='weui-input' id='status'>" +
                                "<span>前面还有</span>" +
                                "<span id='line' class='line'>" + value['peopleNumber'] + "</span>" +
                                "<span>人等待</span>" +
                                "</span>" +
                                "</div>" +
                                "</div>" +
                                "</div>"
                            );
                        }else if(value.status == 2){
                            $("#status").empty();
                            $("#status").append("<span>当前正在办理</span>")
                        }else if(value.status == 3){
                            $("#status").empty();
                            $("#status").append("<span>已过号</span>")
                        }else if(value.status == 4){
                            $("#status").empty();
                            $("#status").append("<span>该业务已办结</span>")
                        }
                    });
                    $.hideLoading();
                }
            }
        });
    }
</script>
</html>