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
		$("#drr").click();
	});
</script>
</head>
<body>
	<div id="main" class="weui-tab__panel"></div>
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
</html>