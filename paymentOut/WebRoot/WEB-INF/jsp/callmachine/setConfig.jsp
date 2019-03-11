<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.f_selectmodelok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#modelId').val(value);
		$('#modelName').val(text);
	};

	rwpTemp.f_selectmodel = function() {
		var url = '${ctx}/macConfigController/configModelDialog?actiontype=selectModel&modelId='
			+ $("#modelId").val() + '&rnd=' + Math.random();
		return rwpPluginHelper.selectContact('选择参数模板', url, 'id', 'modelName',
				'modelId', '', null, rwpTemp.f_selectmodelok);
	};
	$(function() {
		$("#modelName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectmodel
		});
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto'
			/* ajaxSubmitAttrs : {
                isDialog : true,
                afterSubmit : afterSubmit
            } */
		});
	});
	$("#gosub").click(function(){
		$("#gosub").submit();
	});
	$("#back").click(function(){
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
	});
	function afterSubmit(){
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
		rwpPluginHelper.menuHelper
		.loadMenuInsidePage('${ctx}/callMachineController/machineList');
	}
</script>
<form action="${ctx}/callMachineController/saveMacSetting"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>设备参数模板设置
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${id}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="modelId">选择参数模板</label></li>
					<li style="display: none;"><input data-val="true"
						data-val-number="参数模板字段必须是一个数字" data-val-regex="请选择参数模板"
						data-val-regex-pattern="^[1-9]\d*|0$"
						data-val-required="参数模板字段是必需的" id="modelId" name="modelId"
						type="hidden" value="${machine.modelId}" /></li>
					<li class="editor-field"><input id="modelName" name="modelname"
						type="text" value="${machine.model.modelName}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="modelId"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
		</div>
		</div>
	</fieldset>
</form>