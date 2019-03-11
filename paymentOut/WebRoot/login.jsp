<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>登录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div class="mCLoginInfo fCLogin">
		<div class="ht">
			<h1 class="logo">
				<i class="icon">&nbsp;</i>框架
			</h1>
		</div>
		<!--//ht结束-->
		<div class="loginStyle ct">
			<p class="thBg"></p>
			<div class="bd">
				<div class="content">
					<form id="ff" class="easyui-form"
						action="${ctx }/mainController/checkUserLogin" method="post"
						data-options="novalidate:true">
						<ul class="list">
							<li class="label"><label for="userName">用户名：</label>
							</li>
							<li class="clear"></li>
							<li class="inputLine"><input class="easyui-textbox"
								type="text" id="userName" name="userName"
								data-options="required:true"></input>
							</li>
							<li class="verifyLine"><span class="tip">请输入用户名</span>
							</li>
						</ul>
						<ul class="list">
							<li class="label"><label for="password">密&nbsp;&nbsp;码：</label>
							</li>
							<li class="clear"></li>
							<li class="inputLine"><input class="easyui-textbox"
								id="password" type="text" name="password"
								data-options="required:true"></input>
							</li>
							<li class="verifyLine"><span class="tip">请输入密码</span>
							</li>
						</ul>
						<ul class="list">
							<li class="label"><label for="checkCode">验证码： </label>
							</li>
							<li class="clear"></li>
							<li class="codeLine inputLine"><input class=""
								id="checkCode" type="text" name="checkCode"
								data-options="required:true"><img alt="点击刷新验证码"
								src="${ctx }/mainController/validateCode" id="imgs" /></li>
							<li class="verifyLine"><span class="tip">请输入验证码</span>
							</li>
						</ul>
						<ul>
							<li class="submitLine"><a id="btnLogin"
								href="javascript:void(0);" class="login rwpButton"><i
									class="icon">&nbsp;</i>登录</a>
								<p class="clear"></p>
							</li>
							<p class="clear"></p>
							<li></li>
						</ul>
					</form>
				</div>
				<!--//content结束-->
			</div>
			<!--//bd结束-->
			<p class="ftBg"></p>
		</div>
		<!--//ct结束-->
		<div class="ft">
			<p>建议使用浏览器：IE7-11,Google Chrome</p>
			<p class="grey"></p>
		</div>
		<!--//ft结束-->
	</div>
	<!--//mCLoginInfo结束-->
</body>
</html>
