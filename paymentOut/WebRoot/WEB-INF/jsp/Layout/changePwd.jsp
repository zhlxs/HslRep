<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#ajaxform-changepassword").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>
<form action="${ctx }/layoutController/modifPassword"
	id="ajaxform-changepassword" id="ajaxform-changepassword" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>修改密码
		</legend>
		<div class="formpanel">
			<ul class="clear">
				<li class="editor-label"><label for="oldPassWord">旧密码</label></li>
				<li class="editor-field"><input data-val="true"
					data-val-length="输入的旧密码长度必须是6到32个字符" data-val-length-max="32"
					data-val-length-min="6" data-val-required="请输入旧密码" id="oldPassWord"
					name="oldPassWord" type="password" /></li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="oldPassWord"
					data-valmsg-replace="true"></span></li>
			</ul>
			<ul class="clear">
				<li class="editor-label"><label for="newPassWord">新密码</label></li>
				<li class="editor-field"><input data-val="true"
					data-val-length="输入的新密码长度必须是6到32个字符" data-val-length-max="32"
					data-val-length-min="6" data-val-required="请输入新密码" id="newPassWord"
					name="newPassWord" type="password" /></li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="newPassWord"
					data-valmsg-replace="true"></span></li>
			</ul>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="inputNewPassWord">确认新密码</label>
					</li>
					<li class="editor-field"><input data-val="true"
						data-val-equalto="密码必须一致" data-val-equalto-other="*.newPassWord"
						id="inputNewPassWord" name="inputNewPassWord" type="password" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="inputNewPassWord"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>