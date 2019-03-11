<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../base.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>服务评价</title>
    <link rel="stylesheet" href="${ctx}/css/weui.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/style.css"/>
    <script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/script1.js"></script>
    <script type="text/javascript" src="${ctx}/js/popups.js"></script>
    <style>
        .bg {
            background: #ffffff;
            border: 1px solid #e2e2e2;
            border-radius: 5px;
        }

        .bg_click {
            background: #ffffff;
            border: 1px solid #ffb42a;
            border-radius: 5px;
            /*color: #ffffff!important;*/
        }

        .bg_click > a {
            color: #ffb42a;
        }

        .bg_spe {
            background: #ffffff;
            border: 1px solid #e2e2e2;
            border-radius: 5px;
        }
		
        textarea {
            border: 1px solid black;
            font-size: 16px;
            width: 99.7%;
            height: 125px;
        }

        .head {
            /*position: absolute;*/
            /*top: -7px;*/
            /*left: 0;*/
            /*right: 0;*/
            font-size: 18px;
            /*width: 30%;*/
            font-weight: bold;
            margin: 0 auto;
            /*background: #fff;*/
            color: #333333;
        }

     /*   .show-list {
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
        }*/
    </style>
    <script>
        $(function () {
            if ($(".option>ul:last>li").length == 2) {
//                console.log($(".option>ul:last>li").length);
                $(".option>ul:last>li").removeClass('bg').addClass('bg_click');
            } else {
                Description = [];
            }
        })
    </script>
</head>

<body>
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent" style="background: #000; opacity: 0.5"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>
<input type="hidden" name="istest" id="istest" value="${model.istest}">
<div class="js_dialog" id="iosDialog1" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">

        <div class="weui-dialog__bd">双按钮弹窗，描述文字尽量控制在三行内。</div>
        <div class="weui-dialog__ft">
            <a href="javascript:;" id="doubleNO" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
            <a href="javascript:;" id="doubleOK" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
        </div>
    </div>
</div>
<div class="xzw_starSys">
    <!--<div id="hIde" style="margin-bottom: 1.6em;font-size: 1.5em;">-->
    <!--您已关注红谷滩微信户政公众号-->
    <!--</div>-->
    <div class="prompt" style="position: relative">
        <p class="head">您好，请评价我的服务</p>
    </div>
    <div class="xzw_starBox">
        <ul class="star stars" style="overflow: hidden;">
            <!--改为由JS控制距离，否则不好循环-->
            <c:forEach items="${list}" var="item" varStatus="sta">
                <li><a href="javascript:void(0)" id="${sta.index+1}" class="all_star" value="${sta.index+1}"></a></li>
            </c:forEach>
        </ul>
        <div class="current-rating showb"></div>
    </div>
    <!--评价文字-->
    <!--<div class="description"></div>-->
    <!--评价选项-->
    <div class="option" style="background:#fff;">
        <c:forEach items="${list}" var="item">
            <ul class="option-con clearfix">
                <c:forEach items="${item.hotwords}" var="hotword">
                    <li class="bg"><a href="javascript:void(0);">${hotword}</a></li>
                </c:forEach>
            </ul>
        </c:forEach>
    </div>
    <ul id="oa" style="background:#fff;padding: 20px;display: none">
        <li style="margin-top: 20px;padding: 0;width: 100%;">
            <textarea id="text" style="border:1px solid #f2f2f2;background: #fafafa;" rows="10" cols="30"
                      placeholder="如有其他意见或建议请写在这里~"></textarea>
        </li>
    </ul>
</div>
<!--内容 end-->
<!--底部 star-->
<!--<footer class="assess-footer fixed-footer ">-->
<!--<ul>-->
<!--<input id="OK" type="button" value="提交评价" />-->
<!--</ul>-->
<!--</footer>-->
<div class="weui-msg__opr-area" style="margin-bottom: 0;margin-top: 20px">
    <input type="hidden" id="id" name="id" value="${id}">
    <p class="weui-btn-area">
        <a id="OK" href="javascript:;" class="weui-btn weui-btn_primary" style="width: 90%">提交评价</a>
    </p>
</div>
<!--底部  end-->
<!--代码结束-->
</body>
<script>

    //doubleDialog("是否参加问卷调查？","确定","取消");
    var point;
    var ulWidth = 30;
    var stars = $(".star>li").length;
    if (stars == 2) {
        point = 2;
    } else if (stars == 3) {
        point = 3
    } else if (stars == 4) {
        point = 4;
    } else if (stars == 5) {
        point = 5;
    }
    //    $(".star").css("width",stars*ulWidth);
    $(".xzw_starBox").css("width", stars * ulWidth);
    $(".option>ul:last").css("display", "block");
    //    console.log();
    var Description = ['非常满意'];
    var description = new Array();
    $(".star>li:eq(0)>a").css("left", 0);
    $(".star>li:eq(1)>a").css("left", 30);
    $(".star>li:eq(2)>a").css("left", 60);
    $(".star>li:eq(3)>a").css("left", 90);
    $(".star>li:eq(4)>a").css("left", 120);
    $(".option-con").append("<li class='bg_spe'>" +
        "<img style='height: 14px;float: left' src='${ctx}/img/judge.png' alt=''>" +
        "<span style='color: #00a0e9'>文字评价</span>" +
        "</li>");

    //数组去重
    function unique(arr) {
        var ret = [];
        for (var i = 0; i < arr.length; i++) {
            var item = arr[i];
            if (ret.indexOf(item) === -1) {
                if (item != "") {
                    ret.push(item);
                }
            }
        }
        return ret
    }

    //删除没有被选中的
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    $('.bg_spe').click(function () {
        $("#oa").toggle();
    });
    $(".all_star").click(function () {
//        console.log(222);

        //重置热词
        if ($(".option-con>li").hasClass('bg_click')) {
            $(".option-con>li").removeClass('bg_click').addClass('bg')
        }

        $("#oa").hide();
        $('#text').val("");
        $("#hIde").css("display", "none");
        point = ($(this).attr("id"));
//        console.log(point);
        Description = [];
        for (var i = 0; i < $(".star>li").length; i++) {
//        console.log($(".option").find(".option-con:eq("+i+")>li").length);
            if ($(".option").find(".option-con:eq(" + i + ")>li").length == 2) {
                $(".option").find(".option-con:eq(" + i + ")>li").removeClass('bg').addClass('bg_click');
                $(".bg_spe").removeClass("bg_click");
                // console.log($(this));
                if ($(this).attr('value') == 1) {
                    Description.push($('.bg_click:eq(0)').text());
                } else if ($(this).attr('value') == 2) {
                    Description.push($('.bg_click:eq(1)').text());
                } else if ($(this).attr('value') == 3) {
                    Description.push($('.bg_click:eq(2)').text());
                } else if ($(this).attr('value') == 4) {
                    Description.push($('.bg_click:eq(3)').text());
                } else if ($(this).attr('value') == 5) {
                    Description.push($('.bg_click:eq(4)').text());
                }
                description = unique(Description);
            }
        }
    });
    //     Description = [];
    //     for(var i = 0;i<$(".star>li").length;i++){
    // //        console.log($(".option").find(".option-con:eq("+i+")>li").length);
    //         if($(".option").find(".option-con:eq("+i+")>li").length == 2){
    //             $(".option").find(".option-con:eq("+i+")>li").removeClass('bg').addClass('bg_click');
    // //            console.log($('.bg_click>a').text());
    //             Description.push($('.bg_click>a').text());
    //             description = unique(Description);
    //         }
    //     }
    $(".option-con li").click(function () {
        if ($(this).hasClass("bg")) {
            $(this).removeClass("bg").addClass("bg_click");
        } else {
            $(this).removeClass("bg_click").addClass("bg");
        }
        //console.log($(".bg_click").text());
        //new一个数组，把评语放进去，用逗号分隔
        //判断是否被选中
        if ($(this).hasClass("bg_click")) {
            Description.push($(this).text());
        } else {
            Description.remove($(this).text());
        }
//        console.log(unique(Description));
        description = unique(Description);
//        console.log(description);
    });

    $("#OK").click(function () {
        $("#loadingToast").css("display","block");
        description = unique(Description);
        var id = $("#id").val();
        var otheradvice = $("#text").val();
        var ajaxData = "description=" + description;
        ajaxData += "&point=" + point;
        ajaxData += "&otheradvice=" + otheradvice;
        ajaxData += "&id=" + id;
        function doubleDialog(str, doubleNO, doubleOK) {
            $("#doubleOK").unbind("click");
            $("#doubleNO").unbind("click");

            var $iosDialog1 = $('#iosDialog1');
            $iosDialog1.find(".weui-dialog__bd").html(str);
            if (doubleNO) { $('#doubleNO').text(doubleNO); } else { $('#doubleNO').text("取消"); }
            if (doubleOK) { $('#doubleOK').text(doubleOK); } else { $('#doubleOK').text("确定"); }

            $iosDialog1.fadeIn(200);
            // $('#iosDialog1').on('click', '.weui-dialog__btn', function () {
            //     $iosDialog1.fadeOut(200);
            // });
            $("#doubleOK").click(function () {
                $("#loadingToast").css("display","block");
                $iosDialog1.fadeOut(200);
                window.location.href = "${ctx}/wx/appraiseDetailAction/GotoTest?id=" + id;
            });
            $("#doubleNO").click(function () {
                $("#loadingToast").css("display","block");
                $iosDialog1.fadeOut(200);
              //  wx/appraiseDetailAction
                window.location.href = "${ctx}/wx/appraiseDetailAction/finish?id=" + id;
            });
        }
        $.ajax({
            type: "post",
            url: "${ctx}/wx/userAppraiseAction/saveUserAppr",
            data: ajaxData,
            dataType: "json",
            success: function (data) {
                $("#loadingToast").css("display","none");
                console.log(data);
                if (data.stateType == 0) {
                    var istest = $("#istest").val();
                    if (istest == 1) {
                        doubleDialog("是否参加问卷调查？","取消","确定");

                       /* jqalert({
                            title: '提示',
                            content: '您是否愿意进行满意度调查？',
                            yestext: '是',
                            notext: '否',
                            yesfn: function () {
                                //$("#loadingToast").css("display","block");
                                window.location.href = "${ctx}/wx/appraiseDetailAction/GotoTest?id=" + id;
                            },
                            nofn: function () {
                                window.location.href = "${ctx}/wx/appraiseDetailAction/finish?id=" + id;
                            }
                        });*/
                    } else {

                        window.location.href = "${ctx}/wx/appraiseDetailAction/finish?id=" + id;
                    }
                } else {
                    window.location.href = "${ctx}/wx/appraiseDetailAction/unfinish?id=" + id;
                }
            }
        })
    });
    //   function formsubmit() {
    //       var _judgement = $(".bg_click").html();
    //       var ajaxData = "judgement=" + _judgement;
    //       ajaxData += "&point=" + point;
    //       $.ajax({
    //           type:"post",
    //           url:"${ctx}/wx/userAppraiseAction/saveUserAppr",
    //           data:ajaxData,
    //           dataType:"json",
    //          success:function (data) {
    //              console.log(data);
    //            if(data>0){
    //                   window.location.href = "${ctx}/wx/appraiseDetailAction/finish";
    //                 }else{
    //                   window.location.href = "${ctx}/wx/appraiseDetailAction/unfinish";
    //                   }
    //           }
    //       })
    //   }
</script>
</html>