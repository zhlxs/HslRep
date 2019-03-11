<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>服务评价</title>
    <link rel="stylesheet" href="${ctx}/css/css/weui.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/css/stars.css"/>
    <script type="text/javascript" src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
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
            font-size: 1rem;
            /*width: 30%;*/
            /*font-weight: bold;*/
            margin: 0 auto;
            /*background: #fff;*/
            color: #333333;
        }
        .weui-tabbar__label {
    		text-align: center;
    		color: #999999;
    		font-size: 10px;
    		line-height: 0.7;
		}
    </style>
    <script>
        $(function () {
            if ($(".option>ul:last>li").length == 2) {
//                console.log($(".option>ul:last>li").length);
                $(".option>ul:last>li").removeClass('bg').addClass('bg_click');
            }
        })
    </script>
</head>
<body>
<div style="margin: 0 auto">
    <img src="${ctx}/img/banner.png" alt="" style="max-width:100%;height:auto;">
</div>
<div class="xzw_starSys">
    <div class="prompt" style="position: relative">
        <p class="head">你好，请评价我的服务</p>
    </div>
    <div class="xzw_starBox">
        <ul class="star stars" style="overflow: hidden;">
            <c:forEach items="${list}" var="item" varStatus="sta">
                <li><a href="javascript:void(0)" id="${sta.index+1}" class="all_star" value="${sta.index+1}"></a></li>
            </c:forEach>
        </ul>
        <div class="current-rating showb"></div>
    </div>
    <!--评价文字-->
    <div class="description"></div>
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
    <p class="weui-btn-area">
        <a id="OK" href="javascript:;" class="weui-btn weui-btn_primary" style="width: 90%;background: #118EEA;">确定</a>
    </p>
</div>
<!--底部  end-->
<!--代码结束-->
</body>
<script type="text/javascript">
<%
RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
%>
var userid = "<%=weUserinfo.getId()%>";
var stepW = 30;
var stars = $(".stars > li");
var descriptionTemp;
var option = $(".option");
$(".showb").css("width",0);
stars.each(function(i){
    $(stars[i]).click(function(e){
    	var description = new Array("非常不满意","不满意","一般","满意","非常满意");
        var n = i+1;
        $(".showb").css({"width":stepW*n});
        descriptionTemp = description[i];
        $(this).find('a').blur();
        $(".description").text(descriptionTemp);
        option.find(".option-con:eq(" + $(this).index() + ")").show().siblings().hide();
        return stopDefault(e);
        return descriptionTemp;
    });
    //$(stars[i]).click();
});
function stopDefault(e){
    if(e && e.preventDefault)
        e.preventDefault();
    else
        window.event.returnValue = false;
    return false;
}
</script>
<script>
    var point;
    var ulWidth = 30;
    var stars = $(".star>li").length;
    //    $(".star").css("width",stars*ulWidth);
    if (stars == 2) {
        point = 2
    } else if (stars == 3) {
        point = 3;
    } else if (stars == 4) {
        point = 4;
    } else if (stars == 5) {
        point = 5
    }
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
                ret.push(item)
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
        //重置热词
        if ($(".option-con>li").hasClass('bg_click')) {
            $(".option-con>li").removeClass('bg_click').addClass('bg')
        }
        $("#oa").hide();
        $('#text').val("");
        $("#hIde").css("display", "none");
        point = ($(this).attr("id"));
        Description = [];
        for (var i = 0; i < $(".star>li").length; i++) {
            if ($(".option").find(".option-con:eq(" + i + ")>li").length == 2) {
                $(".option").find(".option-con:eq(" + i + ")>li").removeClass('bg').addClass('bg_click');
                Description.push($(this).attr("value"));
                description = unique(Description);
            }
        }
    });
    $(".option-con li").click(function () {
        if ($(this).hasClass("bg")) {
            $(this).removeClass("bg").addClass("bg_click");
        } else {
            $(this).removeClass("bg_click").addClass("bg");
        }
        //new一个数组，把评语放进去，用逗号分隔
        //判断是否被选中
        if ($(this).hasClass("bg_click")) {
            Description.push($(this).text());
        } else {
            Description.remove($(this).text());
        }
        description = unique(Description);
    });
    $("#OK").click(function () {
        description = unique(Description);
        var other = $("#text").val();
        var ajaxData;
        if($("#text").val() == ""){
            ajaxData = "description=" + description;
            ajaxData += "&point=" + point;
            ajaxData += "&appointmeId=${appointmeId}";
            ajaxData += "&modelId=${modelId}";
        }else{
            ajaxData = "description=" + description;
            ajaxData += "&point=" + point;
            ajaxData += "&otheradvice=" + other;
            ajaxData += "&appointmeId=${appointmeId}";
            ajaxData += "&modelId=${modelId}";
        }
        $.ajax({
        	url:'${ctx}/wx/userAppraiseAction/saveUserAppr',
        	type:'post',
        	data:ajaxData,
        	success:function(result){
        		if(result.stateType == 0){
        			$.confirm({
        				title:'提示',
        				text:'评价成功！',
        				onOK:function () {
                            $.showLoading();
                            getHtml('${ctx}/wxUserAction/toIndex');
                            $.hideLoading();
                        },
                        onCancel:function () {
        					
                        }
        			});
        		}else{
        			
        		}
        	}
        });
    });
</script>
</html>