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
<form action="${ctx}/blackListController/saveBlack" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>新增黑名单信息
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字" id="id"
				name="id" type="hidden" value="" />
			<div class="clear">
				<ul class="">
					<li class="editor-label"><label for="name">姓名</label></li>
					<li class="editor-field"><input data-val="true" maxlength="32"
						data-val-length="姓名不能超过20字符" data-val-length-max="20"
						data-val-required="姓名不能为空" id="name" name="name" type="text" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="name"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="">
					<li class="editor-label"><label for="cardnumber">身份证号</label></li>
					<li class="editor-field"><input data-val="true" maxlength="18"
						data-val-length="身份证号不能超过18字符" data-val-length-max="18"
						data-val-required="身份证号不能为空" id="cardnumber" name="cardnumber"
						type="text" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="cardnumber"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
</form>