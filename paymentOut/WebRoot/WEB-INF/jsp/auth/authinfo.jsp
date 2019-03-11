<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.f_selectaction = function() {
		var url = '${ctx}/authController/actionTree?actions='
				+ $("#actionValues").val() + '&rnd=' + Math.random();
		return rwpPluginHelper.selectContact('选择所属动作', encodeURI(url), 'controllerAction',
				'display', 'actionValues', 'actionNames');
	}
	$(function() {
		$("#actionNames").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectaction
		});
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>

<form action="${ctx}/authController/saveAuth" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>权限信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段 权限ID 必须是一个数字。"
				data-val-required="权限ID不能为空" id="ID" name="id" type="hidden"
				value="${authHelper.auth.id }" />
			<ul class="clear">
				<li class="editor-label"><label for="authName">权限名称</label>
				</li>
				<li class="editor-field"><input data-val="true"
					data-val-length="权限名称不能超过20字符" data-val-length-max="20"
					data-val-required="权限名称不能为空" id="authName" name="authName"
					type="text" value="${authHelper.auth.authName }" />
				</li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="authName"
					data-valmsg-replace="true"></span>
				</li>
			</ul>
			<ul class="clear">
				<li class="editor-label"><label for="actions">所属动作列表</label>
				</li>
				<li style=" display: none;"><input id="actionValues"
					name="actionValues" type="hidden"
					value="${authHelper.auth.actionValues }" />
				</li>
				<li class="editor-field"><input id="actionNames"
					name="actionNames" type="text"
					value="${authHelper.auth.actionNames }" /></li>
				<li class="editor-validation"><span
					class="field-validation-valid" data-valmsg-for="actions"
					data-valmsg-replace="true"></span>
				</li>
			</ul>
		</div>
	</fieldset>
</form>