<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>框架</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>@ViewBag.Title</title>
<!--[if lte IE 6]>
    <script src="@Url.Content("~/lib/png.js")" type="text/javascript"></script>
    <script type="text/javascript">
        try {
            document.execCommand("BackgroundImageCache", false, true);
        } catch(e) {}
        DD_belatedPNG.fix('div,p,h1,h2,h3,h4,ul,li,img,a,a:hover,span,em,b,i');
    </script>
  <![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpReset.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpLayout.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpToggle.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpCombobox.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDatagrid.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpDateinput.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpSwitch.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpTree.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpSpinner.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpForm.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpTab.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpIconSelect.css"
	rel="stylesheet" type="text/css" />
</head>


<body style="margin-top:-24px;">
	<div id="layout">
		<div region="top">
			<div class="gHd">
				<div class="uTop">
					<ul class="uTip">
						<li class="menuList time"><i class="i">&nbsp;</i><cite
							id="localdatetime"></cite>
						</li>
						<li class="tipList uDropMenu uDropMenuSmall dropMenuUser"><a
							href="javascript:void(0);" class="dropMenuTitle"><i
								class="i1">&nbsp;</i><em>${user.fullName}，欢迎您！</em><i class="i2">&nbsp;</i>
						</a>
							<div class="dropList">
								<i class="listToolBar"></i> <a class="list firstlist editPwd"
									href="" title="修改密码" id="mpwd"><strong><i
										class="i">&nbsp;</i>修改密码</strong> </a>
								<p class="line"></p>
								<a class="list ext" href="${ctx}/mainController/exit" title="退出"><strong><i
										class="i">&nbsp;</i>退出</strong> </a>
							</div> <!--dropList结束--></li>
					</ul>
				</div>
				<!--uTtop结束-->

				<div class="uCenter">
					<div class="Logo">
						<span class="first t">&nbsp;</span> <span class="txt t">框架</span>
						<span class="end t">&nbsp;</span>
					</div>
					<!--Logo结束 -->
					<div class="gIMenu">
						<ul></ul>
					</div>
					<!--gIMenu结束 -->
					<script type="text/javascript">
						// 						setTimeString();
						// 						setInterval(setTimeString, 1000);
					</script>
				</div>
				<!--uCenter结束-->
			</div>
			<!-- gHd结束 -->
		</div>
		<!-- top结束 -->
		<div data-options="{ 'region':'left','width':'200'}"></div>
		<div data-options="{ 'region':'center'}">
			<div id="pageLayout">
				<div class="pageHeader" data-options="{ 'region':'top' }"></div>
				<div class="pageContent" data-options="{ 'region':'center' }"></div>
			</div>
		</div>
		<!-- center结束 -->
	</div>
	<!-- layout结束 -->
</body>


</html>