<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#ajaxform").rwpUIForm({
			isAjaxSubmit : true,
			inputWidth : 'auto'
		});
	});
</script>
<form action="${ctx}/queueDefendController/saveDeptConfig" id="ajaxform"
	method="post">
	<fieldset>
		<legend>
			<i class="i"></i>队列维护参数配置
		</legend>
		<div class="formpanel">
			<input data-val="true" data-val-number="字段ID必须是一个数字"
				data-val-required="ID不能为空" id="id" name="id" type="hidden"
				value="${id}" />
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="isopen">是否开启维护</label></li>
					<li class="editor-field"><input data-val="true"
						data-val-required="显示状态字段是必需的" id="isopen" name="isopen"
						type="checkbox" value="1"
						<c:if test="${dept.isopen == 1}">
								checked="checked"
							</c:if> /><input
						name="isopen" type="hidden" value="0" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="isopen"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<div class="clear">
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="minutes">超时分钟数</label></li>
					<li class="editor-field"><input data-val="true"
						class="minutes" style="width: 120px;"
						data-val-length="超时分钟数不能超过2字符" data-val-length-max="2"
						data-val-required="超市分钟数不能为空" id="minutes" name="minutes"
						type="text" value="${not empty dept.minutes?dept.minutes:30}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="minutes"
						data-valmsg-replace="true"></span></li>
				</ul>
				<ul class="DialabelWidth">
					<li class="editor-label"><label for="personcount">固定人数</label></li>
					<li class="editor-field"><input data-val="true"
						class="personcount" style="width: 120px;"
						data-val-length="固定人数不能超过2字符" data-val-length-max="2"
						data-val-required="固定人数不能为空" id="personcount" name="personcount"
						type="text"
						value="${not empty dept.personcount?dept.personcount:20}" /></li>
					<li class="editor-validation"><span
						class="field-validation-valid" data-valmsg-for="personcount"
						data-valmsg-replace="true"></span></li>
				</ul>
			</div>
			<script type="text/javascript">
				$('.minutes').rwpUISpinner({
					type : 'int',
					minValue : 0,
					maxValue : 90,
					defaultValueText : ''
				});
				$('.personcount').rwpUISpinner({
					type : 'int',
					minValue : 0,
					maxValue : 60,
					defaultValueText : ''
				});
			</script>
		</div>
		</div>
	</fieldset>
</form>