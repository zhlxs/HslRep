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
          href="${pageContext.request.contextPath}/css/css/weui.css">
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
    </style>
    <script type="text/javascript">
        <%RegisterUser weUserinfo = (RegisterUser) request.getSession()
            .getAttribute("userInfo");%>
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
            //$("#drr").click();
        });
    </script>
</head>
<body>
<div id="main" class="weui-tab__panel">
    <div>
        <div style="margin-top: 1em;margin-left: 15px">
            <span style="color: #8D8D8D;">队列信息</span>
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
</div>
<div class="weui-tabbar"
     style="position:fixed; bottom:0;width:100%;height: 53px;">
    <a id="drr" href="javascript:;"
       class="weui-tabbar__item weui-bar__item_on"> <img
            src="${pageContext.request.contextPath}/img/bottom-sy.png"
            style="display:none" class="weui-tabbar__icon" id="sy"> <img
            src="${pageContext.request.contextPath}/img/bottom-sy-dj.png"
            class="weui-tabbar__icon" id="sy-dj">
        <p class="weui-tabbar__label">业务办理</p>
    </a> <a id="drr1" href="javascript:;" class="weui-tabbar__item"> <img
        src="${pageContext.request.contextPath}/img/bottom-sy1.png"
        class="weui-tabbar__icon" id="sy1"> <img
        src="${pageContext.request.contextPath}/img/bottom-sy1-dj.png"
        class="weui-tabbar__icon" style="display:none" id="sy1-dj">
    <p class="weui-tabbar__label">事项详情</p>
</a> <a id="drr3" href="javascript:;" class="weui-tabbar__item">
    <div class="weui-tabbar__icon"
         style="background-image:url(${pageContext.request.contextPath}/img/bottom-sy3.png);background-size: 100%;"
         id="sy3"></div>
    <div class="weui-tabbar__icon"
         style="background-image:url(${pageContext.request.contextPath}/img/bottom-sy3-dj.png);background-size: 100%;display:none"
         id="sy3-dj"></div>
    <p class="weui-tabbar__label">个人中心</p>
</a>
</div>
<!--BEGIN toast-->
<!-- <div id="toast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content" id="weui-toast__content">已完成</p>
    </div>
</div> -->
<!-- loadingToast -->
<!-- <div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div> -->
</body>
<script type="text/javascript">
    function getHtml(url) {
        if (url) {
            $.showLoading();
            $.post(url, function(result) {
                $("#main").html(result);
                $.hideLoading();
            });
        }
    }
    // loading
    /* function loadingToast() {
     var $loadingToast = $('#loadingToast');
     if ($loadingToast.css('display') != 'none')
     return;
     $loadingToast.fadeIn(100);
     } */
    // toast
    /* function toast(toastcontent) {
     $(".weui-toast__content").text("已完成");
     if (toastcontent) {
     $("#weui-toast__content").text(toastcontent);
     }
     var $toast = $('#toast');
     if ($toast.css('display') != 'none')
     return;
     $toast.fadeIn(100);
     setTimeout(function() {
     $toast.fadeOut(100);
     }, 2000);
     } */
</script>
<script type="text/javascript">
    window.addEventListener("popstate", function (e) {
        //alert("我监听到了浏览器的返回按钮事件啦");
        // 根据自己的需求实现自己的功能
        getHtml('${ctx}/wxUserAction/toIndex');
    });
    //$.showLoading();
    <%-- <%
    RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
    %> --%>
    var $name = "<%=weUserinfo.getUsername()%>";
    var $card = "<%=weUserinfo.getIdCardNumber()%>";
    //var $phone = "<%=weUserinfo.getPhoneNumber()%>";
    var isReg = false;
    var $openId = "<%=weUserinfo.getWxOpenId()%>";
    var $Id = "${seqid}";
    //alert($Id);
    var ajaxData = 'id=' + $Id;
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
                getHtml('${ctx}/wxUserAction/toIndex');
            }
        });
    }else{
        $.ajax({
            url:'${ctx}/wxUserAction/sequenceInfoJsonById',
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
                                "<label class='weui-label'>待办业务：</label>" +
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
                                /* "<div class='weui-cell'>" +
                                "<div class='weui-cell__hd'>" +
                                "<label class='weui-label'>预约时段：</label>" +
                                "</div>" +
                                "<div class='weui-cell__bd'>" +
                                "<span class='weui-input' id='time'>" + value['timeInter'] + "</span>" +
                                "</div>" +
                                "</div>" + */
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
