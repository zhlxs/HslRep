<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpReset.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/js/rwpUI/skins/login/rwpLogin.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/js/rwpUI/skins/lightblue/css/rwpForm.css"
	rel="stylesheet" type="text/css">
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<style type="text/css" adt="123"></style>
<script src="${ctx}/js/rwpUI/js/core/rwpInit.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.validate.unobtrusive.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.js" type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpDialog.plugin.js"
	type="text/javascript"></script>
<script src="${ctx}/js/rwpUI/js/ui/rwpForm.js" type="text/javascript"></script>
<script type="text/javascript">
	if (window.top != self) {
		window.top.location = self.location;
	}
</script>
<script type="text/javascript">
	//提交前
	function beforeSubmit() {
		var subbtn = $('#btnLogin');
		subbtn.attr("disabled", true);
		subbtn.val("登录中");
	}
	//回车提交表单
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#btnLogin").click();
        }
    });
	//提交后
	function afterSubmit(data) {
		var subbtn = $('#btnLogin');
		subbtn.attr("disabled", false);
		subbtn.val("登录");
		if (data && data.stateType == 2) {
			$.each(data.validationResults, function(i, item) {
				$.each(item.memberNames, function(j, name) {
					if (name == 'checkCode') {
						var $input = $("#checkCode", $("#ajaxform"));
						$input.next('img').click(); //刷新验证码
						return false;
					}
				});
			});
		}
	}

	$(function() {
		$("#ajaxform").rwpUIForm({
			isAutoPlugin : false,
			isAjaxSubmit : true,
			ajaxSubmitAttrs : {
				isDialog : false,
				isRedirect : true,
				beforeSubmit : beforeSubmit,
				afterSubmit : afterSubmit
			}
		});

		$('.fCLogin .inputLine input').focus(
				function() {/*获取焦点 文本框错误提示验证*/
					$(this).parents('.list').find('.tip').css("display",
							"inline-block");
				});

		$('.fCLogin .inputLine input').blur(function() {/*失去焦点 文本框错误提示验证*/
			$(this).parents('.list').find(".tip").css("display", "none");
		});

		$('#btnLogin').click(function(e) {
			e.preventDefault();
			$("#ajaxform").data('rwpUIForm').ajaxSubmit();
		});

		$("#imgs").click(
				function() {
					$("#imgs").attr("src",
							"${ctx }/validateCode?rnd=" + Math.random());
				})
	});
</script>
</head>
<body>
	<div class="mCLoginInfo fCLogin">
		<div class="ht">
			<h1 class="logo">
				<i class="icon">&nbsp;</i>${sysBaseInfo.sysName}
			</h1>
		</div>
		<!--//ht结束-->
		<div class="loginStyle ct">
			<p class="thBg"></p>
			<div class="bd">
				<div class="content">
					<sf:form id="ajaxform" action="${ctx }/login" method="post"
						modelAttribute="core_User">
						<ul class="list">
							<li class="label"><label for="userName">用户名：</label></li>
							<li class="clear"></li>
							<li class="inputLine"><input data-val="true"
								data-val-length="用户名不能超过20字符" data-val-length-max="20"
								data-val-required="请输入用户名" id="userName" name="userName"
								type="text" value=""></li>
							<li class="verifyLine"><span class="field-validation-valid"
								data-valmsg-for="userName" data-valmsg-replace="true"></span></span> <span
								class="tip">请输入用户名</span></li>
						</ul>
						<ul class="list">
							<li class="label"><label for="password">密&nbsp;&nbsp;码：</label>
							</li>
							<li class="clear"></li>
							<li class="inputLine"><input data-val="true"
								data-val-length="密码长度必须是6到32个字符" data-val-length-max="32"
								data-val-length-min="6" data-val-required="请输入密码" id="password"
								name="password" type="password"></li>
							<li class="verifyLine"><span class="field-validation-valid"
								data-valmsg-for="password" data-valmsg-replace="true"></span><span
								class="tip">请输入密码</span></li>
						</ul>
						<ul class="list" >
							<li class="label"><label for="checkCode">验证码： </label></li>
							<li class="clear"></li>
							<li class="codeLine inputLine"><input id="checkCode"
								name="checkCode" type="text" value=""> <img
								alt="点击刷新验证码" src="${ctx }/validateCode" id="imgs" />
							</li>
							<li class="verifyLine"><span class="field-validation-valid"
								data-valmsg-for="checkCode" data-valmsg-replace="true"></span><span
								class="tip">请输入验证码</span></li>
						</ul>
						<ul>
							<li ><input id="remeberMe" type="checkbox" value="RemeberMe"
								name="remeberMe" /><label for="remeberMe"><span style="margin-left:10px">记住我</span></label>
							</li>
							<li class="submitLine" style="text-align: center;"><a id="btnLogin"
								href="javascript:void(0);" class="login rwpButton"><i
									class="icon">&nbsp;</i>登&nbsp;录</a>
								<p class="clear"></p></li>
							<p class="clear"></p>
							<li></li>
						</ul>
					</sf:form>
				</div>
				<!--//content结束-->
			</div>
			<!--//bd结束-->
			<p class="ftBg"></p>
		</div>
		<!--//ct结束-->
		<div class="ft">
			<p>建议使用浏览器：IE8-11,Google Chrome</p>
			<p>江西科泰华软件有限公司</p>
			<p class="grey"></p>
		</div>
		<!--//ft结束-->
	</div>
	<!--//mCLoginInfo结束-->
</body>
</html>