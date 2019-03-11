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
<form action="${ctx}/macWindowsController/saveMac" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>新增窗口信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字" id="id"
				name="id" type="hidden" value="${window.id}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="ckbh">窗口编号</label></li>
					<li class="editor-field"><input data-val="true" maxlength="10" value="${window.ckbh}"
						data-val-length="窗口编号不能超过10字符" data-val-length-max="10"
						data-val-required="窗口编号不能为空" id="ckbh" name="ckbh" type="text" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="ckbh"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="status">是否正常</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="窗口状态字段是必需的。" id="status" name="status"
						type="checkbox" value="1"
						<c:if test="${window.status == 1}">
								checked="checked"
							</c:if> /><input
						name="status" type="hidden" value="0" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="status"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="devicenumber">机器码</label></li>
					<li class="editor-field"><input data-val="true" maxlength="32" value="${window.devicenumber}"
						data-val-length="机器码不能超过32字符" data-val-length-max="32"
						data-val-required="机器码不能为空" id="devicenumber" name="devicenumber"
						type="text" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="devicenumber"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="deviceip">计算机IP</label></li>
					<li class="editor-field"><input data-val="true" maxlength="20" value="${window.deviceip}"
						data-val-length="计算机IP不能超过20字符" data-val-length-max="20"
						data-val-required="计算机IP不能为空" id="deviceip" name="deviceip"
						type="text" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deviceip"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="deptID">所属部门</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="字段 所属部门必须是一个数字" data-val-regex="请选择所属部门"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="所属部门 字段是必需的。" id="deptID" name="deptid"
						type="hidden" value="${window.deptid}" /></li>
					<li class="editor-field"><input id="deptName" name="deptName"
						type="text" value="${window.dept.deptName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="deptID"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="systitle">APP标语</label></li>
					<li class="editor-field"><textarea data-val="true"
							maxlength="50" data-val-length="APP标语不能超过50字符"
							data-val-length-max="50" data-val-required="APP标语不能为空"
							id="systitle" name="systitle" type="text">${window.systitle}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="systitle"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="sysunit">APP问候语</label></li>
					<li class="editor-field"><textarea data-val="true"
							maxlength="50" data-val-length="APP问候语不能超过50字符"
							data-val-length-max="50" data-val-required="APP问候语不能为空"
							id="sysunit" name="sysunit" type="text">${window.sysunit}</textarea></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="sysunit"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
</form>