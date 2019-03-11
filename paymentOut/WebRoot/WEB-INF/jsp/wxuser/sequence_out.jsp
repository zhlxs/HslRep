<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>网上车管所</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/css/reset.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/css/pickout.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/css/animate.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/css/weui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/search.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/order.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/jquery-weui.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/js/jquery-2.1.4.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/js/jquery-weui.min.js"></script>
    <style>
        .pinch-zoom,.pinch-zoom img {
            width: 100%;
            -webkit-user-drag: none;
        }
        .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon>i, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__label {
            color: #00a0e9;
        }
        body{
            background: #f2f2f2;
        }
        .line{
            color: red;
            font-weight: bold;
        }
        .weui-dialog__bd {
            line-height: 40px;
        }

        .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon > i, .weui-tabbar__item.weui-bar__item_on .weui-tabbar__label {
            color: #00a0e9;
        }

        * {
            padding: 0;
            margin: 0;
        }

        .content {
            margin: 50px auto 0;
            width: 300px;
            min-height: 500px;
        }

        .form-group {
            width: 100%;
            float: left;
            margin: 5px 0;
        }

        label {
            /*margin-bottom: 10px;*/
            float: left;
        }

        .field-input, select {
            width: calc(100% - 20px);
            float: left;
            padding: 10px;
            font-family: inherit;
        }
        .hide{
            display: none;
        }
        .button {
            background: #00A1E9;
            margin-left: 5px;
            margin-right: 2.5px;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            $('.weui-tabbar__item').on(
                'click',
                function() {
                    $(this).addClass('weui-bar__item_on').siblings(
                        '.weui-bar__item_on').removeClass(
                        'weui-bar__item_on');
                });
            $(".foot a").click(function(e) {
                e.preventDefault();//取消默认事件
                e.stopPropagation();//取消冒泡事件
                $(".foot a").removeClass("selected");
                $(this).addClass("selected");
            });
            $("#close_qrimg").click(function() {
                $("#Div2").hide();
            });
            $("#tpdialog").click(function() {
                $("#tpdialog").hide();
                $("#photo_demo_close").hide();
            });
            function getUrlParam(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = window.location.search.substr(1).match(reg); //匹配目标参数
                if (r != null)
                    return unescape(r[2]);
                return null; //返回参数值
            }
            $("#drr").click(function() {
                $("#sy").hide();
                $("#sy1").show();
                $("#sy2").show();
                $("#sy3").show();
                $("#sy-dj").show();
                $("#sy1-dj").hide();
                $("#sy2-dj").hide();
                $("#sy3-dj").hide();
                getHtml('${pageContext.request.contextPath}/wxUserAction/toIndex');
            });
            $("#drr1").click(function() {
                $("#sy").show();
                $("#sy1").hide();
                $("#sy2").show();
                $("#sy3").show();
                $("#sy-dj").hide();
                $("#sy1-dj").show();
                $("#sy2-dj").hide();
                $("#sy3-dj").hide();
                getHtml('${pageContext.request.contextPath}/wx/appointInfoAction/appointInfoView');
            });
            //            $("#drr2").click(function () {
            //                $("#sy").show();
            //                $("#sy1").show();
            //                $("#sy2").hide();
            //                $("#sy3").show();
            //                $("#sy-dj").hide();
            //                $("#sy1-dj").hide();
            //                $("#sy2-dj").show();
            //                $("#sy3-dj").hide();
            //                getHtml('/view/index/newlist.html');
            //
            //            });
            $("#drr3").click(function() {
                $("#sy").show();
                $("#sy1").show();
                $("#sy2").show();
                $("#sy3").hide();
                $("#sy-dj").hide();
                $("#sy1-dj").hide();
                $("#sy2-dj").hide();
                $("#sy3-dj").show();
                getHtml('${pageContext.request.contextPath}/wxUserAction/usercenter');
            });
//            $("#drr").click();
        });
    </script>
</head>
<body>
<div id="out">
    <div class="wrap">
        <div class="wrap_left">
            <img src="${ctx}/img/tips.png" alt="" style="transform: scale(0.8);vertical-align: middle">
            <span style="font-size: 18px;vertical-align: middle">温馨提示</span>
        </div>
        <!--<div class="wrap_right" onclick="jumpNotice()">-->
        <!--<img src="img/question.png" alt="" style="left: 5px;">-->
        <!--<img src="img/notice.png" alt="" style="right: 8px;">-->
        <!--</div>-->
        <ul class="wrap_fix" id="line">
            <li style="height: inherit">
                <img src="${ctx}/img/aback.png" alt="" style="transform: scale(0.5);margin-left: 10px;margin-top: -2px">
            </li>
            <li>
                <span style="color: #fff;vertical-align: -webkit-baseline-middle;margin-right: 15px">返回</span>
            </li>
        </ul>
        <p class="text">
            数据需交互至公安网服务器，可刷新后查看；为了不耽误您办理业务，预约办理时请尽量提前预约
        </p>
    </div>

    <div class="weui-cells" id="place_list" style="margin-top: 0">
        <div style="margin-top: 1em;margin-left: 15px">
            <span style="color: #8D8D8D;">请选择办理网点</span>
        </div>
        <div id="optionWrap" style="margin-top: 1rem">
            <!--<a id="1" class="weui-cell weui-cell_access" onclick="sAppoint(this)">-->
            <!--<div class="weui-cell__bd">-->
            <!--<p>南昌车管所</p>-->
            <!--</div>-->
            <!--<div class="weui-cell__ft"></div>-->
            <!--</a>-->
            <!--<a id="2" class="weui-cell weui-cell_access" onclick="sAppoint(this)">-->
            <!--<div class="weui-cell__bd">-->
            <!--<p>红谷滩车管所</p>-->
            <!--</div>-->
            <!--<div class="weui-cell__ft"></div>-->
            <!--</a>-->
        </div>
    </div>


    <div id="bw" style="display: none">
        <div id="info" class="weui-cells weui-cells_form" style="display: block;margin-top: 1em">

            <!--<div class="weui-cell">-->
                <!--<div class="weui-cell__hd">-->
                    <!--<label class="weui-label">队&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;列：</label>-->
                <!--</div>-->
                <!--<div class="weui-cell__bd">-->
                    <!--<span class="weui-input" id="bType">综合业务</span>-->
                <!--</div>-->
            <!--</div>-->

            <!--<div class="weui-cell">-->
                <!--<div class="weui-cell__hd">-->
                    <!--<label class="weui-label">窗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;口：</label>-->
                <!--</div>-->
                <!--<div class="weui-cell__bd">-->
                    <!--<span class="weui-input" id="windows">8个</span>-->
                <!--</div>-->
            <!--</div>-->

            <!--<div class="weui-cell">-->
                <!--<div class="weui-cell__hd">-->
                    <!--<label class="weui-label">窗口状态：</label>-->
                <!--</div>-->
                <!--<div class="weui-cell__bd">-->
                    <!--<span class="weui-input" id="busy">空闲</span>-->
                <!--</div>-->
            <!--</div>-->

            <!--<div class="weui-cell">-->
                <!--<div class="weui-cell__hd">-->
                    <!--<label class="weui-label">排队人数：</label>-->
                <!--</div>-->
                <!--<div class="weui-cell__bd">-->
                    <!--<span class="weui-input" id="count">23</span><span class="weui-input">人</span>-->
                <!--</div>-->
            <!--</div>-->

            <!--<div class="weui-cell">-->
                <!--<div class="weui-cell__hd">-->
                    <!--<label class="weui-label">当前叫号：</label>-->
                <!--</div>-->
                <!--<div class="weui-cell__bd">-->
                    <!--<span class="weui-input" id="presentNumber">B032</span>-->
                <!--</div>-->
            <!--</div>-->
        </div>

        <div class="weui-flex" style="margin-top: 15px;padding: 0 20px;">
            <div id="judge" class="button weui-flex__item" onclick="showList()">查询队列</div>
            <!--<div id="advice" class="button weui-flex__item">意见调查</div>-->
            <!--<div id="cancel" class="button weui-flex__item">取消预约</div>-->
        </div>
    </div>

</div>
<div id="list" class="pk-modal -clean -show hide" style="overflow: scroll;height: 350px;width: 90%;left: 5%;right: 5%">
    <div class="head">当前排队队列&nbsp;<span id="people" style="color: red;font-weight: bold;font-size: 18px"></span>&nbsp;人</div>
    <!--<div class="pk-search"><input type="text"></div>-->
    <span class="close" onclick="closeList()">×</span>
    <p id="state" style="color: red;text-align: center">您当前排在第<span id="person"></span>位</p>
    <ul class="main" id="lineList">
        <!--<li class="pk-option -clean">-->
        <!--<span class="icon">1号</span>-->
        <!--<span class="txt">张*</span>-->
        <!--</li>-->
        <!--<li class="pk-option -clean">-->
        <!--<span class="icon">2号</span>-->
        <!--<span class="txt">李**</span>-->
        <!--</li>-->
        <!--<li class="pk-option -clean">-->
        <!--<span class="icon">3号</span>-->
        <!--<span class="txt">赵**</span>-->
        <!--</li>-->
        <!--<li class="pk-option -clean">-->
        <!--<span class="icon">4号</span>-->
        <!--<span class="txt">钱*</span>-->
        <!--</li>-->
        <!--<li class="pk-option -clean">-->
        <!--<span class="icon">5号</span>-->
        <!--<span class="txt">周*</span>-->
        <!--</li>-->
    </ul>
</div>
</body>
<script src="${ctx}/js/js/jquery-weui.js"></script>
<script type="text/javascript">
    <%
    RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
    %>

    var $openid = "<%=weUserinfo.getWxOpenId()%>";
    var $name = "<%=weUserinfo.getUsername()%>";
    var $card = "<%=weUserinfo.getIdCardNumber()%>";
    var $phone = "<%=weUserinfo.getPhoneNumber()%>";
    var isReg = false;
    if($name == 'null' || $card == 'null'){
        isReg = true;
        $.confirm({
            title:'提示',
            text:'您尚未注册，无法进行预约，点击确定前往注册',
            onOK:function () {
                //跳转到注册页面
                getHtml('${ctx}/wxUserAction/goRegister');
            },
            onCancel:function(){
                getHtml('${ctx}/wxUserAction/toIndex');
            }
        });
    }else{
        $("#line").click(function () {
            getHtml('${pageContext.request.contextPath}/wxUserAction/toIndex')

        });
        var departmentId;
        function sAppoint(object) {
            $.showLoading();
            departmentId = $(object).attr("id");
            $("#optionWrap").hide();
            $("#place_list").hide();
            $.ajax({
                url:'http://120.24.238.95:7777/payOut/wx/wxSquenceController/getList',
                type:'post',
                dataType:'json',
                data:{
                    deptid:departmentId
//                deptid:1
                },
                success:function (result) {
//                console.log(result.stateType);
                    if(result.stateType == 0){
                        console.log(result.stateValue);
                        $.each(result.stateValue,function (index,value) {
                            var number = new Array();
//                        console.log(value.winNumber);
//                        console.log(winNumber);
                            number = [];
                            $.each(value.winNumber,function (i,item) {
                                number.push(item.queueNumber);
                                console.log(number);
                            });
                            $("#out").append(
                                "<div id='bw"+index+"'>"+
                                "<div id='info' class='weui-cells weui-cells_form' style='display: block;margin-top: 1em'>"+
                                "<div class='weui-cell'>"+
                                "<div class='weui-cell__hd'>"+
                                "<label class='weui-label'>队&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;列：</label>"+
                                "</div>"+
                                "<div class='weui-cell__bd'>"+
                                "<span class='weui-input' id='bType"+index+"'>"+value.squenceName+"</span>"+
                                "</div>"+
                                "</div>"+

                                "<div class='weui-cell'>"+
                                "<div class='weui-cell__hd'>"+
                                "<label class='weui-label'>窗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;口：</label>"+
                                "</div>"+
                                "<div class='weui-cell__bd'>"+
                                "<span class='weui-input' id='windows"+index+"'>"+8+"</span><span class='weui-input'>个</span>"+
                                "</div>"+
                                "</div>"+

                                "<div class='weui-cell'>"+
                                "<div class='weui-cell__hd'>"+
                                "<label class='weui-label'>窗口状态：</label>"+
                                "</div>"+
                                "<div class='weui-cell__bd'>"+
                                "<span class='weui-input' id='busy"+index+"'></span>"+
                                "</div>"+
                                "</div>"+

                                "<div class='weui-cell'>"+
                                "<div class='weui-cell__hd'>"+
                                "<label class='weui-label'>排队人数：</label>"+
                                "</div>"+
                                "<div class='weui-cell__bd'>"+
                                "<span class='weui-input' id='count"+index+"'>"+value.waitCount+"</span><span class='weui-input'>人</span>"+
                                "</div>"+
                                "</div>"+

                                "<div class='weui-cell'>"+
                                "<div class='weui-cell__hd'>"+
                                "<label class='weui-label'>当前叫号：</label>"+
                                "</div>"+
                                "<div class='weui-cell__bd'>"+
                                "<span class='weui-input' id='presentNumber"+index+"'>"+number+"</span>"+
                                "</div>"+
                                "</div>"+
                                "</div>"+

                                "<div bType='"+value.businessType+"' class='weui-flex' style='margin-top: 15px;padding: 0 20px;'>"+
                                "<div id='judge"+index+"' class='button weui-flex__item' onclick='showList(this)'>查询队列</div>"+
                                "</div>"+
                                "</div>"
                            );
                            //暂时
                            $("#windows1").text("2");
                            if(value.waitCount <= 30){
                                $("#busy"+index).text("空闲");
                            }else if(value.waitCount > 30 && value.waitCount <= 60){
                                $("#busy"+index).text("一般");
                            }else if(value.waitCount > 60){
                                $("#busy"+index).text("繁忙");
                            }
                            if(value.waitCount == 0){
                                $(".button").click(function () {
                                    $("#list").hide();
                                    $.alert("当前无人排队办理业务，欢迎随时前来办理业务");
                                });
                            }
                        })
                    }else if(result.stateType != 0 || result.stateValue == []){
                        $.alert("数据推送中，请稍候查询")
                    }
                    $.hideLoading();
                }
            })
        }
        $.ajax({
            url:'http://120.24.238.95:7777/payOut/deptAddressAction/getDeptAddress',
            type:'post',
            dataType:'json',
            success:function(res){
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
            }
        });
        function closeList() {
            $("#list").removeClass("slideInUp").addClass("animated slideOutUp")
        }
        function sub(str) {
            return str.substr(0,1) + new Array(str.length).join('*');
        }
        function showList(obj) {
//        alert(111);
            $.showLoading();
            $("#lineList").empty();
            $.ajax({
                url:'http://120.24.238.95:7777/payOut/wx/wxSquenceController/getSquenceList',
                type:'post',
                dataType:'json',
                data:{
                    businessType:$(obj).parent().attr("bType"),
                    deptid:departmentId
                },
                success:function (data) {
                    if(data.stateType == 0){
                        console.log(data.stateValue);
                        $("#people").text(data.stateValue.length);
                        $.each(data.stateValue,function (ind,val) {
                            console.log(val);
                            if(val.state == 0){
                                $("#lineList").append(
                                    "<li id='theCard"+ind+"' class='pk-option -clean' card='"+val.card+"'>"+
                                    "<span class='icon' style='margin:0;padding-left: 5%;width: 45%'>"+val.queueNumber+"</span>"+
                                    "<span class='txt' style='margin: 0;width: 50%;text-align: center'>"+sub(val.name)+"</span>"+
                                    "</li>"
                                );
                                if($("#theCard"+ind).attr('card') == $card){
                                    $("#theCard"+ind).css('color','red');
                                    var lineNumber = ind + 1;
                                    $("#person").text(lineNumber);
                                }else{
                                    $("#state").text("当前没有您的排队信息，请确认您是否已取号或稍后查询");
                                    $("#state").css("padding","5px 35px")
                                }
                            }
                        })
                    }else if(data.stateValue != 0 || data.stateValue == []){
                        $.alert("数据推送中，请稍候再次点击查询队列进行刷新");
                        closeList();
                    }
                    $.hideLoading();
                }
            });
            $("#list").removeClass("hide slideOutUp").addClass("animated slideInUp")

        }
    }
</script>
</html>
