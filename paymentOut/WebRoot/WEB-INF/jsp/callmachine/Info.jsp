<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.f_selectuserdeptok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#deptID').val(value);
		$('#deptName').val(text);
	};

	rwpTemp.f_selectuserdept = function() {
		var url = '${ctx}/deptController/deptDialog';
		return rwpPluginHelper.selectContact('选择所属部门', url, 'ID', 'deptName',
				'deptID', '', null, rwpTemp.f_selectuserdeptok);
	};
	$(function() {
		$("#deptName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectuserdept
		});
		if ("1" == 1)
			$('#ulIsSys').show(); //系统级管理员 可设置菜单的系统级权限
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto'
		});
	});
</script>
<form action="${ctx}/callMachineController/saveMachine" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>新增叫号机信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${machine.id}" />
			<div class="clear">
				<ul class="">
					<li class="editor-label"><label for="deviceNumber">设备机器码</label></li>
					<li class="editor-field"><input data-val="true" style="width: 440px;" maxlength="32"
						data-val-length="设备机器码不能超过32字符" data-val-length-max="32"
						data-val-required="设备机器码不能为空" id="deviceNumber"
						name="deviceNumber" type="text" value="${machine.deviceNumber}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deviceNumber"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="deptID">所属部门</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="字段 所属部门必须是一个数字" data-val-regex="请选择所属部门"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属部门 字段是必需的。" id="deptID" name="deptId"
						type="hidden" value="${machine.deptId}" /></li>
					<li class="editor-field"><input id="deptName" name="deptName"
						type="text" value="${machine.dept.deptName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptID"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="machineIp">设备IP</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-length="设备IP不能超过50字符" data-val-length-max="50"
						data-val-required="设备IP不能为空" id="machineIp" name="machineIp"
						type="text" value="${machine.machineIp}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="machineIp"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
</form>