<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.f_selectauth = function() {
		var url = '${ctx}/authController/authDialog?actiontype=selectauth&authids='
				+ $("#authIDs").val() + '&rnd=' + Math.random();
		return rwpPluginHelper.selectContact('选择所属权限', url, 'ID', 'authName',
				'authIDs', 'authNames');
	}
	$(function() {
		$("#authNames").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectauth
		});

		if ("${user.isSys}") {
			$("#ulIsSys").show();
		}
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true
		});
	});
</script>

<form action="${ctx}/roleController/saveRole" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>角色信息
		</legend>
		<input data-val="true" data-val-number="字段 角色ID 必须是一个数字。"
			data-val-required="角色ID 字段是必需的。" id="ID" name="id" type="hidden"
			value="${roleHelper.role.id }" />
		<div class="formpanel">
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="roleName">角色名称</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="角色名称不能超过20字符" data-val-length-max="20"
						data-val-required="角色名称不能为空" id="roleName" name="roleName"
						type="text" value="${roleHelper.role.roleName }" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="roleName"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="auths">包含权限集合</label></li>
					<li style=" display: none;"><input id="authIDs" name="authIDs"
						type="hidden" value="${roleHelper.role.authIDs }" /></li>
					<li class="editor-field"><input id="authNames"
						name="authNames" type="text" value="${roleHelper.role.authNames }" />
					</li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="auths"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth" id="ulIsSys" style="display:none;">
					<li class="editor-label"><label for="isSys">是否系统级</label></li>
					<li class="editor-field"><input data-val="true"
						<c:if test="${roleHelper.role.isSys }">
						checked="checked" 
						</c:if>
						data-val-required="是否系统级 字段是必需的。" id="isSys" name="isSys"
						type="checkbox" value="1" /><input name="isSys" type="hidden"
						value="0" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isSys"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
	</fieldset>
</form>