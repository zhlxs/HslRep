<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	rwpTemp.f_selectmodelok = function(node, value, text) {
		value = value || 0; //没选择部门时赋值为0
		$('#modelId').val(value);
		$('#modelName').val(text);
	};

	rwpTemp.f_selectmodel = function() {
		var url = '${ctx}/appraiseContrller/modelDialog?actiontype=selectModel&modelId='
				+ $("#modelId").val() + '&rnd=' + Math.random();
		return rwpPluginHelper.selectContact('选择评价模板', url, 'id', 'modelname',
				'modelId', '', null, rwpTemp.f_selectmodelok);
	};
	$(function() {
		$(".rwpDialogTitle").html("<span>部门配置</span>");
		$("#modelName").rwpUICombobox({
			onBeforeOpen : rwpTemp.f_selectmodel
		});
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto',
			ajaxSubmitAttrs : {
				isDialog : true,
				afterSubmit : afterSubmit
			}
		});
	});
	$("#gosub").click(function() {
		$("#gosub").submit();
	});
	$("#back").click(function() {
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
	});
	function afterSubmit() {
		$(".rwpDialogMask").hide();
		$(".rwpDialog").remove();
		rwpPluginHelper.menuHelper
				.loadMenuInsidePage('${ctx}/deptTimesConfigController/timesConfigList');
	}
</script>
<form action="${ctx}/deptTimesConfigController/saveTimesConfig"
	id="ajaxform" method="post">
	<fieldset>
		<legend>
			<i class="i"></i>部门取号次数配置
		</legend>
		<div class="formpanel">
			<input id="deptid" name="deptid" type="hidden" value="${deptid}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="title">当前部门</label></li>
					<li class="editor-field"><input readonly="readonly"
						value="${deptname}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="title"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="timeInter">时间间隔(以天为单位)</label></li>
					<li class="editor-field"><input data-val="true"
						class="timeInter" style="width: 100px;"
						data-val-length="时间间隔不能超过3字符" data-val-length-max="3"
						data-val-required="时间间隔不能为空" id="timeInter" name="timeInter"
						type="text"
						value="${not empty deptTimesConfig.timeInter?deptTimesConfig.timeInter:15}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="timeInter"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="times">限制次数</label></li>
					<li class="editor-field"><input data-val="true" class="times"
						style="width: 100px;" data-val-length="限制次数不能超过2字符"
						data-val-length-max="2" data-val-required="限制次数不能为空" id="times"
						name="times" type="text"
						value="${not empty deptTimesConfig.times?deptTimesConfig.times:5}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="times"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<script type="text/javascript">
				$('.timeInter').rwpUISpinner({
					type : 'int',
					minValue : 0,
					maxValue : 100,
					defaultValueText : ''
				});
				$('.times').rwpUISpinner({
					type : 'int',
					minValue : 0,
					maxValue : 50,
					defaultValueText : ''
				});
			</script>
		</div>
		</div>
	</fieldset>
	<div class="clear" style="margin-left: -1%;">
		<div class="fPptions" style="float: left; margin-top: 4px;" id="gosub">
			<a href="javascript:;" title="确定" class="btnLightBlue btn back"
				style="width: 40px; height: 30px;line-height: 30px;" id="rego"><i
				class="i"></i>确定</a>
		</div>
		<div class="fPptions" style="float: left; margin-top: 4px;" id="back">
			<a href="javascript:;" title="返回" class="btnLightBlue btn back"
				style="width: 40px; height: 30px;line-height: 30px;" id="reback"><i
				class="i"></i>返回</a>
		</div>
	</div>
</form>