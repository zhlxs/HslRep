<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="com.jrwp.wx.entity.RegisterUser"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta charset="UTF-8">
    <title>网上车管所</title>
    <link rel="stylesheet" href="${ctx}/css/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/css/index.css">
    <script src="${ctx}/js/js/jquery-2.1.4.js"></script>
    <script src="${ctx}/js/js/jquery-weui.js"></script>
    <style>
        @media only screen and (min-width: 375px) {
            .title{
                font-size: 16px !important;
            }
        }
        @media only screen and (min-width: 360px) {
            .title{
                font-size: 14px !important;
            }
        }
        @media only screen and (max-width: 320px) {
            html,body,.title,.box_name,.kind,.bs_info{
                font-size: 10px !important;
            }
            ul .busi{
                width:41%;
            }
        }
    </style>
</head>
<body>

<article class="r-show">
    <div class="swiper-container">
        <!--<div>-->
            <div class="swiper-slide">
                <img src="${ctx}/img/banner.png" style="width:100%; height:auto;">
            </div>
            <div class="box_wrap">
                <ul class="box clearfix">
                    <li class="box_info" id="b_place">
                        <img src="${ctx}/img/place.png" alt="">
                        <span class="box_name">办证网点</span>
                    </li>
                    <li class="box_info" id="b_appoint">
                        <img src="${ctx}/img/busy.png" alt="" style="width: 35px;">
                        <span class="box_name">网上预约</span>
                    </li>
                    <li class="box_info" id="b_matter">
                        <img src="${ctx}/img/bzjd.png" alt="">
                        <span class="box_name">办理进度</span>
                    </li>
                    <li class="box_info" id="b_line">
                        <img src="${ctx}/img/jhjd.png" alt="">
                        <span class="box_name">叫号进度</span>
                    </li>
                </ul>
                <div class="box_title">
                    <span class="title_tip">通知</span>
                    <span class="title">好消息！以后可以网上预约办事啦！</span>
                </div>
            </div>
        <!--</div>-->
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>

        <!-- <div class='containera' id="+conId+">
            <div class='headera'>
                <div style='width:100%;padding: 15px 12px; border-bottom: 1px solid #ccc'>
                    <img src="img/tip.png" alt="" style="vertical-align: middle">
                    <span class='apply'>业务办理</span>
                </div>
            </div>
        </div> -->

        <!-- <ul class="clearfix">
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/zcdj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info0">注册登记</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/zydj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info1">转移登记</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/zrdj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info2">转入登记</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/bgdj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info3">变更登记</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/bldj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info4">补领登记证书</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/bldj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info5">核发临时号牌</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/dydj.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info6">抵押登记</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/jcdy.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info7">解除抵押</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/jz.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">加载/卸载</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info8">辅助装置</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class='busi'>
                <ul class="clearfix">
                    <li class="busi_cell">
                        <a href='javascript:void(0);' onclick="">
                            <img src="img/more.png">
                        </a>
                    </li>
                    <li class="busi_cell">
                        <ul class="text_left">
                            <li>
                                <span class="kind">机动车</span>
                            </li>
                            <li>
                                <span class="bs_info" id="b_info9">补领行驶证</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul> -->
    </div>
</article>
<div style="clear:both;"></div>
<div id="clear" style="clear:both;height:30px;display: none;"></div>
<!--<div id="footer"></div>-->
</body>
<script>
    alert("此功能正在测试中，请勿使用，给您带来的不变敬请谅解！");
	<%
	RegisterUser weUserinfo = (RegisterUser) request.getSession().getAttribute("userInfo");
	%>

	var $openid = "<%=weUserinfo.getWxOpenId()%>";
	$("#b_matter").click(function(){
        getHtml('${pageContext.request.contextPath}/wx/appointInfoAction/appointInfoView');
	});
	$("#b_appoint").click(function(){
	    //直接预约界面straightappointbusiness
		getHtml("${ctx}/wx/businessAction/directAccess");
	});
	$("#b_place").click(function(){
	    //部门页面place
		getHtml('${ctx}/wxUserAction/addressList');
	});
	$("#b_line").click(function(){
	    //排队队列line_up
		getHtml('${ctx}/wxUserAction/sequenceInfo?openid=' + $openid);
	});
	//$.showLoading();
    $.ajax({
        type: 'post',
        url: '${ctx}/wx/businessAction/businessListJson',
        dataType: 'json',
        success: function (result) {
            $.each(result, function (index, obj) {
            	//console.log(obj);
                var businessConfigId = obj.serCode;
//                alert(isproxy);
                var conId = "con" + index;
                /* $("#main").append("<div class='containera' id=" + conId + ">" +
                    "<div class='headera'>" +
                    "<div style='width:100%;padding: 15px 12px; border-bottom: 1px solid #ccc'>" +
                    "<img src='${ctx}/img/tip.png' style='vertical-align: middle'>" + 
                    "<span class='apply'>" + obj['serName'] + "</span>" +
                    "</div>" +
                    "</div>" +
                    "</div>"); */
                    $("#main").append(
                            "<div class='containera' id=" + conId + ">" +
                            "<div class='headera'>" +
                            "<div style='width:100%;padding: 15px 12px; border-bottom: 1px solid #ccc'>" +
                            "<img src='${ctx}/img/tip.png' style='vertical-align: middle'>" +
                            "<span class='apply'>" + obj['serName'] + "</span>" +
                            "</div>" +
                            "</div>" +
                            "</div>"
                        );
            	//console.log(obj.children);
                var ulId = "ul" + index;
                var $con = $("#" + conId);
                $con.append("<ul id=" + ulId + "></ul>");
                $.each(obj.children, function (index, child) {
//                console.log(child['iconpathstr']);
               		//console.log(child.config['iconpathstr']);
                    var childId = child.serCode;
                    var businessType = child.businessType;
                    var title = child.serName;
//                console.log(childId);
                    var $ul = $("#" + ulId);
                    var str = "${ctx}/" + child.config['iconpathstr'];
                    var isproxy = child.isproxy;
//                var jump= "toApplybusiness"+"&childId="+childId+"&title="+title;
//                console.log(jump);
                    var url = "${ctx}/wx/businessAction/toApplybusiness?serCode="+childId+ '&businessType=' + businessType;
                    /* $ul.append("<li class='busi'>" + "<div>" +
                        "<a href='javascript:;' id='jump" + index + "' onclick='getHtml(" + url + ")'><img src=" + str + "></a>" +
                        "</div>" + "<span style='font-size: 13px;'>" + child['serName'] + "</span>" + "</li>"); */
//                    function go(object) {
//                        alert(111);
//                        alert($(object).parent("li").attr("isproxy"));
//                        if($(object).parent("li").attr("isproxy") == 1){
//                            $.alert("抱歉！根据相关规定暂不提供预约代办功能，请前往办事大厅进行办理")
//                        }else{
//                            ;
//                        }
//                    }
                    $ul.append(
                            "<li id='jump"+index+"' class='busi'>"+
                            "<ul class='clearfix'>"+
                            "<li class='busi_cell' isproxy='"+isproxy+"' >"+
                            "<a href='javascript:void(0);'>"+
                            "<img src="+str+">"+
                            "</a>"+
                            "</li>"+
                            "<li class='busi_cell'>"+
                            "<ul class='text_left'>"+
//                            "<li>"+
//                            "<span class='kind'>驾驶证</span>"+
//                            "</li>"+
                            "<li>"+
                            "<span style='line-height: 50px;color: #000' class='bs_info' id='b_info"+index+"'>" + child['serName'] + "</span>"+
                            "</li>"+
                            "</ul>"+
                            "</li>"+
                            "</ul>"+
                            "</li>"
                        );
                    if(child.isproxy == 1){
                        $("#jump"+index).click(function () {
                            $.alert("抱歉！根据相关规定暂不提供预约代办功能，请前往办事大厅进行办理");
                        });
                    }else{
                        $("#jump"+index).click(function () {
                            getHtml(url);
                        });
                    }
//                    console.log($("#b_info"+index).text());
                });
                $.hideLoading();
            })
        }
    });
    //    console.log(new Date());
</script>
</html>